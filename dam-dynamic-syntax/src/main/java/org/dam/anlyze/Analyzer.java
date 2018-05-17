package org.dam.anlyze;

import org.dam.dynamic.DynamicSyntax;
import org.dam.exception.VariableIllegalException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Analyzer {

    private String data;
    private Map<String,Object> attributes;
    private DynamicDataGenerator generator;
    public Analyzer(String data,Map<String,Object> attributes){
        this.data = data;
        this.attributes = attributes;
        generator = new DynamicDataGenerator(data,attributes);
    }

    public Analyzer analyze() throws VariableIllegalException {
        while (analyzeSingleSyntax()){}
        while (analyzeConditionSyntax()){}
        while (analyzeLooperSyntax()){}
        return this;
    }


    private boolean analyzeSingleSyntax() throws VariableIllegalException {
        char[] dataArr = data.toCharArray();
        int startIndex = data.indexOf(DynamicSyntax.SYNTAX_START.getKeyword());
        if(dataArr[startIndex+1] == DynamicSyntax.SINGLE_START.getKeyword().charAt(0)){
            int endIndex = startIndex + 1;
            while (endIndex++ < dataArr.length){
                if(dataArr[endIndex] == DynamicSyntax.SINGLE_END.getKeyword().charAt(0)){
                    break;
                }
            }
            String regexVar = data.substring(startIndex+2,endIndex);
            System.out.println(regexVar);
            generator.setDynamicString(regexVar);
            generator.setStrategy(new SingleStrategy());
            generator.generate();
            data = data.replace(data.substring(startIndex,endIndex+1),generator.getDynamicString());
            return true;
        }
        return false;
    }

    private boolean analyzeLooperSyntax() throws VariableIllegalException{
       return false;
    }
    private boolean analyzeConditionSyntax() throws VariableIllegalException{
        return false;
    }

    public String getData() {
        return data;
    }

    public static void main(String[] args) {
        Map<String,Object> attrs = new HashMap<>();
        List<String> list = new ArrayList<>();
        Obj obj = new Obj("zhuzhuzhu","男");
        list.add("https://www.baidu.com");
        attrs.put("var","admin");
        attrs.put("list",list);
        attrs.put("obj",obj);
        Analyzer analyzer = new Analyzer("<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>dynamic page</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <ul>\n" +
                "        <li>singleVar:{$var}</li>\n" +
                "        <li>listVar:{$list.get(0)}</li>\n" +
                "        <li>objVar:{$obj.getUserName()}</li>\n" +
                "        <li>objVar:{$obj.getGender()}</li>\n" +
                "    </ul>\n" +
                "</body>\n" +
                "</html>",attrs);
        try {
            analyzer.analyze();
        } catch (VariableIllegalException e) {
            System.out.println(e.toString());
        }
    }
    static class Obj{
        private String username;
        private String gender;
        Obj(String username,String gender){
            this.username = username;
            this.gender = gender;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}
