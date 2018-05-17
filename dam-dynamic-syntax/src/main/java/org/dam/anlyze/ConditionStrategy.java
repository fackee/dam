package org.dam.anlyze;

import org.dam.exception.VariableIllegalException;

import java.util.Map;

public class ConditionStrategy extends AbstractStategy{

    public ConditionStrategy(){
        super();
    }
    public ConditionStrategy(String dynamicVariable,Map<String,Object> attributes){
        super(dynamicVariable,attributes);
    }

    @Override
    public void analyze() {

    }
}
