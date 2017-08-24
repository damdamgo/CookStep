package damdam.cookstep;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import damdam.cookstep.CallBack.CallBack;
import damdam.cookstep.CallBack.CallBackResult;
import damdam.cookstep.setting.Preferences;
import damdam.cookstep.userManage.ConnectionActivity;
import damdam.cookstep.userManage.User;

/**
 * Created by damien villiers on 10/07/2016.
 */
public class SplashScreen extends Activity implements CallBack {

    /**
     * splash screen timer
     */
    private static int SPLASH_TIME_OUT = 3000;
    private boolean authoFromCallBack = false;
    private boolean authoFromThread = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        CookStep.getInstance().getCategories(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                authoFromThread = true;
                goToMainActivity();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void callBack(CallBackResult o) {
        authoFromCallBack=true;
        goToMainActivity();
    }

    private void goToMainActivity(){
        if(authoFromCallBack && authoFromThread){
            User user = User.getInstance(this);
            Log.w("splash",user.getNickname());
            if(!user.isConnected()){
                Intent i = new Intent(SplashScreen.this, ConnectionActivity.class);
                startActivity(i);
                finish();
            }
            else{
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);
                finish();
            }
        }
    }
}
