package damdam.cookstep.StepView.TrickManage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import org.json.JSONArray;

import java.util.ArrayList;

import damdam.cookstep.R;

/**
 * Created by Poste on 16/07/2016.
 */
public class TrickListView extends LinearLayout {

    private boolean modeCreator = false;
    private ArrayList<TrickView> listTricks = new ArrayList<>();

    private Button buttonAddTrick;
    private LinearLayout layoutContainListTrick;

    public TrickListView(Context context) {
        super(context);
        init();
    }

    public TrickListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrickListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.layout_list_trick,this);
        buttonAddTrick = (Button) this.findViewById(R.id.buttonAddTrick);
        layoutContainListTrick = (LinearLayout) this.findViewById(R.id.layoutContainListTrick);

        buttonAddTrick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TrickView newTrick = new TrickView(getContext());
                newTrick.fixCallbackRemove(TrickListView.this);
                listTricks.add(newTrick);
                layoutContainListTrick.addView(newTrick);
                if(modeCreator)newTrick.setModeCreator();
            }
        });
    }


    public void setModeCreator(){
        modeCreator = true;
        buttonAddTrick.setVisibility(VISIBLE);
        for (TrickView trickView: listTricks) {
            trickView.setModeCreator();
        }

    }

    public void setModeView(){
        modeCreator = false;
        buttonAddTrick.setVisibility(GONE);
        for (TrickView trickView: listTricks) {
            trickView.setModeView();
        }
    }

    public void setData(JSONArray jsonArray){

    }

    public void remove(TrickView trickView) {
        //TODO delete from list
        layoutContainListTrick.removeView(trickView);
        listTricks.remove(trickView);
    }

    public ArrayList<Trick> getListTrick() {
        ArrayList<Trick> listTrick = new ArrayList<>();
        for (TrickView trickView: listTricks) {
            listTrick.add(trickView.getTrick());
        }
        return listTrick;
    }
}
