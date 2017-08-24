package damdam.cookstep.StepView.SaveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import damdam.cookstep.StepView.TrickManage.Trick;

/**
 * Created by Poste on 17/07/2016.
 */
public class SaveStep {

    private ArrayList<Trick> listTrick;
    private String stepText,id="";

    public SaveStep(){

    }

    public JSONObject getDataToSend(ArrayList<Trick> lTricks, String stepText, int indexFragmentStep, String idRecipe){
        JSONObject back = new JSONObject();
        try {
            if (id.equals("")) {

                back.put("stepText",stepText);


                JSONArray jsonTrickContain = new JSONArray();
                for (Trick tri: lTricks) {
                    JSONObject jsonTrick = new JSONObject();
                    jsonTrick.put("trick",tri.getTrick());
                    jsonTrickContain.put(jsonTrick);
                }
                back.put("tricks",jsonTrickContain);

            }
            else{
                if(!this.stepText.equals(stepText)) back.put("stepText",stepText);

                boolean autho = false;
                if(listTrick.size()!=lTricks.size())autho=true;
                else{
                    for(int i = 0 ;i<lTricks.size();i++){
                        if(!lTricks.get(i).equals(listTrick.get(i))){
                            autho = true;
                            break;
                        }
                    }
                }
                if(autho){
                    JSONArray jsonTrickContain = new JSONArray();
                    for (Trick tri: lTricks) {
                        JSONObject jsonTrick = new JSONObject();
                        jsonTrick.put("trick",tri.getTrick());
                        jsonTrickContain.put(jsonTrick);
                    }
                    back.put("tricks",jsonTrickContain);
                }
            }
            back.put("idRecipe",idRecipe);
            back.put("indexStep",indexFragmentStep);

            this.listTrick=lTricks;
            this.stepText=stepText;

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return back;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
