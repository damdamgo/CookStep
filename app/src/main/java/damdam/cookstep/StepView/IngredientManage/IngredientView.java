package damdam.cookstep.StepView.IngredientManage;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import damdam.cookstep.R;

/**
 * Created by Poste on 15/07/2016.
 */
public class IngredientView extends LinearLayout {

    private Ingredient ingredient;
    private boolean modeCreator = false;

    private LinearLayout layoutIngredient;
    private TextView textViewQuantity,textViewIngredient;
    private LinearLayout layoutIngredientCreator;
    private EditText editTextQuantity,editTextIngredient;
    private Button buttonRemoveRecette;
    private IngredientListView callbackRemove;

    public IngredientView(Context context) {
        super(context);
        init();
    }

    public IngredientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        ingredient=new Ingredient("","");
        inflate(getContext(), R.layout.layout_ingredient,this);
        layoutIngredient = (LinearLayout) this.findViewById(R.id.layoutIngredient);
        textViewQuantity = (TextView) this.findViewById(R.id.textViewQuantity);
        textViewIngredient = (TextView) this.findViewById(R.id.textViewIngredient);
        layoutIngredientCreator = (LinearLayout) this.findViewById(R.id.layoutIngredientCreator);
        editTextQuantity = (EditText) this.findViewById(R.id.editTextQuantity);
        editTextIngredient = (EditText) this.findViewById(R.id.editTextIngredient);
        buttonRemoveRecette = (Button) this.findViewById(R.id.buttonRemoveIngredient);

        buttonRemoveRecette.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackRemove.remove(IngredientView.this);
            }
        });
    }


    public void setModeCreator() {
        modeCreator = true;
        editTextIngredient.setText(textViewIngredient.getText());
        editTextQuantity.setText(textViewQuantity.getText());
        layoutIngredient.setVisibility(GONE);
        layoutIngredientCreator.setVisibility(VISIBLE);
        ingredient = new Ingredient(editTextIngredient.getText().toString(),editTextQuantity.getText().toString());
    }

    public void setModeView() {
        modeCreator = false;
        textViewQuantity.setText(editTextQuantity.getText());
        textViewIngredient.setText(editTextIngredient.getText());
        layoutIngredient.setVisibility(VISIBLE);
        layoutIngredientCreator.setVisibility(GONE);
        ingredient = new Ingredient(textViewIngredient.getText().toString(),textViewQuantity.getText().toString());
    }


    public void fixCallbackRemove(IngredientListView ingredientListView) {
        this.callbackRemove = ingredientListView;
    }


    public Ingredient getIngredient() {
        return this.ingredient;
    }
}
