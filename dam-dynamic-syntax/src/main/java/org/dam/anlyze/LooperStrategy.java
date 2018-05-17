package org.dam.anlyze;

import org.dam.exception.VariableIllegalException;

import java.util.Map;

public class LooperStrategy extends AbstractStategy{

    public LooperStrategy(){
        super();
    }
    public LooperStrategy(String dynamicVariable,Map<String,Object> attributes){
        super(dynamicVariable,attributes);
    }

    @Override
    public void analyze() throws VariableIllegalException {

    }
}
