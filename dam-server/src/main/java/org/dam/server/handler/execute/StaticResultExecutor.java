package org.dam.server.handler.execute;

import org.dam.exception.VariableIllegalException;
import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;
import org.dam.utils.util.stream.StaticStream;

import java.io.IOException;

import static org.dam.constant.Constants.RESULT_TYPE_FILE;

public class StaticResultExecutor implements ResultExecutor {

    private HandleWrapper.HandleResult handleResult;
    public StaticResultExecutor(HandleWrapper.HandleResult handleResult){
        this.handleResult = handleResult;
    }

    @Override
    public byte[] execute() throws VariableIllegalException, IOException {
        handleResult.setResultStatus(HttpConstant.HttpStatusCode.OK.getDesc());
        if(RESULT_TYPE_FILE.equalsIgnoreCase(handleResult.getResultType())){
            return StaticStream.getBytesByFilePath((String) handleResult.getExtend());
        }
        return gzipPack(StaticStream.getBytesByFilePath((String) handleResult.getExtend()));
    }
}
