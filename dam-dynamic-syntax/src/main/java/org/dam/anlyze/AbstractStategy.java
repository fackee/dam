package org.dam.anlyze;

import org.dam.exception.VariableIllegalException;

import java.util.Map;

public abstract class AbstractStategy implements AnalyzeStrategy{

    protected String dynamicVariable;
    protected Map<String,Object> attributes;
    public AbstractStategy(){}
    public AbstractStategy(String dynamicVariable,Map<String,Object> attributes){
        this.dynamicVariable = dynamicVariable;
        this.attributes = attributes;
    }

    @Override
    public abstract void analyze() throws VariableIllegalException ;

    @Override
    public void setDynamicVariable(String dynamicVariable) {
        this.dynamicVariable = dynamicVariable;
    }

    @Override
    public String getDynamicVariable() {
        return dynamicVariable;
    }

    @Override
    public void setValue(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public Map<String, Object> getValue() {
        return attributes;
    }
}
