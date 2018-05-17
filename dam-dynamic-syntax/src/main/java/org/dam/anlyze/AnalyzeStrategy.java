package org.dam.anlyze;

import org.dam.exception.VariableIllegalException;

import java.util.Map;

public interface AnalyzeStrategy {

    public void analyze() throws VariableIllegalException;

    public void setDynamicVariable(String dynamicVariable);

    public String getDynamicVariable();

    public void setValue(Map<String,Object> attributes) ;

    public Map<String,Object> getValue();

}
