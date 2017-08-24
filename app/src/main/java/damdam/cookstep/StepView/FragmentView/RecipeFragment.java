package damdam.cookstep.StepView.FragmentView;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import org.json.JSONArray;

import damdam.cookstep.StepView.RecipeActivity;
import damdam.cookstep.StepView.SaveCallback;

/**
 * Created by Poste on 15/07/2016.
 */
public abstract class RecipeFragment extends Fragment {

    protected static int SELECT_PICTURE = 451;

    public abstract void setData(JSONArray jsonArray);
    public abstract void setCreatorMode();
    public abstract void setModeView();
    public abstract void saveCreator(SaveCallback saveCallback,RecipeActivity recipeActivity);
    public abstract String getIdData();

    protected String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


}
