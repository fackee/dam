package org.dam.dynamic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum  VariableType {

    SINGLE("single"),
    OBJECT("object"),
    LIST("list");

    private String regex;

    VariableType(String regex){
        this.regex = regex;
    }

    public String getRegex() {
        return regex;
    }
}
