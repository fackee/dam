package org.dam.server.handler.execute;

import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;

public class ServerErrorResultExecutor implements ResultExecutor {
    private HandleWrapper.HandleResult handleResult;

    public ServerErrorResultExecutor(HandleWrapper.HandleResult handleResult){
        this.handleResult = handleResult;
    }

    @Override
    public byte[] execute() {
        handleResult.setResultStatus(HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc());
        return gzipPack(((String)handleResult.getExtend()).getBytes());
    }
}
