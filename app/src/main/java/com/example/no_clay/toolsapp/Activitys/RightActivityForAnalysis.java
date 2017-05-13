package com.example.no_clay.toolsapp.Activitys;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.no_clay.toolsapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RightActivityForAnalysis extends AppCompatActivity{

    @BindView(R.id.showResultWindow)
    TextView mShowResultWindow;
    @BindView(R.id.buttonFinal)
    Button mButtonFinal;
    @BindView(R.id.buttonKey)
    Button mButtonKey;
    @BindView(R.id.buttonOperation)
    Button mButtonOperation;
    @BindView(R.id.buttonMember)
    Button mButtonMember;
    private String data = null;
    List<String> finals = new ArrayList<>();
    List<String> keys = new ArrayList<>();
    List<String> operations = new ArrayList<>();
    List<String> members = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.right_fragment);
        ButterKnife.bind(this);
        getData();


    }

    public void getData() {
        data = getIntent().getStringExtra("data");
        chooseClick(0);
    }

    public void analysis(String data){

    }

    public void clearChoosenState(){
        mButtonFinal.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonFinal.setTextColor(getResources().getColor(R.color.white));
        mButtonKey.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonKey.setTextColor(getResources().getColor(R.color.white));
        mButtonMember.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonMember.setTextColor(getResources().getColor(R.color.white));
        mButtonOperation.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonOperation.setTextColor(getResources().getColor(R.color.white));
    }
    public void chooseClick(int position){
        clearChoosenState();
        switch (position){
            case 0:{
                mButtonFinal.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonFinal.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
            case 1:{
                mButtonKey.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonKey.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
            case 2:{
                mButtonOperation.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonOperation.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
            case 3:{
                mButtonMember.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonMember.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
        }
    }

    @OnClick({R.id.buttonFinal, R.id.buttonKey, R.id.buttonOperation, R.id.buttonMember})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFinal:
                chooseClick(0);
                break;
            case R.id.buttonKey:
                chooseClick(1);
                break;
            case R.id.buttonOperation:
                chooseClick(2);
                break;
            case R.id.buttonMember:
                chooseClick(3);
                break;
        }
    }
}
