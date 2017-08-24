package damdam.cookstep.StepView.FragmentView;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import damdam.cookstep.R;
import damdam.cookstep.StepView.RecipeActivity;
import damdam.cookstep.StepView.SaveCallback;
import damdam.cookstep.StepView.SaveData.SaveStep;
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
public class StepRecipeFragment extends RecipeFragment {

    private static String TAG = "StepRecipeFragment";
    private boolean modeCreator = false;
    public String pathImage = "";

    private ViewGroup rootView = null;
    private ImageView imageViewStep;
    private TextView textViewStep;
    private EditText editTextStep;
    private TrickListView trickListView;
    private SaveStep saveStep ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = (ViewGroup) inflater.inflate(
                R.layout.step_recipe_fragment, container, false);
        imageViewStep = (ImageView) rootView.findViewById(R.id.imageViewStep);
        textViewStep = (TextView) rootView.findViewById(R.id.textViewStepText);
        editTextStep = (EditText) rootView.findViewById(R.id.editTextStepText);
        trickListView = (TrickListView) rootView.findViewById(R.id.trickListStep);

        ViewTreeObserver vto = imageViewStep.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                imageViewStep.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int height = (imageViewStep.getWidth()*360)/640;
                imageViewStep.getLayoutParams().height = height;
                imageViewStep.requestLayout();
            }
        });

        imageViewStep.setOnClickListener(new View.OnClickListener() {
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
            trickListView.setModeCreator();
            editTextStep.setText(textViewStep.getText());
            textViewStep.setVisibility(View.GONE);
            editTextStep.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                pathImage = getPath(selectedImageUri);
                imageViewStep.setImageBitmap(BitmapFactory.decodeFile(pathImage));
            }
        }
    }



    @Override
    public void setData(JSONArray jsonArray) {
        if(jsonArray == null){
            setCreatorMode();
            saveStep = new SaveStep();
        }
    }

    @Override
    public void setCreatorMode() {
        modeCreator = true;
        if(rootView!=null){
            trickListView.setModeCreator();
            editTextStep.setText(textViewStep.getText());
            textViewStep.setVisibility(View.GONE);
            editTextStep.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setModeView() {
        modeCreator = false;
        if(rootView!=null){
            trickListView.setModeView();
            textViewStep.setText(editTextStep.getText());
            textViewStep.setVisibility(View.VISIBLE);
            editTextStep.setVisibility(View.GONE);
        }
    }

    @Override
    public void saveCreator(SaveCallback saveCallback,RecipeActivity recipeActivity) {
        ArrayList<Trick> lTrick = trickListView.getListTrick();
        JSONObject jsonObject = saveStep.getDataToSend(lTrick,
                textViewStep.getText().toString(),
                recipeActivity.getIndexFragmentStep(this),
                recipeActivity.getIdRecipe());
        sendData(saveCallback,jsonObject);
    }

    @Override
    public String getIdData() {
        return saveStep.getId();
    }

    private void sendData(final SaveCallback saveCallback, JSONObject jsonObject) {
        OkHttpClient client = new OkHttpClient();

        FormBody formBody = new FormBody.Builder().add("jsonStep",jsonObject.toString()).build();

        Request request = new Request.Builder().url(Preferences.URL_BASE+Preferences.APPID+Preferences.SAVE_STEP)
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
                            saveStep.setId(res.getString("idStep"));
                            if(pathImage.equals("")){
                                saveCallback.saveCallback(new JSONObject().put("code",1).put("main",false));
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

    private void sendPhoto(final SaveCallback saveCallback) {
        OkHttpClient client = new OkHttpClient();

        File img = new File(pathImage);

        MultipartBody multipartyBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("idStep",saveStep.getId())
                .addFormDataPart(saveStep.getId(),pathImage, RequestBody.create(MediaType.parse("image/png"),img)).build();

        Request request = new Request.Builder().url(Preferences.URL_BASE+Preferences.APPID+Preferences.SAVE_STEP_PICTURE).post(multipartyBody).build();
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
                            saveCallback.saveCallback(new JSONObject().put("code",1).put("main",false));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


}
