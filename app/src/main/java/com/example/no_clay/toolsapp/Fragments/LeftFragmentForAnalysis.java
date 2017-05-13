package com.example.no_clay.toolsapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.no_clay.toolsapp.Activitys.RightActivityForAnalysis;
import com.example.no_clay.toolsapp.R;

import butterknife.ButterKnife;

/**
 * Created by no_clay on 2017/3/2.
 */

public class LeftFragmentForAnalysis extends Fragment implements View.OnClickListener {

    private View mainView;
    private boolean isTwoPane = false;
    private View view;
    private ImageView mClearAllString;
    /**
     * 请输入要分析的字符串
     */
    private EditText mInputString;
    /**
     * 词法分析
     */
    private Button mAnalysisString;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.left_fragment, container, false);
        ButterKnife.bind(this, mainView);
        initView();
        return mainView;
    }

    private void initView() {
        mClearAllString = (ImageView) mainView.findViewById(R.id.clearAllString);
        mInputString = (EditText) mainView.findViewById(R.id.inputString);
        mAnalysisString = (Button) mainView.findViewById(R.id.analysisString);
        mAnalysisString.setOnClickListener(this);
        mClearAllString.setOnClickListener(this);
        mClearAllString.setVisibility(View.INVISIBLE);
        mInputString.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (count == 0) {
                    mClearAllString.setVisibility(View.INVISIBLE);
                } else {
                    mClearAllString.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count == 0) {
                    mClearAllString.setVisibility(View.INVISIBLE);
                } else {
                    mClearAllString.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity().findViewById(R.id.right_fragment) == null) {
            //单页模式
            isTwoPane = false;
        } else {
            isTwoPane = true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clearAllString:
                mInputString.setText("");
                break;
            case R.id.analysisString:
                if (mInputString.getText().toString().length() <= 0) {
                    return;
                }
                if (isTwoPane) {
                    //双页模式
                    Log.d("test", "onClick: send");
                    FragmentManager manager = getFragmentManager();
                    ReFreshFragment fragment =
                            (ReFreshFragment) manager.findFragmentById(R.id.right_fragment);
                    fragment.refresh(mInputString.getText().toString());
                } else {
                    Intent intent = new Intent(getContext(), RightActivityForAnalysis.class);
                    intent.putExtra("data", mInputString.getText().toString());
                    getActivity().startActivity(intent);
                }
                break;
        }
    }
}
