package org.dam.utils.util.stream;

import org.dam.utils.util.cache.StaticSourceMap;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StaticStream {

    private static final StaticSourceMap SOURCE_MAP = new StaticSourceMap();

    public static byte[] getBytesByFilePath(String filePath){
        if(SOURCE_MAP.get(filePath) != null){
            return SOURCE_MAP.get(filePath);
        }
        File file = new File(filePath);
        InputStream inputStream = null;
        GZIPOutputStream gzip = null;
        ByteArrayOutputStream outputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            gzip = new GZIPOutputStream(outputStream);
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            while (inputStream.available() >0){
                inputStream.read(bytes);
            }
            System.out.println(bytes.length);
            gzip.write(bytes,0,bytes.length);
            gzip.finish();
            System.out.println(outputStream.toByteArray().length);
            SOURCE_MAP.put(filePath,outputStream.toByteArray());
            return outputStream.toByteArray();
        } catch (FileNotFoundException e) {

        }catch (IOException e){
            System.out.println(e);
        }finally {
            try {
                inputStream.close();
                outputStream.flush();
                gzip.flush();
                outputStream.close();
                gzip.close();
            } catch (IOException e) {

            }
        }
        return new byte[0];
    }


}