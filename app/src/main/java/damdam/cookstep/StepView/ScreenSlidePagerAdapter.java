package damdam.cookstep.StepView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

import damdam.cookstep.StepView.FragmentView.RecipeFragment;

/**
 * Created by Poste on 15/07/2016.
 */
public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private ArrayList<RecipeFragment> listStepRecipeFragment ;

    public ScreenSlidePagerAdapter(FragmentManager fm,ArrayList<RecipeFragment> listStepRecipeFragment) {
        super(fm);
        this.listStepRecipeFragment = listStepRecipeFragment;
    }

    @Override
    public Fragment getItem(int position) {
        return listStepRecipeFragment.get(position);
    }

    @Override
    public int getCount() {
        return listStepRecipeFragment.size();
    }

    public void setListStepRecipeFragment(ArrayList<RecipeFragment> listStepRecipeFragment) {
        this.listStepRecipeFragment = listStepRecipeFragment;
    }
}
