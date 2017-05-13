package com.example.no_clay.toolsapp.GrammticalAnalysis;

import java.util.List;

/**
 * Created by noclay on 2017/5/6.
 */

public class RuleTreeNode {
    private String value;
    private List<RuleTreeNode> son;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<RuleTreeNode> getSon() {
        return son;
    }

    public void setSon(List<RuleTreeNode> son) {
        this.son = son;
    }
}
