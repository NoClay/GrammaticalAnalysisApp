package com.example.no_clay.toolsapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;

import com.example.no_clay.toolsapp.R;
import com.example.no_clay.toolsapp.GrammticalAnalysis.HtmlBuilder;
import com.example.no_clay.toolsapp.GrammticalAnalysis.RuleHelper;

import java.util.Arrays;

/**
 * Created by noclay on 2017/5/4.
 */

public class RightFragmentCreateFirstAndFollow extends ReFreshFragment implements View.OnClickListener {


    private View view;
    /**
     * 等待分析
     */
    private WebView mShowResultWindow;
    /**
     * 下一步
     */
    private Button mNext;
    private String input;
    private boolean created = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.right_create_first_and_follow, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        mShowResultWindow = (WebView) view.findViewById(R.id.showResultWindow);
        mNext = (Button) view.findViewById(R.id.next);
        mNext.setOnClickListener(this);
    }

    @Override
    public void refresh(String input) {
        super.refresh(input);
        this.input = input;
        RuleHelper.initAll(input.split("\n"));
        showInHtml();
        created = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:{
                if (!created){
                    created = !RuleHelper.initFirstAndFollowOnce();
                    showInHtml();
                }else{
                    Toast.makeText(getContext(), "已经生成了", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    public void showInHtml(){
        HtmlBuilder builder = new HtmlBuilder();
        builder.appendTitleAndContent("原来的语法规则：", input);
        StringBuilder b = new StringBuilder();
        for (int i = 0; i < RuleHelper.rules.size(); i++) {
            b.append(RuleHelper.rules.get(i).getValue() + "\n");
        }
        builder.appendTitleAndContent("分解后的语法规则：", b.toString());
        builder.appendTitleAndContent("非终结符：", Arrays.toString(RuleHelper.vN.toArray()));
        builder.appendTitleAndContent("终结符：", Arrays.toString(RuleHelper.vT.toArray()));
        builder.appendTitleAndContent("First集和Follow集：", "");
        Object[] vns = RuleHelper.vN.toArray();
        String[][] contents = new String[vns.length][3];
        for (int i = 0; i < vns.length; i++) {
            contents[i][0] = (String) vns[i];
            contents[i][1] = Arrays.toString(RuleHelper.firsts.get(vns[i]).toArray());
            contents[i][2] = Arrays.toString(RuleHelper.follows.get(vns[i]).toArray());
        }
        String[] titles = {
            "非终结符", "First集", "Follow集",
        };
        builder.appendExcel(3, RuleHelper.vN.size(),
                titles,
                contents
                );
        mShowResultWindow.loadDataWithBaseURL(null, builder.toHtml(),
                "text/html", "utf-8", null);
    }
}
