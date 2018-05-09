package org.dam.utils.util.stream;

import org.dam.utils.util.cache.StaticSourceMap;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class StaticStream {

    private static final StaticSourceMap SOURCE_MAP = new StaticSourceMap();

    public static byte[] getBytesByFilePath(String filePath) throws IOException {
        if (SOURCE_MAP.get(filePath) != null) {
            return SOURCE_MAP.get(filePath);
        }
        File file = new File(filePath);
        InputStream inputStream = null;
        //GZIPOutputStream gzip = null;
        //ByteArrayOutputStream outputStream = null;
        try {
            //outputStream = new ByteArrayOutputStream();
            //gzip = new GZIPOutputStream(outputStream);
            inputStream = new FileInputStream(file);
            byte[] bytes = new byte[inputStream.available()];
            while (inputStream.available() > 0) {
                inputStream.read(bytes);
            }
//            if(bytes[0] == 0xef && bytes[1] == 0xbb && bytes[2] == 0xbf){
//                System.out.println("==================POM======================");
//            }
            System.out.println(bytes.length);
            //gzip.write(bytes,0,bytes.length);
            //gzip.finish();
            //System.out.println(outputStream.toByteArray().length);
            SOURCE_MAP.put(filePath, bytes);
            return bytes;
        } finally {
            inputStream.close();
//                outputStream.flush();
//                gzip.flush();
//                outputStream.close();
//                gzip.close();
        }
    }


}