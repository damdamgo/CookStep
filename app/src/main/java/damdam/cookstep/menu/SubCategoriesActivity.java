package damdam.cookstep.menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import damdam.cookstep.CallBack.CallBack;
import damdam.cookstep.CallBack.CallBackResult;
import damdam.cookstep.CookStep;
import damdam.cookstep.R;

/**
 * Created by Poste on 13/07/2016.
 */
public class SubCategoriesActivity extends AppCompatActivity implements CallBack {

    private static String TAG = "SubCategoriesActivity";
    private int subCategories;
    public static String INTENT_STRING_TITLE = "indexSubCategoryTitle";
    public static String INTENT_STRING_ID = "indexSubCategoryId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_categories);
        Intent i = getIntent();
        subCategories = i.getIntExtra(MainCategoriesActivity.KEY_INDEX_SUBCATEGORIES,0);
        CookStep.getInstance().getCategories(this);
    }

    @Override
    public void callBack(CallBackResult o) {
        if(o.getErrorCode()!=1){
            Log.w(TAG,"error callack "+o.getErrorCode());
            //TODO manage error callback
        }
        else {
            try {
                LinearLayout containSubCategories = (LinearLayout) findViewById(R.id.containSubCategories);
                for(int i =1;i<((JSONArray)o.getmJsonArray().getJSONArray(subCategories)).length();i++){
                    JSONObject subCategory = o.getmJsonArray().getJSONArray(subCategories).getJSONObject(i);
                    Button b = new Button(this);
                    b.setText(subCategory.getString("FR"));
                    final String title = subCategory.getString("FR");
                    final String idCategory = subCategory.getString("_id");
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(SubCategoriesActivity.this,ShowRecipeFromCategory.class);
                            i.putExtra(INTENT_STRING_TITLE,title);
                            i.putExtra(INTENT_STRING_ID,idCategory);
                            startActivity(i);
                        }
                    });
                    containSubCategories.addView(b);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
