package org.dam.server.response;

import org.dam.http.Request;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.http.constant.HttpMedia;
import org.dam.http.HttpHelper;
import org.dam.server.handler.HandleWrapper;

import static org.dam.constant.Constants.*;

public class OKResponseStrategy extends AbstractReponseStrategy{

    private boolean isStaticSource;
    private Object extend;
    private HandleWrapper.HandleResult handleResult;
    public OKResponseStrategy(){}

    public OKResponseStrategy(HandleWrapper.HandleResult handleResult, Request request,
                              Response response){
        super(request,response,handleResult.getBody());
        this.handleResult = handleResult;
        this.isStaticSource = HttpHelper.isStaticResponse(this.handleResult.getResultType());
        this.extend = this.handleResult.getExtend();
    }

    @Override
    public void doGenerate() {
        super.doGenerate();
        response.setHttpHeader(STATUS,HttpConstant.HttpStatusCode.OK.getDesc());
        if(isStaticSource){
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),
                    HttpHelper.sourceCase((String) extend)
                            .getAccept());
        }
        if(RESULT_TYPE_JSON.equalsIgnoreCase(handleResult.getResultType())){
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpMedia.Accept.APPLICATION_JSON.getAccept());
        }if(RESULT_TYPE_DYNAMIC.equalsIgnoreCase(handleResult.getResultType())){
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpMedia.Accept.TEXT_HTML.getAccept());
        }
        response.setHttpHeader(HttpConstant.HttpEntity.Accept_Ranges.toString(),RANGE);
        if(hasBody()){
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Encoding.toString(),
                    HttpConstant.HTTP_ENCODING_GZIP);
        }
        final String ifNoneMatch = request.getHeader().getRequestHeader().getIf_None_Match();
        String eTag = "iss-" + new String(body).hashCode();
        if(ifNoneMatch != null && ifNoneMatch.length() > 0){
            if(hasBody() && isStaticSource){
                if(eTag.equalsIgnoreCase(ifNoneMatch)){
                    response.setBodyBytes(null);
                }
            }
        }
        response.setHttpHeader(HttpConstant.HttpEntity.ETag.toString(),eTag);
        if(RESULT_TYPE_FILE.equalsIgnoreCase(handleResult.getResultType())){
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpMedia.Accept.APPLICATION_0CTET_STREAM.getAccept());
            response.setHttpHeader(HttpConstant.HttpGeneralHeader.Connection.toString(),"keep-alive");
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Encoding.toString(),null);
            response.setHttpHeader(HttpConstant.HttpEntity.ETag.toString(),null);
            String fileName = (String) handleResult.getExtend();
            response.setHttpHeader(HttpConstant.HttpEntity.Content_Disposition.toString(),"attachment; filename="
                +fileName.substring(fileName.lastIndexOf("/")+1,fileName.length()));
        }
    }

}
