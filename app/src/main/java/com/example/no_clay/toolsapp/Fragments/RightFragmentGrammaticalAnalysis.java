package com.example.no_clay.toolsapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.no_clay.toolsapp.GrammticalAnalysis.Element;
import com.example.no_clay.toolsapp.GrammticalAnalysis.HtmlBuilder;
import com.example.no_clay.toolsapp.GrammticalAnalysis.LL1Resolver;
import com.example.no_clay.toolsapp.GrammticalAnalysis.RuleTreeHelper;
import com.example.no_clay.toolsapp.GrammticalAnalysis.RuleTreeNode;
import com.example.no_clay.toolsapp.GrammticalAnalysis.TreeViewAdapter;
import com.example.no_clay.toolsapp.GrammticalAnalysis.TreeViewItemClickListener;
import com.example.no_clay.toolsapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private ListView mTreeView;
    private boolean isFirst = true;
    private String ruleSrc;
    private LL1Resolver mResolver;
    private int state = 0;
    public static final int ELIMINATE_LEFT_RECURSION = 0;
    public static final int GENERATE_FIRST_AND_FOLLOW = 1;
    public static final int GENERATE_FORECAST_MATRIX = 2;
    public static final int GRAMMATICAL_ANALYSIS = 3;
    public static final int GENERATE_GRAMMATICAL_TREE = 4;
    /** 树中的元素集合 */
    private ArrayList<Element> elements;
    /** 数据源元素集合 */
    private ArrayList<Element> elementsData;

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
        mTreeView = (ListView) view.findViewById(R.id.treeView);
        mNext.setOnClickListener(this);
        mTreeView.setVisibility(View.GONE);
        mShowResultWindow.setVisibility(View.VISIBLE);
    }

    @Override
    public void refresh(String input) {
        super.refresh(input);
        this.input = input;
        mTreeView.setVisibility(View.GONE);
        mShowResultWindow.setVisibility(View.VISIBLE);
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
                        int flag = mResolver.doResolverAtOnce();
                        if (flag == LL1Resolver.JUST_DOING){
                            mNext.setText("正在进行语法分析，点击进行下一步");
                        }else if (flag == LL1Resolver.SUCCESS){
                            state ++;
                            mNext.setText("语法分析完成，点击进行下一步");
                        }else if (flag == LL1Resolver.FAILED){
                            mNext.setText("语法分析失败");
                        }
                        break;
                    }
                    case GENERATE_GRAMMATICAL_TREE:{
                        mNext.setText("语法树生成完毕");
                        Toast.makeText(getContext(), "请不要点击，语法分析已经完成了", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default:{

                    }
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
                builder.appendTitleAndContent("输入的句子：", input);
                builder.appendTitleAndContent("构成语法树：", "");
                RuleTreeHelper helper = new RuleTreeHelper(mResolver.getStepRuleList(), mResolver);
                RuleTreeNode root = helper.constructRuleTree();
                builder.appendHtml(helper.getSon(root));
                break;
            }
        }

        mShowResultWindow.loadDataWithBaseURL(null, builder.toHtml(),
                "text/html", "utf-8", null);

        if (state == GENERATE_GRAMMATICAL_TREE){
            Log.d("loadlist", "showInHtml: 开始展示");
            mShowResultWindow.setVisibility(View.GONE);
            mTreeView.setVisibility(View.VISIBLE);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            init();
            TreeViewAdapter treeViewAdapter = new TreeViewAdapter(
                    elements, elementsData, inflater, getContext());
            TreeViewItemClickListener treeViewItemClickListener = new TreeViewItemClickListener(treeViewAdapter);
            mTreeView.setAdapter(treeViewAdapter);
            mTreeView.setOnItemClickListener(treeViewItemClickListener);
        }
    }

    public void getElementsData(RuleTreeNode parentNode, Element parent, ArrayList<Element> elementsData){
        if (parentNode.getSon() != null){
            List<RuleTreeNode> sons = parentNode.getSon();
            for (int i = 0; i < sons.size(); i++) {
                String temp = sons.get(i).getValue();
                if (mResolver.getRulesHelper().getvT().contains(temp)){
                    //这个子节点是叶子节点
                    Element element = new Element(temp, parent.getLevel() + 1, elementsData.size() + 1, parent.getId(), false, false);
                    elementsData.add(element);
                }else{
                    Element element = new Element(temp, parent.getLevel() + 1, elementsData.size() + 1, parent.getId(), true, false);
                    elementsData.add(element);
                    getElementsData(sons.get(i), element, elementsData);
                }
            }
        }
    }


    private void init() {
        elements = new ArrayList<Element>();
        elementsData = new ArrayList<Element>();
        RuleTreeHelper helper = new RuleTreeHelper(mResolver.getStepRuleList(), mResolver);
        RuleTreeNode root = helper.constructRuleTree();
        Element top = new Element(root.getValue() , Element.TOP_LEVEL, 0, Element.NO_PARENT, true, false);
        elements.add(top);
        getElementsData(root, top, elementsData);
//        //添加节点  -- 节点名称，节点level，节点id，父节点id，是否有子节点，是否展开
//
//        //添加最外层节点

//        //添加第一层节点
//        Element e1 = new Element("什么" , Element.TOP_LEVEL, 0, Element.NO_PARENT, true, false);
//
//        Element e2 = new Element("青岛市", Element.TOP_LEVEL + 1, 1, e1.getId(), true, false);
//        //添加第二层节点
//        Element e3 = new Element("市南区", Element.TOP_LEVEL + 2, 2, e2.getId(), true, false);
//        //添加第三层节点
//        Element e4 = new Element("香港中路", Element.TOP_LEVEL + 3, 3, e3.getId(), false, false);
//
//        //添加第一层节点
//        Element e5 = new Element("烟台市", Element.TOP_LEVEL + 1, 4, e1.getId(), true, false);
//        //添加第二层节点
//        Element e6 = new Element("芝罘区", Element.TOP_LEVEL + 2, 5, e5.getId(), true, false);
//        //添加第三层节点
//        Element e7 = new Element("凤凰台街道", Element.TOP_LEVEL + 3, 6, e6.getId(), false, false);
//
//        //添加第一层节点
//        Element e8 = new Element("威海市", Element.TOP_LEVEL + 1, 7, e1.getId(), false, false);
//
//        //添加最外层节点
//        Element e9 = new Element("广东省", Element.TOP_LEVEL, 8, Element.NO_PARENT, true, false);
//        //添加第一层节点
//        Element e10 = new Element("深圳市", Element.TOP_LEVEL + 1, 9, e9.getId(), true, false);
//        //添加第二层节点
//        Element e11 = new Element("南山区", Element.TOP_LEVEL + 2, 10, e10.getId(), true, false);
//        //添加第三层节点
//        Element e12 = new Element("深南大道", Element.TOP_LEVEL + 3, 11, e11.getId(), true, false);
//        //添加第四层节点
//        Element e13 = new Element("10000号", Element.TOP_LEVEL + 4, 12, e12.getId(), false, false);
////添加初始树元素
//        elements.add(e1);
//        elements.add(e9);
//        //创建数据源
//        elementsData.add(e1);
//        elementsData.add(e2);
//        elementsData.add(e3);
//        elementsData.add(e4);
//        elementsData.add(e5);
//        elementsData.add(e6);
//        elementsData.add(e7);
//        elementsData.add(e8);
//        elementsData.add(e9);
//        elementsData.add(e10);
//        elementsData.add(e11);
//        elementsData.add(e12);
//        elementsData.add(e13);
    }
}
