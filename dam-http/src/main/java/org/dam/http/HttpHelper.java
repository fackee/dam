package org.dam.http;

import org.dam.constant.Constants;
import org.dam.http.constant.HttpConstant;
import org.dam.http.constant.HttpMedia;

public class HttpHelper {

    private static final String HTML_PREFIX= "<html><head></head><body>";
    private static final String HTML_SUFFIX = "</body></html>";

    public static HttpMedia.Accept sourceCase(String source){
        if(source.contains(".png") ||  source.contains(".jpeg")
                || source.contains(".jpg")){
            return HttpMedia.Accept.IMAGE_JPG;
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

    public static boolean isStaticResponse(String resultType){
        return Constants.RESULT_TYPE_PAGE.equals(resultType) ||
                Constants.RESULT_TYPE_STATIC.equals(resultType);
    }

    public static boolean is20X(String status){
        return status.equals(HttpConstant.HttpStatusCode.OK.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Created.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Accepted.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.NO_CONTENT.getDesc());
    }
    public static boolean is30X(String status){
        return status.equals(HttpConstant.HttpStatusCode.Moved_Permanently.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.FOUND.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Multiple_Choices.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Not_Modified.getDesc())||
                status.equals(HttpConstant.HttpStatusCode.SEE_OTHER);
    }
    public static boolean is40X(String status){
        return status.equals(HttpConstant.HttpStatusCode.Bad_Request.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Forbidden.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Not_Found.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Unauthorized.getDesc());
    }

    public static boolean is50X(String status){
        return status.equals(HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Bad_Gateway.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Service_Unavailable.getDesc()) ||
                status.equals(HttpConstant.HttpStatusCode.Not_Implemented.getDesc());
    }
}