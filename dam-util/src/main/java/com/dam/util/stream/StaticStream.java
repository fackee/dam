package com.dam.util.stream;

import com.dam.util.cache.StaticSourceMap;

import java.io.*;

public class StaticStream {

    private static final StaticSourceMap sourceMap = new StaticSourceMap();

    public static byte[] getBytesByFilePath(String filePath){
        if(sourceMap.get(filePath) != null){
            return sourceMap.get(filePath);
        }
        File file = new File(filePath);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            while (inputStream.available() >0){
                inputStream.read(bytes);
            }
            sourceMap.put(filePath,bytes);
            return bytes;
        } catch (FileNotFoundException e) {

        }catch (IOException e){

        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {

            }
        }
        return new byte[0];
    }


}