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
public class MainCategoriesActivity extends AppCompatActivity implements CallBack {

    private static String TAG = "MainCategoriesActivity";
    public static String KEY_INDEX_SUBCATEGORIES ="keyIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_categories);
        CookStep.getInstance().getCategories(this);
    }

    @Override
    public void callBack(CallBackResult o) {
        if(o.getErrorCode()!=1){
            Log.w(TAG,"error callack "+o.getErrorCode());
            //TODO manage error callback
        }
        else{
            LinearLayout contain = (LinearLayout) findViewById(R.id.containMainCategories);
            for(int i = 0;i<o.getmJsonArray().length();i++){
                try {
                    JSONObject mainCategorie = (JSONObject) ((JSONArray)o.getmJsonArray().get(i)).get(0);
                    Button b = new Button(this);
                    b.setText(mainCategorie.getString("FR"));
                    final int y =i;
                    b.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(MainCategoriesActivity.this, SubCategoriesActivity.class);
                            intent.putExtra(KEY_INDEX_SUBCATEGORIES,y);
                            startActivity(intent);
                        }
                    });
                    contain.addView(b);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
