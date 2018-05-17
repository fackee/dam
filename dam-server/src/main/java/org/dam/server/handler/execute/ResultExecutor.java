package org.dam.server.handler.execute;

import org.dam.exception.VariableIllegalException;
import org.dam.utils.util.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

public interface ResultExecutor {

    public byte[] execute() throws VariableIllegalException, IOException;

    public default byte[] gzipPack(byte[] exeRes){
        byte[] gzip = null;
        ByteArrayOutputStream outputStream = null;
        GZIPOutputStream gzipOutputStream = null;
        try {
            outputStream = new ByteArrayOutputStream();
            gzipOutputStream = new GZIPOutputStream(outputStream);
            gzipOutputStream.write(exeRes);
            gzipOutputStream.finish();
            gzip = outputStream.toByteArray();
        }catch (Exception e){
            gzip = new byte[0];
        }finally {
            try {
                outputStream.close();
                gzipOutputStream.close();
            } catch (IOException e) {
                gzip = new byte[0];
            }
        }
        return gzip;
    }
}
