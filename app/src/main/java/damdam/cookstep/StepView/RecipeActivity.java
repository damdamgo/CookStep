package damdam.cookstep.StepView;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import damdam.cookstep.R;
import damdam.cookstep.StepView.FragmentView.MainRecipeFragment;
import damdam.cookstep.StepView.FragmentView.RecipeFragment;
import damdam.cookstep.StepView.FragmentView.StepRecipeFragment;

/**
 * Created by Poste on 15/07/2016.
 */
public class RecipeActivity extends AppCompatActivity implements SaveCallback{

    private static String TAG ="RecipeActivity";
    private ViewPager mRecipePager;
    private ScreenSlidePagerAdapter mPagerAdapter;
    private ArrayList<RecipeFragment> listStepRecipeFragment ;
    private boolean modeCreator = false;
    private RecipeFragment mainFragment = null;
    private EditText editTextRecipeName;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        initView();
    }

    private void initView(){

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        toolbar.setTitle("");
        editTextRecipeName = (EditText) findViewById(R.id.editTextRecipeTitle);

        mRecipePager = (ViewPager) findViewById(R.id.RecipePager);

        if(mainFragment==null)mainFragment = new MainRecipeFragment();
        else Log.w(TAG,"RecipeActivity");


        listStepRecipeFragment = new ArrayList<>();
        listStepRecipeFragment.add(mainFragment);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),listStepRecipeFragment);
        mRecipePager.setAdapter(mPagerAdapter);
        mRecipePager.setOffscreenPageLimit(100);

        //TODO A modifier
        mainFragment.setData(null);
        modeCreator = true;
        setModeCreator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_recipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menuCreator:
                showPopupCreator(findViewById(R.id.menuCreator));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void showPopupCreator(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        MenuInflater inflater = popup.getMenuInflater();
        if(modeCreator)inflater.inflate(R.menu.menu_creator, popup.getMenu());
        else inflater.inflate(R.menu.menu_none_creator, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add:
                        StepRecipeFragment step = new StepRecipeFragment();
                        step.setData(null);
                        listStepRecipeFragment.add(step);
                        mPagerAdapter.setListStepRecipeFragment(listStepRecipeFragment);
                        mPagerAdapter.notifyDataSetChanged();
                        mRecipePager.setCurrentItem(listStepRecipeFragment.size()-1);
                        return true;
                    case R.id.remove://TODO attention ne peut supprimer le main sinon quitte le projet
                        return true;
                    case R.id.leaveCreator:
                        setModeView();
                        return true;
                    case R.id.accessCreator:
                        setModeCreator();
                        return true;
                    case R.id.save:
                        saveCreator(false);
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void setModeView() {
        modeCreator = false;
        getSupportActionBar().setTitle(editTextRecipeName.getText().toString());
        editTextRecipeName.setVisibility(View.GONE);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        for (RecipeFragment step: listStepRecipeFragment) {
            step.setModeView();
        }
    }

    private void setModeCreator(){
        modeCreator = true;
        editTextRecipeName.setText(getSupportActionBar().getTitle());
        editTextRecipeName.setVisibility(View.VISIBLE);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        for (RecipeFragment step: listStepRecipeFragment) {
            step.setCreatorMode();
        }
    }

    private void saveCreator(boolean saveStep) {
        if(modeCreator)setModeView();
        if(!saveStep)listStepRecipeFragment.get(0).saveCreator(this,this);
        else{
            for(int i =1;i<listStepRecipeFragment.size();i++){
                listStepRecipeFragment.get(i).saveCreator(this,this);
            }
        }
    }

    @Override
    public void saveCallback(JSONObject jsonObject) {
        try {
            if(jsonObject.getInt("code")==1 && jsonObject.getBoolean("main")){
                saveCreator(true);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getNameRecipe() {
        return toolbar.getTitle().toString();
    }

    public int getNbStep(){
        return listStepRecipeFragment.size()-1;
    }

    public int getIndexFragmentStep(RecipeFragment recipeFragment){
        for(int i =1;i<listStepRecipeFragment.size();i++){
            if(listStepRecipeFragment.get(i).equals(recipeFragment))return i;
        }
        return -1;
    }

    public String getIdRecipe(){
        return listStepRecipeFragment.get(0).getIdData();
    }
}
