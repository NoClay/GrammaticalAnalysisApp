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
import com.example.no_clay.toolsapp.GrammticalAnalysis.LL1Resolver;

import java.util.Arrays;

/**
 * Created by noclay on 2017/5/5.
 */

public class RightFragmentGrammaticalAnalysis extends ReFreshFragment implements View.OnClickListener{


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
    private boolean isFirst = true;
    private String ruleSrc;
    private LL1Resolver mResolver;
    private int state = 0;
    public static final int ELIMINATE_LEFT_RECURSION = 0;
    public static final int GENERATE_FIRST_AND_FOLLOW = 1;
    public static final int GENERATE_FORECAST_MATRIX = 2;
    public static final int GRAMMATICAL_ANALYSIS = 3;
    public static final int GENERATE_GRAMMATICAL_TREE = 4;

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
        state = ELIMINATE_LEFT_RECURSION;
        int pos = input.indexOf("#");
        ruleSrc = input.substring(pos + 1);  
        mResolver = new LL1Resolver(input);
        showInHtml();
        mNext.setText("已经消除左递归，点击进行下一步");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:{
                switch (state){
                    case ELIMINATE_LEFT_RECURSION:{
                        state ++;
                        mNext.setText("First和Follow集已经生成，点击进行下一步");
                        break;
                    }
                    case GENERATE_FIRST_AND_FOLLOW:{
                        state ++;
                        mNext.setText("预测分析表已经生成，点击进行下一步");
                        break;
                    }
                    case GENERATE_FORECAST_MATRIX:{
                        state ++;
                        mNext.setText("正在进行语法分析，点击进行下一步");
                        break;
                    }
                    case GRAMMATICAL_ANALYSIS:{
                        if (mResolver.doResolverAtOnce() == LL1Resolver.JUST_DOING){
                            mNext.setText("正在进行语法分析，点击进行下一步");
                        }else if (mResolver.doResolverAtOnce() == LL1Resolver.SUCCESS){
                            state ++;
                            mNext.setText("语法分析完成，点击进行下一步");
                        }else{
                            mNext.setText("语法分析失败");
                        }
                        break;
                    }
                    case GENERATE_GRAMMATICAL_TREE:{
                        state ++;
                        mNext.setText("语法树生成完毕");
                        break;
                    }
                    default:Toast.makeText(getContext(), "请不要点击，语法分析已经完成了", Toast.LENGTH_SHORT).show();

                }
                showInHtml();
                break;
            }
        }
    }

    public void showInHtml(){
        HtmlBuilder builder = new HtmlBuilder();
        switch (state){
            case ELIMINATE_LEFT_RECURSION:{
                builder.appendTitleAndContent("原来的语法规则：", ruleSrc);
                StringBuilder b = new StringBuilder();
                for (int i = 0; i < mResolver.getRulesHelper().rules.size(); i++) {
                    b.append(mResolver.getRulesHelper().rules.get(i).getValue() + "\n");
                }
                builder.appendTitleAndContent("消除左递归并分解后的语法规则：", b.toString());
                builder.appendTitleAndContent("非终结符：", Arrays.toString(mResolver.getRulesHelper().vN.toArray()));
                builder.appendTitleAndContent("终结符：", Arrays.toString(mResolver.getRulesHelper().vT.toArray()));
                break;
            }
            case GENERATE_FIRST_AND_FOLLOW:{
                builder.appendTitleAndContent("First集和Follow集：", "");
                Object[] vns = mResolver.getRulesHelper().vN.toArray();
                String[][] contents = new String[vns.length][3];
                for (int i = 0; i < vns.length; i++) {
                    contents[i][0] = (String) vns[i];
                    contents[i][1] = Arrays.toString(mResolver.getRulesHelper().firsts.get(vns[i]).toArray());
                    contents[i][2] = Arrays.toString(mResolver.getRulesHelper().follows.get(vns[i]).toArray());
                }
                String[] titles = {
                        "非终结符", "First集", "Follow集",
                };
                builder.appendExcel(3, mResolver.getRulesHelper().vN.size(),
                        titles,
                        contents
                );
                break;
            }
            case GENERATE_FORECAST_MATRIX:{
                builder.appendTitleAndContent("预测分析表：", "");
                builder.appendExcel(
                        mResolver.getRulesHelper().getvT().size() + 2,
                        mResolver.getRulesHelper().getvN().size() + 1,
                        mResolver.getRulesHelper().getForecastMatrix());
                break;
            }
            case GRAMMATICAL_ANALYSIS:{
                builder.appendExcel(4, mResolver.getStepCount() + 1,
                        mResolver.getSteps());
                break;
            }
            case GENERATE_GRAMMATICAL_TREE:{
                break;
            }
        }

        mShowResultWindow.loadDataWithBaseURL(null, builder.toHtml(),
                "text/html", "utf-8", null);
    }
}
