package com.example.no_clay.toolsapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.no_clay.toolsapp.R;
import com.example.no_clay.toolsapp.GrammticalAnalysis.AnalysisWord;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by no_clay on 2017/3/2.
 */

public class RightFragmentForAnalysis extends ReFreshFragment {
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
    private View mainView;
    AnalysisWord mAnalysisWord;
    boolean result = false;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.right_fragment, container, false);
        ButterKnife.bind(this, mainView);
        mAnalysisWord = new AnalysisWord();
        chooseClick(0);
        return mainView;
    }

    @Override
    public void refresh(String data) {
        Log.d("test", "refresh: receive");
        mShowResultWindow.setText(data);
        mAnalysisWord.init();
        chooseClick(0);
        result = mAnalysisWord.doBackground(data);
        if (result) {
            //成功无错误
            Toast.makeText(getContext(), mAnalysisWord.getContent(), Toast.LENGTH_SHORT).show();
            mShowResultWindow.setText(mAnalysisWord.getFinals());
        } else {
            mShowResultWindow.setText("错误");
        }
    }

    @OnClick({R.id.buttonFinal, R.id.buttonKey, R.id.buttonOperation, R.id.buttonMember})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonFinal:
                chooseClick(0);
                if (result) {
                    //成功无错误
                    mShowResultWindow.setText(mAnalysisWord.getFinals());
                } else {
                    mShowResultWindow.setText("错误");
                }
                break;
            case R.id.buttonKey:
                chooseClick(1);
                if (result) {
                    //成功无错误
                    mShowResultWindow.setText(mAnalysisWord.getKeys());
                } else {
                    mShowResultWindow.setText("错误");
                }
                break;
            case R.id.buttonOperation:
                chooseClick(2);
                if (result) {
                    //成功无错误
                    mShowResultWindow.setText(mAnalysisWord.getOperations());
                } else {
                    mShowResultWindow.setText("错误");
                }
                break;
            case R.id.buttonMember:
                chooseClick(3);
                if (result) {
                    //成功无错误
                    mShowResultWindow.setText(mAnalysisWord.getMarks());
                } else {
                    mShowResultWindow.setText("错误");
                }
                break;
        }
    }

    public void clearChoosenState() {
        mButtonFinal.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonFinal.setTextColor(getResources().getColor(R.color.white));
        mButtonKey.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonKey.setTextColor(getResources().getColor(R.color.white));
        mButtonMember.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonMember.setTextColor(getResources().getColor(R.color.white));
        mButtonOperation.setBackgroundColor(getResources().getColor(R.color.lightBlue));
        mButtonOperation.setTextColor(getResources().getColor(R.color.white));
    }

    public void chooseClick(int position) {
        clearChoosenState();
        switch (position) {
            case 0: {
                mButtonFinal.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonFinal.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
            case 1: {
                mButtonKey.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonKey.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
            case 2: {
                mButtonOperation.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonOperation.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
            case 3: {
                mButtonMember.setBackgroundColor(getResources().getColor(R.color.white));
                mButtonMember.setTextColor(getResources().getColor(R.color.lightBlue));
                break;
            }
        }
    }
}
