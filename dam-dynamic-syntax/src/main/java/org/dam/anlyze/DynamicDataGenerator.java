package org.dam.anlyze;

import org.dam.exception.VariableIllegalException;

import java.util.Map;

public class DynamicDataGenerator {

    private String dynamicString;
    private Map<String,Object> attributes;
    private AnalyzeStrategy strategy;

    public DynamicDataGenerator(String dynamicString,Map<String,Object> attributes){
        this.dynamicString = dynamicString;
        this.attributes = attributes;
    }
    public DynamicDataGenerator(String dynamicString,Map<String,Object> attributes,AnalyzeStrategy strategy){
        this.dynamicString = dynamicString;
        this.attributes = attributes;
        this.strategy = strategy;
    }

    public String generate() throws VariableIllegalException {
        strategy.analyze();
        this.dynamicString =strategy.getDynamicVariable();
        return dynamicString;
    }


    public void setStrategy(AnalyzeStrategy strategy) {
        this.strategy = strategy;
        strategy.setDynamicVariable(dynamicString);
        strategy.setValue(attributes);
    }

    public void setDynamicString(String dynamicString) {
        this.dynamicString = dynamicString;
    }

    public String getDynamicString() {
        return dynamicString;
    }
}
