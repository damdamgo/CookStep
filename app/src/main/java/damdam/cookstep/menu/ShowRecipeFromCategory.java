package damdam.cookstep.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import damdam.cookstep.CallBack.CallBack;
import damdam.cookstep.CallBack.CallBackResult;
import damdam.cookstep.CookStep;
import damdam.cookstep.R;
import damdam.cookstep.Tool.Tools;
import damdam.cookstep.setting.Preferences;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Poste on 21/07/2016.
 */
public class ShowRecipeFromCategory extends AppCompatActivity{

    private static String TAG = "ShowRecipeFromCategory";
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String titleCategory,idCategory;
    private String keySort = "creationDate";
    private String sortOrder = "1";
    private int minBorder = 0;
    private int maxBorder = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_categories_recipe);
        Intent i = getIntent();
        titleCategory = i.getStringExtra(SubCategoriesActivity.INTENT_STRING_TITLE);
        idCategory = i.getStringExtra(SubCategoriesActivity.INTENT_STRING_ID);


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AdapterListRecipe();
        mRecyclerView.setAdapter(mAdapter);

        getRecipePartFromCategory();


    }

    private void getRecipePartFromCategory(){
        String[][] arrayPost = new String[][]{{"idCategorie",idCategory},
                {"keySort",keySort},
                {"sortOrder",sortOrder},
                {"borderMin", String.valueOf(minBorder)},
                {"borderMax", String.valueOf(maxBorder)}};
        String url = Preferences.URL_BASE+Preferences.APPID+Preferences.GET_RECIPE_FROM_CATEGORY;
        Log.w(TAG,url);
        Tools.sendPost(arrayPost, url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Tools.displayMessage("une erreur s'est produite",ShowRecipeFromCategory.this);
                Log.w(TAG,e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.w(TAG, "error internet" + response.code());
                    Tools.displayMessage("une erreur s'est produite", ShowRecipeFromCategory.this);
                } else {
                    try {
                        JSONObject answer = new JSONObject(response.body().string());
                        Log.w(TAG, answer.toString());
                        if (answer.getInt("code") == 1) {
                            Log.w(TAG, answer.toString());
                        } else {
                            Tools.displayMessage("une erreur s'est produite", ShowRecipeFromCategory.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

}
