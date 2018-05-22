package org.dam.server.handler.execute;

import org.dam.anlyze.Analyzer;
import org.dam.exception.VariableIllegalException;
import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;
import org.dam.utils.util.StringUtil;
import org.dam.utils.util.log.Logger;
import org.dam.utils.util.stream.StaticStream;
import java.io.IOException;
import java.util.Map;

/**
 * Created by geeche on 2018/5/7.
 */
public class DynamicResultExecutor implements ResultExecutor {

    private Map<String,Object> attributes;
    private byte[] data;
    private Analyzer analyzer;
    private HandleWrapper.HandleResult handlerResult;
    public DynamicResultExecutor(HandleWrapper.HandleResult handlerResult, Map<String,Object> attributes){
        this.handlerResult = handlerResult;
        this.attributes = attributes;
    }

    @Override
    public byte[] execute() throws VariableIllegalException {
        try {
            data = StaticStream.getBytesByFilePath((String) handlerResult.getExtend());
        } catch (IOException e) {
            handlerResult.setResultStatus(HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc());
            return StringUtil.format("analyze dynamic page error:{}",
                    Logger.printStackTraceToString(e)).getBytes();
        }
        analyzer = new Analyzer(new String(data),attributes);
        analyzer.analyze();
        handlerResult.setResultStatus(HttpConstant.HttpStatusCode.OK.getDesc());
        return gzipPack(analyzer.getData().getBytes());
    }
}
