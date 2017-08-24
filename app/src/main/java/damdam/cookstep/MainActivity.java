package damdam.cookstep;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import damdam.cookstep.StepView.RecipeActivity;
import damdam.cookstep.menu.MainCategoriesActivity;

public class MainActivity extends AppCompatActivity {

    private static String TAG = "mainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView(){
        ((Button)findViewById(R.id.buttonAccessToCategories)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, MainCategoriesActivity.class);
                startActivity(i);
            }
        });

        ((Button)findViewById(R.id.buttonAccessToRecipe)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RecipeActivity.class);
                startActivity(i);
            }
        });
    }


}
