package org.dam.server.handler.execute;

import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;

public class ClientErrorResultExecutor implements ResultExecutor {

    private HandleWrapper.HandleResult handleResult;

    public ClientErrorResultExecutor(HandleWrapper.HandleResult handleResult){
        this.handleResult = handleResult;
    }

    @Override
    public byte[] execute(){
        handleResult.setResultStatus(HttpConstant.HttpStatusCode.Not_Found.getDesc());
        return gzipPack(((String)handleResult.getExtend()).getBytes());
    }
}
