package damdam.cookstep.setting;

import android.app.Activity;
import android.content.SharedPreferences;

/**
 * Created by Poste on 12/07/2016.
 */
public class Preferences {
    /**
     * callback success
     */
    public static final int SUCCESS = 1;
    /**
     * network error
     */
    public static final int ERROR_NETWORK_CALLBACK = 2;
    /**
     * error read data from url given
     */
    public static final int ERROR_READ_DATA_CALLBACK = 3;



    ///////////////////////////url
    /**
     * url
     */
    public static final String URL_BASE= "http://192.168.1.35:3000/";
    /**
     * app id
     */
    public static final String APPID = "AJLKJKdsf54454LKLJSDDFjklgsgfs4fd5sdfkNFNDF";
    /**
     * categories access
     */
    public static final String CATEGORIES = "/FR/getCategories";
    /**
     * sign in
     */
    public static final String SIGNIN = "/FR/signinUser";
    /**
     * sign up
     */
    public static final String SIGNUP = "/FR/signupUser";
    /**
     * image user
     */
    public static final String IMAGEUSER = "/FR/imageUser";
    /**
     * save recipe
     */
    public static final String SAVE_RECIPE ="/FR/saveRecipe";
    /**
     *save recipepicture
     */
    public static final String SAVE_MAIN_RECIPE_PICTURE = "/FR/saveRecipePicture";
    /**
     * save recipe
     */
    public static final String SAVE_STEP ="/FR/saveStep";
    /**
     *save recipepicture
     */
    public static final String SAVE_STEP_PICTURE = "/FR/saveStepPicture";
    /**
     * get recipe from categories
     */
    public static final String GET_RECIPE_FROM_CATEGORY = "/FR/getRecipeFromCategory";




    /**
     * manager shared preferences
     */
    public static final String PREFS_NAME = "MyPrefs";

    /**
     * getter shared preferences
     * @param activity
     * @param key
     * @return
     */
    public static String getSharedPreferenceString(Activity activity,String key){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        return settings.getString(key,"none");
    }

    public static void setSharedPreferenceString(Activity activity,String key,String value){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key,value);
        editor.commit();
    }

    public static int getSharedPreferenceInt(Activity activity,String key){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        return settings.getInt(key,-1);
    }

    public static void setSharedPreferenceInt(Activity activity,String key,int value){
        SharedPreferences settings = activity.getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt(key,value);
        editor.commit();
    }

}
