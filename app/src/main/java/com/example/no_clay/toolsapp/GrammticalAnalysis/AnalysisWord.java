package com.example.no_clay.toolsapp.GrammticalAnalysis;

import com.example.no_clay.toolsapp.Utils.MyConstants;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by nocla on 2017/4/5.
 */

public class AnalysisWord {

    HashMap<String, Integer> markMap;
    HashMap<String, Integer> operationMap;
    HashMap<String, Integer> finalMap;
    HashMap<String, Integer> keyMap;
    StringBuilder content;

    public AnalysisWord() {
        init();
    }

    public void init(){
        markMap = new HashMap<>();
        operationMap = new HashMap<>();
        finalMap = new HashMap<>();
        keyMap = new HashMap<>();
        content = new StringBuilder();
    }

    public boolean doBackground(String data) {
        StringBuilder strToken = new StringBuilder();
        char nowState;
        char lastState = ' ';
        char ch = ' ';
        boolean error = false;
        String str;
        //初始化符号表
        MyConstants.initKeyMap(true);
        int i = 0;
        for (i = 0; i < data.length() && !error; ) {
            ch = data.charAt(i);
            nowState = MyConstants.typeOfChar(ch);
            switch (nowState) {
                case 'a': {
                    //可以构成关键字，标识符，常量字符串
                    while ((nowState == 'a' || nowState == '0' ||
                            nowState == '$' || nowState == '￥' ||
                            nowState == '_')
                            && i < data.length()) {
                        strToken.append(ch);
                        i++;
                        ch = data.charAt(i);
                        nowState = MyConstants.typeOfChar(ch);
                    }
                    str = strToken.toString();
                    Integer number = MyConstants.KEYMAP.get(str);
                    if (number != null) {
                        addToMap(keyMap, str);
                        strToken = new StringBuilder();
                    } else {
                        addToMap(markMap, str);
                        strToken = new StringBuilder();
                    }
                    lastState = 'a';
                    break;
                }
                case '_': {
                    //只能出现在标识符中或者常量字符串中
                    do {
                        strToken.append(ch);
                        i++;
                        ch = data.charAt(i);
                        nowState = MyConstants.typeOfChar(ch);
                    } while ((nowState == 'a' || nowState == '0' ||
                            nowState == '$' || nowState == '￥' || nowState == '_')
                            && i < data.length());
                    str = strToken.toString();
                    addToMap(markMap, str);
                    strToken = new StringBuilder();
                    lastState = '_';
                    break;
                }
                case '￥':
                    //只能出现在标识符，常量字符串中
                    do {
                        strToken.append(ch);
                        i++;
                        ch = data.charAt(i);
                        nowState = MyConstants.typeOfChar(ch);
                    } while ((nowState == 'a' || nowState == '0' ||
                            nowState == '$' || nowState == '￥' || nowState == '_')
                            && i < data.length());
                    str = strToken.toString();
                    addToMap(markMap, str);
                    strToken = new StringBuilder();
                    lastState = '￥';
                    break;
                case '$':
                    //只能出现在标识符，常量字符串中
                    do {
                        strToken.append(ch);
                        i++;
                        ch = data.charAt(i);
                        nowState = MyConstants.typeOfChar(ch);
                    } while ((nowState == 'a' || nowState == '0' ||
                            nowState == '$' || nowState == '￥' || nowState == '_')
                            && i < data.length());
                    str = strToken.toString();
                    addToMap(markMap, str);
                    strToken = new StringBuilder();
                    lastState = '$';
                    break;
                case '.':
                    //只能作为标识符之间的连接部分，或者常量中的一部分
                    if (lastState == 'a' || lastState == '$' ||
                            lastState == '￥' || lastState == '_') {
                        if (i == data.length() - 1) {
                            //.符号处于结尾
                            error = true;
                            break;
                        } else {
                            i++;
                            ch = data.charAt(i);
                            nowState = MyConstants.typeOfChar(ch);
                            if (nowState == 'a' || nowState == '$' ||
                                    nowState == '￥' || nowState == '_' || nowState == '*') {
                                addToMap(operationMap, ".");
                                lastState = '.';
                            } else {
                                error = true;
                                break;
                            }
                        }
                    } else {
                        error = true;
                    }
                    break;
                case '0':
                    //出现在数字中
                    do {
                        strToken.append(ch);
                        i++;
                        ch = data.charAt(i);
                        nowState = MyConstants.typeOfChar(ch);
                    } while (nowState == '0');
                    addToMap(finalMap, strToken.toString());
                    strToken = new StringBuilder();
                    break;
                case ' ':
                    i++;
                    break;
                case '\n':
                    i++;
                    break;
                case '-':
                    i = countOperation(i, '-', data, operationMap);
                    if (i < 0) {
                        error = true;
                        i = -i;
                    }
                    break;
                case '+':
                    i = countOperation(i, '+', data, operationMap);
                    if (i < 0) {
                        error = true;
                        i = -i;
                    }
                    break;
                case '*':
                    if (i < data.length() - 4) {
                        addToMap(operationMap, "*");
                        i++;
                    } else {
                        error = true;
                    }
                    break;
                case '/':
                    //只能出现在注释中或者乘除法，以及常量字符串中
                    if (i < data.length() - 1) {
                        char nextState = MyConstants.typeOfChar(data.charAt(i + 1));
                        if (nextState == '*') {
                            //多行注释开始
                            strToken.append('/');
                            i++;
                            do {
                                char c = data.charAt(i);
                                nextState = MyConstants.typeOfChar(c);
                                strToken.append(c);
                                i++;
                            }
                            while (!(nextState == '/' && MyConstants.typeOfChar(data.charAt(i - 2)) == '*')
                                    && i < data.length() - 1);
                            if (i == data.length() - 2 &&
                                    MyConstants.typeOfChar(data.charAt(data.length() - 1)) != '/') {
                                //直到最后也没有找到多行注释的结尾
                                error = true;
                                break;
                            } else if (strToken.length() > 0) {
                                content.append("多行注释:\n" + strToken.toString() + "\n");
                                strToken = new StringBuilder();
                            }
                        } else if (nextState == '/') {
                            //单行注释开始
                            do {
                                char c = data.charAt(i);
                                nextState = MyConstants.typeOfChar(c);
                                strToken.append(c);
                                i++;
                            } while (nextState != '\n');
                            if (strToken != null) {
                                content.append("单行注释:\n" + strToken.toString() + "\n");
                                strToken = new StringBuilder();
                            }
                        } else {
                            //分析是不是除号
                            if (i <= data.length() - 4) {
                                //...../3;}#合法
                                addToMap(operationMap, "/");
                                i++;
                            } else {
                                error = true;
                            }
                        }
                    } else {
                        error = true;
                        break;
                    }
                    break;
                case ';':
                    i++;
                    break;
                case '(':
                    i++;
                    break;
                case ')':
                    i++;
                    break;
                case '&':
                    i = countOperation(i, '&', data, operationMap);
                    if (i < 0) {
                        error = true;
                        i = -i;
                    }
                    break;
                case '|':
                    i = countOperation(i, '|', data, operationMap);
                    if (i < 0) {
                        error = true;
                        i = -i;
                    }
                    break;
                case '=':
                    i = countOperation(i, '=', data, operationMap);
                    if (i < 0) {
                        error = true;
                        i = -i;
                    }
                    break;
                case '"':
                    //这里判断常量字符串
                    do {
                        strToken.append(ch);
                        i++;
                        ch = data.charAt(i);
                        nowState = MyConstants.typeOfChar(ch);
                    } while ((nowState != '"') && i < data.length());
                    if (i == data.length()) {
                        error = true;
                    } else if (strToken.length() > 0) {
                        addToMap(finalMap, strToken.toString() + "\"");
                        i++;
                        strToken = new StringBuilder();
                    }
                    break;
                case '\'':
                    if (i <= data.length() - 5) {
                        //.....'j';}#
                        if (MyConstants.typeOfChar(data.charAt(i + 2)) == '\'') {
                            addToMap(finalMap, "'" + data.charAt(i + 1) + "'");
                            i += 3;
                        } else {
                            error = true;
                        }
                    } else {
                        error = true;
                    }
                    break;
                case '\\':
                    i++;
                    break;
                default:
                    i++;
                    break;
            }
        }
        if (error) {
            return false;
        }
        return true;
    }


    public static int countOperation(int i, char ch, String data, HashMap<String, Integer> operationMap) {
        if (i < data.length() - 1) {
            if (MyConstants.typeOfChar(data.charAt(i + 1)) == ch) {
                //==
                if (i <= data.length() - 5) {
                    //.....==3;}#合法
                    addToMap(operationMap, ch + "" + ch);
                    i += 2;
                } else {
                    i = -i;
                }
            } else {
                //=
                if (i <= data.length() - 4) {
                    //....=3;}#合法
                    addToMap(operationMap, ch + "");
                    i++;
                } else {
                    i = -i;
                }
            }
        }
        return i;
    }

    public String getMap(HashMap<String, Integer> map){
        if (map == null){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Set<Map.Entry<String, Integer>> entrySet = map.entrySet();
        Iterator it = entrySet.iterator();
        while (it.hasNext()) {
            Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) it.next();
            builder.append(entry.getKey() + ":------>" + entry.getValue() + "\n");
        }
        return builder.toString();
    }

    public String getKeys(){
        return getMap(keyMap);
    }

    public String getFinals(){
        return getMap(finalMap);
    }

    public String getMarks(){
        return getMap(markMap);
    }

    public String getOperations(){
        return getMap(operationMap);
    }

    public String getContent(){
        return content.toString();
    }

    /**
     * 向某一个Hash表中插入一个key，如果存在增加次数
     *
     * @param map
     * @param key
     */
    public static void addToMap(HashMap<String, Integer> map, String key) {
        Integer count = map.get(key);
        if (count != null) {
            count++;
        } else {
            count = 1;
        }
        map.put(key, count);
    }
}
