package org.dam.server.handler.execute;

import org.dam.exception.VariableIllegalException;
import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;

import java.io.IOException;

/**
 * Created by geeche on 2018/5/7.
 */
public class RedirectResultExecutor implements ResultExecutor {

    private HandleWrapper.HandleResult handleResult;

    public RedirectResultExecutor(HandleWrapper.HandleResult handleResult){
        this.handleResult = handleResult;
    }

    @Override
    public byte[] execute() throws VariableIllegalException, IOException {
        handleResult.setResultStatus(HttpConstant.HttpStatusCode.FOUND.getDesc());
        return new byte[0];
    }
}
