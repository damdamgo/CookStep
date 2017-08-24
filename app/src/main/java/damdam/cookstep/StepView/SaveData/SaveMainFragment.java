package damdam.cookstep.StepView.SaveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import damdam.cookstep.StepView.IngredientManage.Ingredient;
import damdam.cookstep.StepView.TrickManage.Trick;

/**
 * Created by Poste on 17/07/2016.
 */
public class SaveMainFragment {

    private ArrayList<Ingredient> listIngredient;
    private ArrayList<Trick> listTrick;
    private String source,country,time,id="",nameRecipe,idCategorie;


    public SaveMainFragment(){

    }

    public SaveMainFragment(JSONArray jsonArray){

    }

    public JSONObject getDataToSend(ArrayList<Ingredient> lIngredient, ArrayList<Trick> lTrick, String country, String time, String source,String nameRecipe,int nbStep,String idCategorie){
        JSONObject back = new JSONObject();

        if(id.equals("")){
            JSONArray jsonIngrContain = new JSONArray();
            try {
                for (Ingredient ing: lIngredient) {
                    JSONObject jsonIngr = new JSONObject();
                    jsonIngr.put("ingredient",ing.getIngredient());
                    jsonIngr.put("quantity",ing.getQuantity());
                    jsonIngrContain.put(jsonIngr);
                }
                back.put("ingredients",jsonIngrContain);

                JSONArray jsonTrickContain = new JSONArray();
                for (Trick tri: lTrick) {
                    JSONObject jsonTrick = new JSONObject();
                    jsonTrick.put("trick",tri.getTrick());
                    jsonTrickContain.put(jsonTrick);
                }
                back.put("tricks",jsonTrickContain);
                back.put("country",country);
                back.put("source",source);
                back.put("time",time);
                back.put("name",nameRecipe);
                back.put("nbStep",nbStep);
                back.put("idCategorie",idCategorie);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                back.put("id",id);
                if(!this.idCategorie.equals(idCategorie))back.put("idCategorie",idCategorie);
                back.put("nbStep",nbStep);
                if(!this.nameRecipe.equals(nameRecipe))back.put("name",nameRecipe);
                if(!this.time.equals(time))back.put("time",time);
                if(!this.source.equals(source))back.put("source",source);
                if(!this.country.equals(country))back.put("country",country);

                boolean autho = false;
                if(listIngredient.size()!=lIngredient.size())autho=true;
                else{
                    for(int i = 0 ;i<lIngredient.size();i++){
                        if(!lIngredient.get(i).equals(listIngredient.get(i))){
                            autho = true;
                            break;
                        }
                    }
                }
                if(autho){
                    JSONArray jsonIngrContain = new JSONArray();
                    for (Ingredient ing: lIngredient) {
                        JSONObject jsonIngr = new JSONObject();
                        jsonIngr.put("ingredient",ing.getIngredient());
                        jsonIngr.put("quantity",ing.getQuantity());
                        jsonIngrContain.put(jsonIngr);
                    }
                    back.put("ingredients",jsonIngrContain);
                }

                autho = false;
                if(listTrick.size()!=lTrick.size())autho=true;
                else{
                    for(int i = 0 ;i<lTrick.size();i++){
                        if(!lTrick.get(i).equals(listTrick.get(i))){
                            autho = true;
                            break;
                        }
                    }
                }
                if(autho){
                    JSONArray jsonTrickContain = new JSONArray();
                    for (Trick tri: lTrick) {
                        JSONObject jsonTrick = new JSONObject();
                        jsonTrick.put("trick",tri.getTrick());
                        jsonTrickContain.put(jsonTrick);
                    }
                    back.put("tricks",jsonTrickContain);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        listIngredient=lIngredient;
        listTrick=lTrick;
        this.country = country;
        this.source=source;
        this.time=time;
        this.nameRecipe=nameRecipe;
        this.idCategorie=idCategorie;
        return back;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
