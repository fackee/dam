package org.dam.server.handler.execute;

import org.dam.exception.VariableIllegalException;
import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;
import org.dam.utils.util.stream.StaticStream;

import java.io.IOException;

public class FileResultExecutor implements ResultExecutor {

    private HandleWrapper.HandleResult handleResult;

    public FileResultExecutor(HandleWrapper.HandleResult handleResult){
        this.handleResult = handleResult;
    }

    @Override
    public byte[] execute() throws VariableIllegalException, IOException {
        handleResult.setResultStatus(HttpConstant.HttpStatusCode.OK.getDesc());
        return gzipPack(StaticStream.getBytesByFilePath((String) handleResult.getExtend()));
    }
}
