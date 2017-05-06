package com.example.no_clay.toolsapp.GrammticalAnalysis;

import java.util.*;

/**
 * Created by nocla on 2017/5/6.
 */
public class EliminateLeftRecursion {
    private String[] rules;
    private Map<String, Set<Rule>> ruleMap;
    private String startSignal;
    private List<String> lefts;
    private List<Rule> ruleList;

    public String getStartSignal() {
        return startSignal;
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public EliminateLeftRecursion(String[] rules) {
        this.rules = rules;
        ruleMap = new HashMap<>();
        lefts = new ArrayList<>();
        ruleList = new ArrayList<>();
        initRuleMap();
        eliminateLeftRecursion();
    }

    private void initRuleMap() {
        for (int i = 0; i < rules.length; i++) {
            Set<Rule> temp = new HashSet<>();
            List<Rule> list = RulesHelper.crateRuleFromString(rules[i]);
            temp.addAll(list);
            String left = list.get(0).left;
            if (i == 0) {
                startSignal = left;
            }
            ruleMap.put(left, temp);
            if (!lefts.contains(left)) {
                lefts.add(left);
            }
        }
    }

    //因为利用任意顺序来进行替换候选，则假设最后一个为开始符号
    private void eliminateLeftRecursion() {
        //随便取一种顺序排列成P1,P2,P3...Pn
        for (int i = 0; i < lefts.size(); i++) {
            for (int j = 0; j <= i - 1; j++) {
                doInBackground(lefts.get(i), lefts.get(j));
            }
        }
        clearDirectLeftRecursion();
        clearOtherRule();
    }

    private void clearOtherRule() {
        Set<String> vns = new HashSet<>();
        int startCount = vns.size();
        vns.add(startSignal);
        do {
            startCount = vns.size();
            Object[] vnArr = vns.toArray();
            for (int i = 0; i < vnArr.length; i++) {
                Iterator<Rule> iterator = ruleMap.get(vnArr[i]).iterator();
                while (iterator.hasNext()) {
                    vns.addAll(iterator.next().getVN());
                }
            }
        } while (startCount != vns.size());
        Object[] vnArr = vns.toArray();
        for (int i = 0; i < vnArr.length; i++) {
            ruleList.addAll(ruleMap.get(vnArr[i]));
        }
    }

    private void clearDirectLeftRecursion() {
        for (int i = 0; i < lefts.size(); i++) {
            List<Rule> rules = new ArrayList<>();
            rules.addAll(ruleMap.get(lefts.get(i)));
            List<Rule> trues = new ArrayList<>();
            List<Rule> falses = new ArrayList<>();
            for (int j = 0; j < rules.size(); j++) {
                Rule temp = rules.get(j);
                String firstvn = temp.getFirstVN();
                if (firstvn != null && firstvn.equals(lefts.get(i))) {
                    //直接左递归
                    falses.add(temp);
                } else {
                    trues.add(temp);
                }
            }
            if (falses.size() != 0) {
                //有直接左递归
                Set<Rule> result = new HashSet<>();
                for (int j = 0; j < trues.size(); j++) {
                    Rule temp = trues.get(j);
                    result.add(new Rule(temp.left + "->" + temp.right + temp.left + "'"));
                }
                ruleMap.put(lefts.get(i), result);
                result = new HashSet<>();
                for (int j = 0; j < falses.size(); j++) {
                    Rule temp = falses.get(j);
                    result.add(new Rule(temp.left + "'->" + temp.getRightExceptFirstVN() + temp.left + "'"));
                }
                result.add(new Rule(lefts.get(i) + "'->" + "$"));
                ruleMap.put(lefts.get(i) + "'", result);
            }
        }
    }

    private void doInBackground(Object lefti, Object leftj) {
        Object[] ruleI = ruleMap.get(lefti).toArray();
        for (int i = 0; i < ruleI.length; i++) {
            String firstVN = ((Rule) ruleI[i]).getFirstVN();
            if (firstVN != null && ((String) leftj).equals(firstVN)) {
                ruleMap.get(lefti).remove(ruleI[i]);
                Object[] ruleJ = ruleMap.get(leftj).toArray();
                for (int j = 0; j < ruleJ.length; j++) {
                    ruleMap.get(lefti).add(new Rule(
                            lefti + "->" + ((Rule) ruleJ[j]).getRight() + ((Rule) ruleI[i]).getRightExceptFirstVN())
                    );
                }
            }
        }
    }

//    public void test() {
//        Set<Map.Entry<String, Set<Rule>>> sets = ruleMap.entrySet();
//        Iterator<Map.Entry<String, Set<Rule>>> it = sets.iterator();
//        while (it.hasNext()) {
//            Map.Entry<String, Set<Rule>> entry = it.next();
//            System.out.println(entry.getKey() + "\t" + Arrays.toString(entry.getValue().toArray()));
//        }
//    }


}
