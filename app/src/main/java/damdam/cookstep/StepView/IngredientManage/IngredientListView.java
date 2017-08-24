package damdam.cookstep.StepView.IngredientManage;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;

import java.util.ArrayList;

import damdam.cookstep.R;

/**
 * Created by Poste on 16/07/2016.
 */
public class IngredientListView extends LinearLayout{

    private static String TAG = "IngredientListView";
    private ArrayList<IngredientView> listIngredients = new ArrayList<>();
    private boolean modeCreator = false;

    private LinearLayout layoutContainListIngredient;
    private Button buttonAddIngredient;

    public IngredientListView(Context context) {
        super(context);
        init();
    }

    public IngredientListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IngredientListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.layout_list_ingredient,this);
        layoutContainListIngredient = (LinearLayout) this.findViewById(R.id.layoutContainListIngredients);
        buttonAddIngredient = (Button) this.findViewById(R.id.buttonAddIngredient);
        buttonAddIngredient.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                IngredientView newIngredient = new IngredientView(getContext());
                newIngredient.fixCallbackRemove(IngredientListView.this);
                listIngredients.add(newIngredient);
                layoutContainListIngredient.addView(newIngredient);
                if(modeCreator)newIngredient.setModeCreator();
            }
        });
    }

    public void setModeCreator(){
        modeCreator = true;
        buttonAddIngredient.setVisibility(VISIBLE);
        for (IngredientView ingredientView: listIngredients) {
            ingredientView.setModeCreator();
        }

    }

    public void setModeView(){
        modeCreator = false;
        buttonAddIngredient.setVisibility(GONE);
        for (IngredientView ingredientView: listIngredients) {
            ingredientView.setModeView();
        }
    }

    public void setData(JSONArray jsonArray){

    }

    public void remove(IngredientView ingredientView) {
        //TODO delete from list
        layoutContainListIngredient.removeView(ingredientView);
        listIngredients.remove(ingredientView);
    }

    public ArrayList<Ingredient> getListIngredient() {
        ArrayList<Ingredient> listIngredient = new ArrayList<>();
        for (IngredientView ingredientView: listIngredients) {
            listIngredient.add(ingredientView.getIngredient());
        }
        return listIngredient;
    }
}
