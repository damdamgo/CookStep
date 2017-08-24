package damdam.cookstep.userManage;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import damdam.cookstep.MainActivity;
import damdam.cookstep.Tool.Tools;
import damdam.cookstep.setting.Preferences;
import damdam.cookstep.R;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Poste on 14/07/2016.
 */
public class ConnectionActivity extends AppCompatActivity {

    private static String TAG = "ConnectionActivity";
    private static int SELECT_PICTURE = 1542;
    private String pathImageUser = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);
        initView();
    }

    private void initView(){
        ((Button)findViewById(R.id.buttonSignin)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nicknameEdit = (EditText) findViewById(R.id.editTextSigninNickname);
                String nickname = nicknameEdit.getText().toString();
                EditText passwordEdit = (EditText) findViewById(R.id.editTextSigninPassword);
                String password = passwordEdit.getText().toString();
                sendDataSignin(nickname,password);
            }
        });

        ((Button)findViewById(R.id.buttonSelectPhotoUser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
            }
        });

        ((Button)findViewById(R.id.buttonSignup)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText nicknameEdit = (EditText) findViewById(R.id.editTextSignupNickname);
                String nickname = nicknameEdit.getText().toString();
                EditText passwordFEdit = (EditText) findViewById(R.id.editTextSignupFirstPassword);
                String passwordF = passwordFEdit.getText().toString();
                EditText passwordSEdit = (EditText) findViewById(R.id.editTextSignupSecondPassword);
                String passwordS = passwordSEdit.getText().toString();
                sendDataSignup(nickname,passwordF,passwordS);
            }
        });
    }

    private void sendDataImageUser(String nickname,String idUser) {
        Log.w(TAG,"sendDataImageUser");
        String[][] arrayPost = new String[][]{{"idUser",idUser}};
        String url = Preferences.URL_BASE+Preferences.APPID+Preferences.IMAGEUSER;
        Tools.sendPicture(arrayPost, url, pathImageUser, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                Log.w(TAG,e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Log.w(TAG,"error internet"+response.code());
                    Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                }
                else{
                    try {
                        JSONObject answer = new JSONObject(response.body().string());
                        Log.w(TAG,answer.toString());
                        if(answer.getInt("code")==1){
                            Intent i = new Intent(ConnectionActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    private void sendDataSignup(final String nickname, String passwordF, String passwordS) {
        if(passwordF.equals(passwordS)){
            if(User.getInstance(this).isConnected()){//if problem during photo sending TODO prevent nickname modification
                if(!pathImageUser.equals("")){
                    sendDataImageUser(nickname,User.getInstance(this).getIdUser());
                }
            }
            else{
                String[][] arrayPost = new String[][]{{"nickname",nickname},{"password",passwordF}};
                String url = Preferences.URL_BASE+Preferences.APPID+Preferences.SIGNUP;
                Tools.sendPost(arrayPost, url, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (!response.isSuccessful()) {
                            Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                            Log.w(TAG,"error internet"+response.code());
                        }
                        else{
                            try {
                                JSONObject answer = new JSONObject(response.body().string());
                                Log.w(TAG,answer.toString());
                                if(answer.getInt("code")==1){
                                    User.getInstance(ConnectionActivity.this).setConnection(answer.getString("idUser"),nickname,answer.getInt("status"),ConnectionActivity.this);
                                    if(!pathImageUser.equals("")){
                                        sendDataImageUser(nickname,User.getInstance(ConnectionActivity.this).getIdUser());
                                    }
                                    else{
                                        Intent i = new Intent(ConnectionActivity.this, MainActivity.class);
                                        startActivity(i);
                                        finish();
                                    }
                                    //TODO check if nickname is not already taken
                                }
                                else{
                                    Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        }
        else{
            //TODO manage not equal
            Log.w(TAG,"password not equal");
        }
    }



    private void sendDataSignin(final String nickname, String password) {

        String[][] arrayPost = new String[][]{{"nickname",nickname},{"password",password}};
        String url = Preferences.URL_BASE+Preferences.APPID+Preferences.SIGNIN;
        Tools.sendPost(arrayPost, url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                    Log.w(TAG,"error internet"+response.code());
                }
                else{
                    try {
                        JSONObject answer = new JSONObject(response.body().string());
                        if(answer.getInt("code")==1){
                            User.getInstance(ConnectionActivity.this).setConnection(answer.getString("idUser"),nickname,answer.getInt("status"),ConnectionActivity.this);
                            Intent i = new Intent(ConnectionActivity.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else{
                            Tools.displayMessage("une erreur s'est produite",ConnectionActivity.this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK)
        {
            if (requestCode == SELECT_PICTURE)
            {
                Uri selectedImageUri = data.getData();
                pathImageUser = getPath(selectedImageUri);
            }
        }
    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }
}
