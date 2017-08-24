package damdam.cookstep.Tool;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import damdam.cookstep.setting.Preferences;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by Poste on 19/07/2016.
 */
public class Tools {

    public static void sendPost(String[][] arrayPost, String url, Callback callback){
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder formBody = new FormBody.Builder();

        for(int i =0;i<arrayPost.length;i++){
            formBody.add(((String)arrayPost[i][0]),((String)arrayPost[i][1]));
        }

        Request request = new Request.Builder().url(url)
                .post(formBody.build()).build();

        client.newCall(request).enqueue(callback);

    }

    public static void sendPicture(String[][] arrayPost, String url,String pathPicture, Callback callback){
        OkHttpClient client = new OkHttpClient();

        File img = new File(pathPicture);

        MultipartBody.Builder multipartyBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);

        for(int i =0;i<arrayPost.length;i++){
            Log.w("eee",arrayPost[i][0]);
            multipartyBody.addFormDataPart(((String)arrayPost[i][0]),((String)arrayPost[i][1]));
        }
        MultipartBody m = multipartyBody.addFormDataPart("image",pathPicture, RequestBody.create(MediaType.parse("image/jpg"),img)).build();

        Request request = new Request.Builder().url(url).post(m).build();

        client.newCall(request).enqueue(callback);

    }

    public static void displayMessage(final String message,final Activity activity){
        activity.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(activity, message,
                        Toast.LENGTH_LONG).show();
            }
        });
    }

}
