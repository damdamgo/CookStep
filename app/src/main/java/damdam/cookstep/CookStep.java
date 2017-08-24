package damdam.cookstep;

import android.app.Application;

import damdam.cookstep.CallBack.CallBack;
import damdam.cookstep.Data.CategoriesManager;

/**
 * Created by Poste on 12/07/2016.
 */
public class CookStep extends Application {

    private static CookStep mCookStep = null;
    private CategoriesManager mCategoriesManager = null;

    @Override
    public void onCreate() {
        super.onCreate();
        mCookStep = this;
        mCategoriesManager = new CategoriesManager();
    }

    public static CookStep getInstance(){
        checkInstance();
        return mCookStep;
    }

    private static void checkInstance() {
        if (mCookStep == null)
            throw new IllegalStateException("Application not created yet!");
    }

    public void getCategories(CallBack callBack){
        mCategoriesManager.getCategories(callBack);
    }
}
