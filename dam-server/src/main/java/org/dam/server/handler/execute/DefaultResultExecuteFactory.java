package org.dam.server.handler.execute;

import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.server.handler.HandleWrapper;

import java.util.Map;

import static org.dam.constant.Constants.*;

public class DefaultResultExecuteFactory implements ResultExecuteFactory {

    private Map<String,Object> attributes;
    private String extend;
    private Response response;
    public DefaultResultExecuteFactory(Response response){
        this.response = response;
        this.attributes = this.response.getAttribute();
    }

    @Override
    public ResultExecutor createResultExecute(HandleWrapper.HandleResult handlerResult) {
        if(RESULT_TYPE_STATIC.equals(handlerResult.getResultType())){
            return new StaticResultExecutor(handlerResult);
        }else if(RESULT_TYPE_DYNAMIC.equals(handlerResult.getResultType())){
            return new DynamicResultExecutor(handlerResult,attributes);
        }else if(RESULT_TYPE_JSON.equals(handlerResult.getResultType())){
            return new JSONResultExecutor(handlerResult);
        }else if(RESULT_TYPE_REDIRECT.equals(handlerResult.getResultType())){
            return new RedirectResultExecutor(handlerResult);
        }else if(RESULT_TYPE_CLIENT_ERROR.equals(handlerResult.getResultType())){
            return new ClientErrorResultExecutor(handlerResult);
        }else if(RESULT_TYPE_SERVER_ERROR.equals(handlerResult.getResultType())){
            return new ServerErrorResultExecutor(handlerResult);
        }else if(RESULT_TYPE_FILE.equalsIgnoreCase(handlerResult.getResultType())){
            return new StaticResultExecutor(handlerResult);
        }
        handlerResult.setResultStatus(HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc());
        return new ResultExecutor() {
            @Override
            public byte[] execute() {
                return "result type not found".getBytes();
            }
        };
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public String getExtend() {
        return extend;
    }

    public void setExtend(String extend) {
        this.extend = extend;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
