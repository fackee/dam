package org.dam.server.handler.execute;

import com.alibaba.fastjson.JSON;
import org.dam.exception.VariableIllegalException;
import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;

import java.io.IOException;

/**
 * Created by geeche on 2018/5/7.
 */
public class JSONResultExecutor implements ResultExecutor {

    private HandleWrapper.HandleResult handleResult;
    public JSONResultExecutor(HandleWrapper.HandleResult handleResult){
        this.handleResult = handleResult;
    }
    @Override
    public byte[] execute() throws VariableIllegalException, IOException {
        Object object = handleResult.getExtend();
        String result = JSON.toJSONString(object);
        handleResult.setResultStatus(HttpConstant.HttpStatusCode.OK.getDesc());
        return gzipPack(result.getBytes());
    }
}
