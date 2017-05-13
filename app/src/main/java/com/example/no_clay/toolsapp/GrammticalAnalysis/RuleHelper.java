package com.example.no_clay.toolsapp.GrammticalAnalysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nocla on 2017/5/2.
 */
public class RuleHelper {
    public static Map<String, Set<String>> firsts;
    public static Map<String, Set<String>> follows;
    //终结符
    public static Set<String> vT = new HashSet<>();
    //非中介符
    public static Set<String> vN = new HashSet<>();
    public static List<Rule> rules;
    public static String startSignal;
    public static boolean firstInited = false;
    public static boolean followInited = false;

    public static void initAll(String[] ruleArray) {
        rules = new ArrayList<>();
        firsts = new HashMap<>();
        follows = new HashMap<>();
        vT = new HashSet<>();
        vN = new HashSet<>();
        firstInited = false;
        followInited = false;
        initRules(ruleArray);
        initStartSignal();
        initVTAndVN();
        initFirstAndFollow();
    }

    private static void initStartSignal() {
        if (rules.size() > 0) {
            //代表有规则
            startSignal = rules.get(0).getLeft();
        }
    }

    private static void initRules(String[] ruleArray) {
        for (int i = 0; i < ruleArray.length; i++) {
            rules.addAll(crateRuleFromString(ruleArray[i]));
        }
    }

    public static List<Rule> crateRuleFromString(String rule) {
        List<Rule> results = new ArrayList<>();
        int pos = rule.indexOf("->");
        if (pos < 0) {
            return results;
        } else {
            String left = rule.substring(0, pos);
            String[] rights = rule.substring(pos + 2).split("[|]");
            for (int i = 0; i < rights.length; i++) {
                results.add(new Rule(left + "->" + rights[i]));
            }
        }
        return results;
    }

    private static void initVTAndVN() {
        for (int i = 0; i < rules.size(); i++) {
            Rule temp = rules.get(i);
            vT.addAll(temp.getVT());
            vN.add(temp.left);
        }
    }

    public void createFirstAndFollow(){
        while (initFirstAndFollowOnce());
    }

    private static void initFirstAndFollow() {
        //1.若X属于Vt,则first(X) = {X}
        Iterator<String> it = vT.iterator();
        while (it.hasNext()) {
            Set temp = new HashSet();
            String key = it.next();
            temp.add(key);
            firsts.put(key, temp);
        }
        it = vN.iterator();
        while (it.hasNext()) {
            Set temp = new HashSet();
            String key = it.next();
            firsts.put(key, temp);
        }
        //2.若X属于VN, 且有产生式X->a....,则把a加入到First(X)中，若X->$也是一条产生式，则把$也加入到First（X）
        for (int i = 0; i < rules.size(); i++) {
            Rule rule = rules.get(i);
            Set temp = firsts.get(rule.left);
            String v = rule.getFirstVT();
            if (v != null) {
                temp.add(v);
            }
        }

        //1.对于文法的开始符号S，置#于Follow(S)中
        Set<String> temp = new HashSet<>();
        temp.add("#");
        it = vT.iterator();
        while (it.hasNext()) {
            String key = it.next();
            follows.put(key, new HashSet<String>());
        }
        it = vN.iterator();
        while (it.hasNext()) {
            String key = it.next();
            if (key.equals(startSignal)) {
                follows.put(startSignal, temp);
            } else {
                follows.put(key, new HashSet<String>());
            }
        }
    }

    /**
     * 初始化完毕后就返回false
     *
     * @return
     */
    public static boolean initFirstAndFollowOnce() {
        if (firstInited && followInited) {
            return false;
        }
        int startCount, endCount;
        if (!firstInited) {
            //First还没有初始化完毕
            //3.若X->Y...是一个产生式且有Y属于VN， 则把First(Y)中所有非$元素都加入到First(X),
            //若X->Y1Y2Y3...Yk是一个产生式，Y1,....Yk都是非终结符，而且对于任何1<=j<=i-1,First(Yj)都含有$(即Y1....Yi-1=>$)
            //则把First(Yi)中的所有非$元素都加入到First(X)，特别的，把所有First(Yj)均含有$,j=1,2,3....k，则把$加入到First(X)中
            startCount = getMapSetCount(firsts);
            for (int i = 0; i < rules.size(); i++) {
                Rule rule = rules.get(i);
                Set temp = firsts.get(rule.left);
                String v = rule.getFirstVT();
                if (v == null) {
                    if (rule.isAllVN()) {
                        //全部是非终结符
                        int flagI = -1;
                        for (int j = 0; j < rule.getRights().size(); j++) {
                            Set<String> result = firsts.get(rule.getRights().get(j));
                            if (result != null && result.contains("$")) {
                                flagI = j;
                            } else {
                                break;
                            }
                        }
                        if (flagI == -1) {
                            //从开始就不包含$
                            temp.addAll(firsts.get(rule.getRights().get(0)));
                        } else if (flagI >= rule.getRights().size() - 1) {
                            //都包含$
                            temp.add("$");
                        } else {
                            //一部分包含
                            temp.addAll(getSetExceptDest(firsts.get(rule.getRights().get(flagI + 1)), "$"));
                        }
                    } else {
                        String firstVn = rule.getFirstVN();
                        temp.addAll(getSetExceptDest(firsts.get(firstVn), "$"));
                    }
                }
            }
            endCount = getMapSetCount(firsts);
            if (endCount != startCount) {
                return true;
            } else {
                firstInited = true;
            }
        }

        if (!followInited){
            //Follow没有初始化完毕
            Set<String> temp = new HashSet<>();
            startCount = getMapSetCount(follows);

                for (int i = 0; i < rules.size(); i++) {
                    Rule rule = rules.get(i);
                    List<String> rightArray = rule.getRights();
                    String last, now;
                    if (rightArray.size() <= 0) {
                        //没有右部
                    } else if (rightArray.size() == 1) {
                        //右部只有一个符号
                        last = rightArray.get(0);
                        if (vN.contains(last)) {
                            temp = follows.get(last);
                            temp.add("#");
                            temp.addAll(follows.get(rule.left));
                        }
                    } else {
                        last = rightArray.get(0);
                        for (int j = 1; j < rightArray.size(); j++) {
                            now = rightArray.get(j);
                            //遍历在这里开始
                            temp = follows.get(last);
                            if (vN.contains(last) && vT.contains(now)) {
                                temp.add(now);
                            } else if (vN.contains(last) && vN.contains(now)) {
                                Set<String> pFirst = firsts.get(now);
                                temp.addAll(getSetExceptDest(pFirst, "$"));
                                if (pFirst.contains("$")) {
                                    temp.addAll(follows.get(rule.left));
                                }
                            }
                            //遍历在这里结束
                            last = now;
                        }
                        String end = rightArray.get(rightArray.size() - 1);
                        if (vN.contains(end)) {
                            temp = follows.get(end);
                            temp.add("#");
                            temp.addAll(follows.get(rule.left));
                        }
                    }
                }
                endCount = getMapSetCount(follows);
            if (endCount != startCount) {
                return true;
            }else{
                followInited = true;
            }
        }
        return false;
    }

    /**
     * 1. 直接收取：注意产生式右部的每一个形如“…Ua…”的组合，把a直接收入到Follow(U)中。因a是紧跟在U
     * 后的终结符。
     * 2．直接收取：对形如“…UP…”(P是非终结符)的组合，把First(P)直接收入到Follow(U)中【在这里，如果
     * First（P）中有空字符，那么就要把左部（假设是S）的Follow（S）送入到Follow（U）中。还有就是
     * Follow集中是没有空字符的】。
     * 3. 直接收取：若S－>…U，即以U结尾，则#∈Follow(U)
     * 4．*反复传送：对形如U－>…P的产生式（其中P是非终结符），应把Follow(U)中的全部内容传送到
     * Follow(P)中。
     */


    private static int getMapSetCount(Map<String, Set<String>> dest) {
        int count = 0;
        Iterator<Map.Entry<String, Set<String>>> it = dest.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, Set<String>> entry = it.next();
            count += entry.getValue().size();
        }
        return count;
    }


    private static Set<String> getSetExceptDest(Set<String> src, String dest) {
        if (src == null) {
            return null;
        }
        Set<String> result = new HashSet<>();
        Iterator<String> it = src.iterator();
        while (it.hasNext()) {
            String temp = it.next();
            if (!temp.equals(dest)) {
                result.add(temp);
            }
        }
        return result;
    }
}
