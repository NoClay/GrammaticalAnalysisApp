package com.example.no_clay.toolsapp.GrammticalAnalysis;

import java.util.*;

/**
 * Created by nocla on 2017/5/2.
 */
public class Rule {
    String value;
    String left;
    String right;
    int pos;
    List<String> rights;
    Set<String> vT;
    Set<String> vN;

    public String getRight() {
        return right;
    }

    public List<String> getRights() {
        return rights;
    }

    //产生式开头是终结符号还是非终结符
    public String getFirstVT() {
        char temp = right.charAt(0);
        if (temp < 'A' || temp > 'Z') {
            return temp + "";
        }
        return null;
    }

    public String getRightExceptFirstVN(){
        String firstVn = getFirstVN();
        if (firstVn == null){
            return right;
        }else{
            return right.substring(firstVn.length());
        }
    }

    public String getFirstVN() {
        char temp = right.charAt(0);
        if (temp >= 'A' && temp <= 'Z') {
            if (right.length() > 1 && right.charAt(1) == '\'') {
                return temp + "'";
            }
            return temp + "";
        }
        return null;
    }

    //产生式是否全部是非终结符
    public boolean isAllVN() {
        if (vT == null || vT.size() == 0) {
            return true;
        }
        return false;
    }

    public Set<String> getVT() {
        return vT;
    }

    public Set<String> getVN() {
        return vN;
    }

    public String getValue() {
        return value;
    }

    public String getLeft() {
        return left;
    }

    public int getPos() {
        return pos;
    }


    public Rule(String value) {
        this.value = value;
        this.vT = new HashSet<>();
        this.vN = new HashSet<>();
        this.rights = new ArrayList<>();
        initPos();
        initLeft();
        initRights();
    }

    private void initPos() {
        pos = value.indexOf("->");
    }

    private void initLeft() {
        left = value.substring(0, pos);
    }

    private void initRights() {
        this.right = value.substring(pos + 2);
        int length = right.length();
        for (int j = 0; j < length; ) {
            char tempChar = right.charAt(j);
            if (tempChar < 'A' || tempChar > 'Z') {
                vT.add(tempChar + "");
                rights.add(tempChar + "");
                j++;
            } else if (j < length - 1 && right.charAt(j + 1) == '\'') {
                vN.add(tempChar + "\'");
                rights.add(tempChar + "\'");
                j += 2;
            } else {
                vN.add(tempChar + "");
                rights.add(tempChar + "");
                j++;
            }
        }
    }

    /**
     * 判断产生式是否形如A->aBc
     * @param vt
     * @param vn
     * @return
     */
    public boolean isVT_VN_VTType(Set<String> vt, Set<String> vn) {
        if (isVT_VNType(vt, vn) && rights.size() >= 3 && vt.contains(rights.get(2))){
            return true;
        }
        return false;
    }

    /**
     * 判断产生式是否形如A->aB
     * @param vt
     * @param vn
     * @return
     */
    public boolean isVT_VNType(Set<String> vt, Set<String> vn) {
        if (rights.size() >= 2 && vt.contains(rights.get(0)) && vn.contains(rights.get(1))){
            return true;
        }
        return false;
    }
}
