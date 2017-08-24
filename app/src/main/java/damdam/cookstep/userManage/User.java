package damdam.cookstep.userManage;

import android.app.Activity;

import damdam.cookstep.setting.Preferences;

/**
 * Created by Poste on 18/07/2016.
 */
public class User {

    private static User user = null;
    private String nickname;
    private int status;
    private String idUser = "";

    private User(String idUser,String name,int status){
        this.idUser = idUser;
        this.nickname=name;
        this.status=status;
    }

    public static User getInstance(Activity activity){
        if(user==null && activity!=null)user = new User(Preferences.getSharedPreferenceString(activity,"idUser"),Preferences.getSharedPreferenceString(activity,"nickname"),Preferences.getSharedPreferenceInt(activity,"status"));
        return user;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public boolean isConnected(){
        return (!nickname.equals("none")) && (status!=-1) && (!idUser.equals(""));
    }

    public void setConnection(String idUser,String nickname, int status,Activity activity) {
        Preferences.setSharedPreferenceString(activity,"idUser",idUser);
        Preferences.setSharedPreferenceString(activity,"nickname",nickname);
        Preferences.setSharedPreferenceInt(activity,"status",status);
        setIdUser(idUser);
        setNickname(nickname);
        setStatus(status);
    }
}
