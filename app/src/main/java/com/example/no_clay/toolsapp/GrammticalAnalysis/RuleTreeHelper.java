package com.example.no_clay.toolsapp.GrammticalAnalysis;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by noclay on 2017/5/6.
 */

public class RuleTreeHelper {
    private List<Rule> steps;
    private LL1Resolver mResolver;

    public RuleTreeHelper(List<Rule> stepList, LL1Resolver mResolver) {
        this.steps = stepList;
        this.mResolver = mResolver;
    }

    public RuleTreeNode constructRuleTree(){
        RuleTreeNode root = null;
        if (steps.size() > 0){
            root = new RuleTreeNode();
            root.setValue(steps.get(0).getLeft());
            List<String> rights = steps.get(0).getRights();
            List<RuleTreeNode> sons = new ArrayList<>();
            steps.remove(0);
            for (int i = 0; i < rights.size(); i++) {
                String temp = rights.get(i);
                RuleTreeNode son;
                if (mResolver.getRulesHelper().getvT().contains(temp)){
                    //终结符
                    son = new RuleTreeNode();
                    son.setValue(temp);
                    sons.add(son);
                }else{
                    son = constructRuleTree();
                    if (son != null){
                        sons.add(son);
                    }
                }
            }
            root.setSon(sons);
        }
        return root;
    }


    public String getSon(RuleTreeNode root){
        StringBuilder string = new StringBuilder();
        List<RuleTreeNode> sons = root.getSon();
        if (sons != null){
            string.append("<ul class=\"tree\">");
            string.append("<li>" + root.getValue() + "</li>");
            for (int i = 0; i < sons.size(); i++) {
                string.append(getSon(sons.get(i)));
            }
            string.append("</ul>");
        }else{
            string.append("<ul class=\"tree\"><li>" + root.getValue() + "</li></ul>");
        }
        return string.toString();
    }

    public String getSonToHtml(String sonString){
        StringBuilder builder = new StringBuilder();
        builder.append("<!doctype html>\n" +
                "<html>\n" +
                "<head>\n" +
                "<meta charset='UTF-8'><meta name='viewport' content='width=device-width initial-scale=1'>\n" +
                "<title></title><link href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,700italic,700,400&subset=latin,latin-ext' rel='stylesheet' type='text/css' /><style type='text/css'>html {overflow-x: initial !important;}.mermaid .label { color: rgb(51, 51, 51); }\n" +
                "tree {\n" +
                " list-style-type: none;\n" +
                " background: url(images/vline.png) repeat-y;\n" +
                " margin-left:20px\n" +
                " padding: 0;\n" +
                "}\n" +
                "\n" +
                "li {\n" +
                " list-style-type:none;\n" +
                " margin: 0;\n" +
                " padding: 0 12px;\n" +
                " line-height: 20px;\n" +
                " background: url(images/node.png) no-repeat;\n" +
                " color: #369;\n" +
                " font-weight: bold;\n" +
                "}\n" +
                "\n" +
                "\n" +
                "\n" +
                "</style>\n" +
                "</head>\n" +
                "<body>\n" +
                "\t<div>");
        builder.append(sonString);
        builder.append("\t</div>\n" +
                "</body>\n" +
                "</html>");
        return builder.toString();
    }
}
