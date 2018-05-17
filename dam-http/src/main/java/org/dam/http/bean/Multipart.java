package org.dam.http.bean;

import org.dam.annotation.File;

import java.util.HashMap;
import java.util.Map;

public class Multipart {

    private Map<String,String> contentDisposition = new HashMap<>(16);

    private String name;

    private String fileName;

    private byte[] bytes;

    public String getContentDisposition(String key) {
        return contentDisposition.get(key);
    }

    public void addContentDisposition(String key, String value) {
        contentDisposition.putIfAbsent(key ,value);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
