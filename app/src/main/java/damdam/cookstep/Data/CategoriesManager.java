package damdam.cookstep.Data;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;

import damdam.cookstep.CallBack.CallBack;
import damdam.cookstep.CallBack.CallBackResult;
import damdam.cookstep.setting.Preferences;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Poste on 12/07/2016.
 */
public class CategoriesManager {

    JSONArray mCategories = null;

    public CategoriesManager(){

    }

    public void getCategories(final CallBack callBack) {
        if(mCategories != null){
            callBack.callBack(new CallBackResult(Preferences.SUCCESS,mCategories));
        }
        else{
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(Preferences.URL_BASE+Preferences.APPID+Preferences.CATEGORIES).build();
                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                        callBack.callBack(new CallBackResult(Preferences.ERROR_NETWORK_CALLBACK,null));
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            callBack.callBack(new CallBackResult(Preferences.ERROR_NETWORK_CALLBACK,null));
                        }
                        else{
                            try {
                                mCategories = new JSONArray(response.body().string());
                            } catch (JSONException e) {
                                e.printStackTrace();
                                callBack.callBack(new CallBackResult(Preferences.ERROR_READ_DATA_CALLBACK,null));
                            }
                            callBack.callBack(new CallBackResult(Preferences.SUCCESS,mCategories));
                        }
                    }
                });
        }
    }
}
