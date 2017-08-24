package damdam.cookstep.StepView.FragmentView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import damdam.cookstep.CallBack.CallBack;
import damdam.cookstep.CallBack.CallBackResult;
import damdam.cookstep.CookStep;
import damdam.cookstep.Data.CategoriesManager;
import damdam.cookstep.R;
import damdam.cookstep.StepView.IngredientManage.Ingredient;
import damdam.cookstep.StepView.IngredientManage.IngredientListView;
import damdam.cookstep.StepView.RecipeActivity;
import damdam.cookstep.StepView.SaveCallback;
import damdam.cookstep.StepView.SaveData.SaveMainFragment;
import damdam.cookstep.StepView.TrickManage.Trick;
import damdam.cookstep.StepView.TrickManage.TrickListView;
import damdam.cookstep.setting.Preferences;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Poste on 15/07/2016.
 */
public class MainRecipeFragment extends RecipeFragment implements CallBack {

    private static String TAG ="MainRecipeFragment";
    private IngredientListView mIngredientListView;
    private ImageView mImageViewPhoto;
    private ViewGroup rootView = null;
    private boolean modeCreator = false;
    private TrickListView mTrickListView;
    private TextView textViewTime;
    private EditText editTextTime;
    private TextView textViewSource;
    private EditText editTextSource;
    private TextView textViewCountry;
    private EditText editTextCountry;
    private String pathImage = "";
    private SaveMainFragment saveMainFragment;
    private Spinner spinnerMainCategories,spinnerSubCategories;
    private ArrayList<CharSequence> mainCategories = new ArrayList<>();
    private ArrayList<ArrayList> subCategories = new ArrayList<>();
    private String idCategories = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.main_recipe_fragment, container, false);
        mIngredientListView = (IngredientListView) rootView.findViewById(R.id.layoutContainListIngredientsMain);
        mTrickListView = (TrickListView) rootView.findViewById(R.id.layoutContainListTrickMain);
        mImageViewPhoto = (ImageView) rootView.findViewById(R.id.imageViewPhoto);
        textViewTime = (TextView) rootView.findViewById(R.id.textViewTime);
        editTextTime = (EditText) rootView.findViewById(R.id.editTextTime);
        textViewCountry = (TextView) rootView.findViewById(R.id.textViewCountry);
        editTextCountry = (EditText) rootView.findViewById(R.id.editTextCountry);
        textViewSource = (TextView) rootView.findViewById(R.id.textViewSource);
        editTextSource = (EditText) rootView.findViewById(R.id.editTextSource);
        spinnerMainCategories = (Spinner) rootView.findViewById(R.id.spinnerMainCategories);
        spinnerSubCategories = (Spinner) rootView.findViewById(R.id.spinnerSubCategories);
        CookStep.getInstance().getCategories(this);


        ViewTreeObserver vto = mImageViewPhoto.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mImageViewPhoto.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = (mImageViewPhoto.getWidth()*360)/640;
                mImageViewPhoto.getLayoutParams().height = height;
                mImageViewPhoto.requestLayout();
            }
        });

        mImageViewPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modeCreator){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(modeCreator){
            mIngredientListView.setModeCreator();
            mTrickListView.setModeCreator();

            editTextSource.setText(textViewSource.getText());
            textViewSource.setVisibility(View.GONE);
            editTextSource.setVisibility(View.VISIBLE);

            editTextTime.setText(textViewTime.getText());
            textViewTime.setVisibility(View.GONE);
            editTextTime.setVisibility(View.VISIBLE);

            editTextCountry.setText(textViewCountry.getText());
            textViewCountry.setVisibility(View.GONE);
            editTextCountry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                pathImage = getPath(selectedImageUri);
                mImageViewPhoto.setImageBitmap(BitmapFactory.decodeFile(pathImage));
            }
        }
    }

    @Override
    public void setData(JSONArray jsonArray) {
        if(jsonArray == null){
            setCreatorMode();
            saveMainFragment = new SaveMainFragment();
        }
    }

    @Override
    public void setCreatorMode() {
        modeCreator= true;
        if(rootView!=null){
            mIngredientListView.setModeCreator();
            mTrickListView.setModeCreator();
            editTextSource.setText(textViewSource.getText());
            textViewSource.setVisibility(View.GONE);
            editTextSource.setVisibility(View.VISIBLE);

            editTextTime.setText(textViewTime.getText());
            textViewTime.setVisibility(View.GONE);
            editTextTime.setVisibility(View.VISIBLE);

            editTextCountry.setText(textViewCountry.getText());
            textViewCountry.setVisibility(View.GONE);
            editTextCountry.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setModeView() {
        modeCreator = false;
        if(rootView!=null){
            mIngredientListView.setModeView();
            mTrickListView.setModeView();
            textViewSource.setText(editTextSource.getText());
            textViewSource.setVisibility(View.VISIBLE);
            editTextSource.setVisibility(View.GONE);

            textViewTime.setText(editTextTime.getText());
            textViewTime.setVisibility(View.VISIBLE);
            editTextTime.setVisibility(View.GONE);

            textViewCountry.setText(textViewCountry.getText());
            textViewCountry.setVisibility(View.VISIBLE);
            editTextCountry.setVisibility(View.GONE);
        }
    }

    @Override
    public void saveCreator(SaveCallback saveCallback,RecipeActivity recipeActivity) {
        ArrayList<Ingredient> lIngredient = mIngredientListView.getListIngredient();
        ArrayList<Trick> lTrick = mTrickListView.getListTrick();
        JSONObject jsonObject = saveMainFragment.getDataToSend(lIngredient,
                lTrick,
                textViewCountry.getText().toString(),
                textViewTime.getText().toString(),
                textViewSource.getText().toString(),
                recipeActivity.getNameRecipe(),
                recipeActivity.getNbStep(),
                idCategories);
        sendData(saveCallback,jsonObject);
    }

    @Override
    public String getIdData() {
        return saveMainFragment.getId();
    }

    private void sendData(final SaveCallback saveCallback, JSONObject jsonObject) {
        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder().add("jsonRecipe",jsonObject.toString()).build();

        Request request = new Request.Builder().url(Preferences.URL_BASE+Preferences.APPID+Preferences.SAVE_RECIPE)
                .post(formBody).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //TODO manage error
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.w(TAG,"error internet");
                }
                else{
                    try {
                        JSONObject res = new JSONObject(response.body().string());
                        if(res.getInt("code")==1){
                            saveMainFragment.setId(res.getString("idRecipe"));
                            if(pathImage.equals("")){
                                saveCallback.saveCallback(new JSONObject().put("code",1).put("main",true));
                            }
                            else{
                                sendPhoto(saveCallback);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void sendPhoto(final SaveCallback saveCallback){
        OkHttpClient client = new OkHttpClient();

        File img = new File(pathImage);

        MultipartBody multipartyBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("idRecipe",saveMainFragment.getId())
                .addFormDataPart(saveMainFragment.getId(),pathImage, RequestBody.create(MediaType.parse("image/png"),img)).build();

        Request request = new Request.Builder().url(Preferences.URL_BASE+Preferences.APPID+Preferences.SAVE_MAIN_RECIPE_PICTURE).post(multipartyBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.w(TAG,"error internet"+response.code());
                }
                else{
                    try {
                        JSONObject res = new JSONObject(response.body().string());
                        if(res.getInt("code")==1){
                            pathImage="";
                            saveCallback.saveCallback(new JSONObject().put("code",1).put("main",true));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void callBack(final CallBackResult o) {
        Log.w(TAG,o.getmJsonArray().toString());
        try {
            for (int i = 0;i<o.getmJsonArray().length();i++){
                ArrayList<CharSequence> subCat = new ArrayList<>();
                for(int y = 0;y<((JSONArray)o.getmJsonArray().get(i)).length();y++){
                    JSONObject json = (JSONObject) ((JSONArray)o.getmJsonArray().get(i)).get(y);
                      if(y==0){
                          mainCategories.add(json.getString("FR"));
                      }
                    else{
                          subCat.add(json.getString("FR"));
                      }
                }
                subCategories.add(subCat);
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1,mainCategories);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerMainCategories.setAdapter(adapter);
                spinnerMainCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getContext(), android.R.layout.simple_list_item_1,subCategories.get(i));
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinnerSubCategories.setAdapter(adapter);
                        final int indexMain = i;
                        spinnerSubCategories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                for (int ind = 0;ind<o.getmJsonArray().length();ind++){
                                    try {
                                        for(int indd = 0;indd<((JSONArray)o.getmJsonArray().get(ind)).length();indd++){
                                            JSONObject json = (JSONObject) ((JSONArray)o.getmJsonArray().get(ind)).get(indd);
                                            if(json.getString("FR").equals(subCategories.get(indexMain).get(i))){
                                                idCategories = json.getString("_id");
                                                Log.w(TAG,idCategories);
                                            }
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }
        });

    }
}
