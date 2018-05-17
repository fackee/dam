package org.dam.dynamic;

public enum DynamicSyntax {
    SYNTAX_START("{"),
    SINGLE_START("$"),SINGLE_END("}"),
    CONDITION_IF("if("),CONDITION_ENDIF(")}"),
    CONDITION_ELSE_IF("else if("),CONDITION_ELSE("else}"),CONDITION_END("{endif}"),
    LOOP_START("loop("),LOOP_END("endloop");

    private String keyword;
    DynamicSyntax(String keyword){
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }
}
