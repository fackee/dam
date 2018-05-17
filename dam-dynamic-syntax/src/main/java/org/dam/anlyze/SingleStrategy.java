package org.dam.anlyze;

import org.dam.dynamic.VariableType;
import org.dam.exception.VariableIllegalException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class SingleStrategy extends AbstractStategy{

    public SingleStrategy(){
        super();
    }
    public SingleStrategy(String dynamicVariable,Map<String,Object> attributes){
        super(dynamicVariable,attributes);
    }
    @Override
    public void analyze() throws VariableIllegalException {
        VariableType variableType = variableType();
        if(variableType == VariableType.SINGLE){
            singleVar(attributes.get(dynamicVariable));
        }else if(variableType == VariableType.OBJECT){
            String methodName = dynamicVariable.substring(dynamicVariable.indexOf('.')+1
                    ,dynamicVariable.indexOf('('));
            try {
                String key = dynamicVariable.substring(0,dynamicVariable.indexOf('.'));
                Object value = attributes.get(key);
                Method method = value.getClass().getDeclaredMethod(methodName);
                try {
                    Object result = method.invoke(value);
                    singleVar(result);
                } catch (IllegalAccessException e) {
                    dynamicVariable = "undefine";
                } catch (InvocationTargetException e) {
                    dynamicVariable = "undefine";
                }
            } catch (NoSuchMethodException e) {
                dynamicVariable = "undefine";
            }
        }else if(variableType == VariableType.LIST){
            String key = dynamicVariable.substring(0,dynamicVariable.indexOf('.'));
            int listIndex = Integer.parseInt(dynamicVariable.substring(dynamicVariable.indexOf('(')+1,dynamicVariable.indexOf(')')));
            Object value = attributes.get(key);
            if(value instanceof List){
                List list = (List)value;
                value = list.get(listIndex);
                dynamicVariable = (String) value;
            }else{
                dynamicVariable = "undefine";
            }
        }else{
            throw new VariableIllegalException();
        }
    }

    private void singleVar(Object value){
        if(value == null){
            dynamicVariable =  "undefine";
        }
        if(value instanceof String){
            dynamicVariable = (String)value;
        }else if(value instanceof Integer){
            dynamicVariable = String.valueOf(value);
        }else if(value instanceof Boolean){
            dynamicVariable = String.valueOf(value);
        }else if(value instanceof Float){
            dynamicVariable = String.valueOf(value);
        }else if(value instanceof Double){
            dynamicVariable = String.valueOf(value);
        }else{
            dynamicVariable = "undefine";
        }
    }

    private VariableType variableType() throws VariableIllegalException {

        int pointIndex = dynamicVariable.indexOf(".");
        int endIndex =dynamicVariable.lastIndexOf(".");
        int leftBrackets = dynamicVariable.indexOf("(");
        int rightBrackets = dynamicVariable.indexOf(")");
        int get = dynamicVariable.indexOf(".get(");
        if(pointIndex == -1){
            return VariableType.SINGLE;
        }else if(pointIndex != endIndex){
            throw new VariableIllegalException();
        }else if(get == -1 && rightBrackets - leftBrackets == 1){
            return VariableType.OBJECT;
        }else if(rightBrackets - get > 1){
            return VariableType.LIST;
        }else{
            throw new VariableIllegalException();
        }
    }
}
