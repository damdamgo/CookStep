package damdam.cookstep.StepView.TrickManage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import damdam.cookstep.R;


/**
 * Created by Poste on 16/07/2016.
 */
public class TrickView extends LinearLayout {

    private TrickListView callbackRemove;
    private boolean modeCreator = false;
    private Trick trick;
    private LinearLayout layoutTrick;
    private TextView textViewTrick;
    private LinearLayout layoutTrickCreator;
    private EditText editTextTrick;
    private Button buttonRemoveTrick;

    public TrickView(Context context) {
        super(context);
        init();
    }

    public TrickView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TrickView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        inflate(getContext(), R.layout.layout_trick,this);
        layoutTrick = (LinearLayout) this.findViewById(R.id.layoutTrick);
        textViewTrick = (TextView) this.findViewById(R.id.textViewTrick);
        layoutTrickCreator = (LinearLayout) this.findViewById(R.id.layoutTrickCreator);
        editTextTrick = (EditText) this.findViewById(R.id.editTextTrick);
        buttonRemoveTrick = (Button) this.findViewById(R.id.buttonRemoveTrick);

        buttonRemoveTrick.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                callbackRemove.remove(TrickView.this);
            }
        });
    }


    public void setModeCreator() {
        modeCreator = true;
        editTextTrick.setText(textViewTrick.getText());
        layoutTrick.setVisibility(GONE);
        layoutTrickCreator.setVisibility(VISIBLE);
        trick = new Trick(editTextTrick.getText().toString());
    }

    public void setModeView() {
        modeCreator = false;
        textViewTrick.setText(editTextTrick.getText());
        layoutTrick.setVisibility(VISIBLE);
        layoutTrickCreator.setVisibility(GONE);
        trick = new Trick(textViewTrick.getText().toString());
    }


    public void fixCallbackRemove(TrickListView trickListView) {
        this.callbackRemove = trickListView;
    }

    public Trick getTrick() {
        return this.trick;
    }
}

