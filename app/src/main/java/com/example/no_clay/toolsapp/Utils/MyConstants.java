package com.example.no_clay.toolsapp.Utils;

import com.example.no_clay.toolsapp.R;

import java.util.HashMap;

/**
 * Created by no_clay on 2017/3/2.
 */

public class MyConstants {
    public static final String [] MENU_TITLE = new String[]{
            "词法分析器",
            "生成First和Follow集",
            "语法分析器",
            "未开发",
            "未开发",
            "未开发",
            "未开发",
            "未开发",
            "未开发",
    };
    public static final int []MENU_ICON = new int[]{
            R.drawable.analysis_white_128,
            R.drawable.create,
            R.drawable.yufa,
            R.drawable.kaifa_white_128,
            R.drawable.kaifa_white_128,
            R.drawable.kaifa_white_128,
            R.drawable.kaifa_white_128,
            R.drawable.kaifa_white_128,
            R.drawable.kaifa_white_128,
    };


    public static char typeOfChar(char ch) {
        if ((ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z')) {
            return 'a';
        } else if (ch >= '0' && ch <= '9') {
            return '0';
        } else if (ch == ' ' || ch == '\t') {
            return ' ';
        } else if (ch == '\n') {
            return '\n';
        } else {
            return ch;
        }
    }

    public static final HashMap<String, Integer> KEYMAP = new HashMap<>();

    private static void initKeyMap() {
        for (int i = 0; i < KEYS.length; i++) {
            KEYMAP.put(KEYS[i], 0);
        }
    }
    public static void initKeyMap(boolean isC){
        initKeyMap();
        if (isC){
            for (int i = 0; i < KEYS_C.length; i++) {
                KEYMAP.put(KEYS_C[i], 0);
            }
        }
    }

    public static final String[] KEYS = {
            "abstract", "interface", "boolean", "long", "break", "native",
            "byte", "new", "case", "null", "catch", "package", "char", "private",
            "class", "protected", "continue", "public", "default", "return", "do",
            "short", "double", "static", "else", "super", "extends", "switch", "false",
            "synchronized", "final", "this", "finally", "throw", "throws", "for", "float",
            "transient", "if", "try", "implements", "true", "import", "void", "instanceof",
            "volatile", "int", "while"
    };

    public static final String[] KEYS_C = {
            "NULL", "include", "struct", "sizeof"
    };


}
