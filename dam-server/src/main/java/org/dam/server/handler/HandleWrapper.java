package org.dam.server.handler;

import org.dam.exception.*;
import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.server.Server;
import org.dam.server.handler.execute.DefaultResultExecuteFactory;
import org.dam.server.handler.execute.ResultExecutor;
import org.dam.server.response.generator.ResponseGenerator;
import org.dam.utils.util.StringUtil;
import org.dam.utils.util.log.Logger;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static org.dam.constant.Constants.RESULT_TYPE_CLIENT_ERROR;
import static org.dam.constant.Constants.RESULT_TYPE_SERVER_ERROR;
import static org.dam.constant.Constants.STATUS;

/**
 * Created by geeche on 2018/1/27.
 */
public class HandleWrapper extends AbstractHandler {

    private final List<Handler> handlerChain;

    private final HandleResult handleResult = new HandleResult();

    private DefaultResultExecuteFactory resultExecuteFactory;

    private ResponseGenerator responseGenerator;

    private ResultExecutor resultExecutor;

    public HandleWrapper(LinkedList<Handler> handlers){
        handlerChain = Collections.synchronizedList(handlers);
    }

    public HandleWrapper addDefaultHandler(){
        HttpHandler httpHandler = new HttpHandler();
        httpHandler.setServer(getServer());
        httpHandler.setHandleResult(handleResult);
        handlerChain.add(httpHandler);
        SessionHandler sessionHandler = new SessionHandler();
        sessionHandler.setServer(getServer());
        sessionHandler.setHandleResult(handleResult);
        handlerChain.add(sessionHandler);
        AppLoadHandler appLoadHandler = new AppLoadHandler();
        appLoadHandler.setServer(getServer());
        appLoadHandler.setHandleResult(handleResult);
        handlerChain.add(appLoadHandler);
        return this;
    }

    @Override
    public boolean handle(Request request, Response response) {
        for(Handler handler : handlerChain){
            try {
                if(!handler.handle(request,response)){
                    Logger.INFO("execute-[{}] back:",handler.getClass().getName());
                    break;
                }
            }catch (ClientException e){
                handleResult.setResultType(RESULT_TYPE_CLIENT_ERROR);
                handleResult.setExtend(e.getMessage());
                break;
            }catch (ServerException e){
                handleResult.setResultType(RESULT_TYPE_SERVER_ERROR);
                handleResult.setExtend(e.getMessage());
                break;
            }
        }
        Logger.INFO("handle chain over resultHandle:{}",handleResult.toString());
        resultExecuteFactory = new DefaultResultExecuteFactory(response);
        resultExecutor = resultExecuteFactory.createResultExecute(handleResult);
        try {
            handleResult.setBody(resultExecutor.execute());
        } catch (VariableIllegalException e) {
            handleResult.setBody(StringUtil.format("handle dynamic page error:{}",
                    Logger.printStackTraceToString(e)).getBytes());
            response.setHttpHeader(STATUS,HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc());
            handleResult.setResultStatus(RESULT_TYPE_SERVER_ERROR);
        } catch (IOException e) {
            handleResult.setBody(StringUtil.format("handle static source error:{}",
                    Logger.printStackTraceToString(e)).getBytes());
            handleResult.setResultStatus(RESULT_TYPE_CLIENT_ERROR);
            response.setHttpHeader(STATUS,HttpConstant.HttpStatusCode.Not_Found.getDesc());
        }
        responseGenerator = new ResponseGenerator(handleResult,request,response);
        responseGenerator.generate();
        response.generateHeaderBytes();
        return true;
    }

    @Override
    public void setServer(Server server) {
        super.setServer(server);
    }

    public static class HandleResult{

        private String resultStatus;

        private String resultType;

        private Object extend;

        private byte body[];

        public String getResultType() {
            return resultType;
        }

        public void setResultType(String resultType) {
            this.resultType = resultType;
        }

        public byte[] getBody() {
            return body;
        }

        public void setBody(byte[] body) {
            this.body = body;
        }

        public String getResultStatus() {
            return resultStatus;
        }

        public void setResultStatus(String resultStatus) {
            this.resultStatus = resultStatus;
        }

        public void setExtend(Object extend) {
            this.extend = extend;
        }

        public Object getExtend() {
            return extend;
        }

        @Override
        public String toString() {
            return resultStatus + "\n" + resultType + "\n" + extend;
        }
    }
}
