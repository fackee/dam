package org.dam.server.handler.sourcehandle;

import org.dam.server.config.Configuration;
import org.dam.utils.util.stream.StaticStream;

import java.io.IOException;

public class StaticHandle {

    private String target;
    private String sourceDirectory;

    public StaticHandle(String target){
        this.target = target;
        sourceDirectory = Configuration.DefaultConfig.getInstance().getAppsDirectory()+target;
    }

    public byte[] getSource() throws IOException {
        return StaticStream.getBytesByFilePath(sourceDirectory);
    }
}
