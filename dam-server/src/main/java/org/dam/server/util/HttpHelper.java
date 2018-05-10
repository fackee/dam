package org.dam.server.util;

import com.sun.org.apache.regexp.internal.RE;
import org.dam.http.Response;
import org.dam.http.constant.HttpConstant;
import org.dam.http.statics.HttpMedia;
import sun.dc.pr.PRError;

public class HttpHelper {

    private static final String HTML_PREFIX= "<html><head></head><body>";
    private static final String HTML_SUFFIX = "</body></html>";

    public static HttpMedia.Accept sourceCase(String source){
        if(source.contains(".png") ||  source.contains(".jpeg")
                || source.contains(".jpg")){
            return HttpMedia.Accept.IMAGE_JPEG;
        }
        if(source.contains(".html") || source.contains(".htm")){
            return HttpMedia.Accept.TEXT_HTML;
        }
        if(source.contains(".js")){
            return HttpMedia.Accept.APPLICATION_X_JAVASCRIPT;
        }
        if(source.contains(".css")){
            return HttpMedia.Accept.TEXT_CSS;
        }
        if(source.contains(".gif")){
            return HttpMedia.Accept.IMAGE_GIF;
        }
        if(source.contains(".svg")){
            return HttpMedia.Accept.IMAGE_SVG_XML;
        }
        if(source.contains(".ico")){
            return HttpMedia.Accept.IMAGE_X_ICO;
        }
        if(source.contains(".mp3")){
            return HttpMedia.Accept.AUDIO_MPEG;
        }
        if(source.contains(".avi") || source.contains(".mp4")){
            return HttpMedia.Accept.VIDEO_X_MSVIDEO;
        }
        return null;
    }

    public static byte[] handleError(String errorMsg){
        String result = HTML_PREFIX + errorMsg + HTML_SUFFIX;
        return result.getBytes();
    }

    public static void serverError(Response response){
        response.setHttpHeader(HttpConstant.STATUS,HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc());
        response.setHttpHeader(HttpConstant.HttpEntity.Content_Type.toString(),HttpMedia.Accept.TEXT_HTML.getAccept());
    }
}