package com.example.no_clay.toolsapp.GrammticalAnalysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

/**
 * Created by nocla on 2017/5/5.
 */
public class LL1Resolver {
    private String data;
    private RulesHelper rulesHelper;
    private Stack<String> resolverStack;
    private int pos;
    //0代表正在分析
    //1代表分析成功
    //代表分析失败
    private List<Step> steps;

    public static final int SUCCESS = 2;
    public static final int JUST_DOING = 0;
    public static final int FAILED = -1;
    private int state = JUST_DOING;
    //第一行为分析的数据之后为测试的数据
    public LL1Resolver(String data) {
        String[] datas = data.split("\n");
        this.data = datas[0];
        String[] rules = new String[datas.length - 1];
        for (int i = 1; i < datas.length; i++) {
            rules[i - 1] = datas[i];
        }
        rulesHelper = new RulesHelper(rules);
        rulesHelper.createFirstAndFollow();
        rulesHelper.initForecastMatrix();
        resolverStack = new Stack<>();
        resolverStack.push("#");
        resolverStack.push(rulesHelper.getStartSignal());
        steps = new ArrayList<>();
        steps.add(new Step(Arrays.toString(resolverStack.toArray()),
                datas[0], null));
    }
    public int doResolverAtOnce(){
        if (steps == null){
            steps = new ArrayList<>();
        }
        if (state != JUST_DOING){
            return state;
        }else if (state == 1){
            state ++;
        }
        char inputA = data.charAt(pos);
        String stackX = resolverStack.peek();
        if (stackX.equals(inputA + "") && inputA == '#') {
            //1.若X == a == '#'，则代表分析成功，停止分析过程
            state = SUCCESS;
            steps.add(new Step(Arrays.toString(resolverStack.toArray()),
                    data.substring(pos),
                    null));
            return state;
        }else if (stackX.equals(inputA + "") && inputA != '#'){
            //2.若x == a != '#' 则将X弹出，并让a指向下一个输入符号
            resolverStack.pop();
            pos ++;
            steps.add(new Step(Arrays.toString(resolverStack.toArray()),
                    data.substring(pos),
                    null));
            return state;
        }else if (rulesHelper.getvN().contains(stackX)){
            //3.若X是一个非终结符，则查看分析表M，若M[A,a]中存放着关于X的一个产生式，那么，首先将X弹出，
            //然后将产生式右部符号串按照反序一一压栈，若右部符号为'$'，则不进行压栈，若M[A,a]中存放出错
            //标志则调用诊断程序
            Rule next = rulesHelper.getRuleByForecastMatrix(stackX, inputA + "");
            if (next == null){
                steps.add(new Step(Arrays.toString(resolverStack.toArray()),
                        data.substring(pos),
                        null));
                state = FAILED;
                return state;
            }else {
                Object[] rights = next.getRights().toArray();
                resolverStack.pop();
                for (int i = rights.length - 1; i >= 0; i--) {
                    if (((String)(rights[i])).equals("$")){
                        //do nothing
                    }else{
                        resolverStack.push((String) rights[i]);
                    }
                }
                steps.add(new Step(Arrays.toString(resolverStack.toArray()),
                        data.substring(pos),
                        next));
            }
        }
        return state;
    }

    public String getData() {
        return data;
    }

    public RulesHelper getRulesHelper() {
        return rulesHelper;
    }

    public String[][] getSteps() {
        String[][] result = new String[steps.size() + 1][4];
        result[0][0] = "步骤";
        result[0][1] = "符号栈";
        result[0][2] = "输入串";
        result[0][3] = "所用产生式";
        for (int i = 1; i < steps.size() + 1; i++) {
            Step temp = steps.get(i - 1);
            result[i][0] = (i - 1) + "";
            result[i][1] = temp.getStack();
            result[i][2] = temp.getInput();
            result[i][3] = temp.getRule() == null ? "" : temp.getRule().getValue();
        }
        return result;
    }

    public Stack<String> getResolverStack() {
        return resolverStack;
    }

    public int getPos() {
        return pos;
    }

    public int getState() {
        return state;
    }

    public int getStepCount(){
        return steps.size();
    }

    static class Step{
        private String stack;
        private String input;
        private Rule mRule;

        public Step(String stack, String input, Rule rule) {
            this.stack = stack;
            this.input = input;
            mRule = rule;
        }

        public String getStack() {
            return stack;
        }

        public String getInput() {
            return input;
        }

        public Rule getRule() {
            return mRule;
        }
    }

    public List<Rule> getStepRuleList(){
        List<Rule> rules = new ArrayList<>();
        for (int i = 0; i < steps.size(); i++) {
            if (steps.get(i).getRule() != null){
                rules.add(steps.get(i).getRule());
            }
        }
        return rules;
    }
}
