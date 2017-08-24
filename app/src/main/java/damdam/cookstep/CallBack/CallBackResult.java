package damdam.cookstep.CallBack;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Poste on 12/07/2016.
 */
public class CallBackResult {

    private int errorCode = 0;
    private JSONArray mJsonArray = null;

    public CallBackResult(int errorCode,JSONArray mJsonArray){
        this.errorCode = errorCode;
        this.mJsonArray = mJsonArray;
    }

    public JSONArray getmJsonArray() {
        return mJsonArray;
    }

    public int getErrorCode() {
        return errorCode;
    }
}
