package org.dam.http.statics;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class HttpMedia {

    private static final List<String> mimeList = new CopyOnWriteArrayList<>();
    private static final List<String> acceptMime = new CopyOnWriteArrayList<>();
    static {
        acceptMime.add(Accept.TEXT_HTML.getAccept());
        acceptMime.add(Accept.TEXT_HTM.getAccept());
        acceptMime.add(Accept.APPLICATION_X_JAVASCRIPT.getAccept());
        acceptMime.add(Accept.AUDIO_MPEG.getAccept());
        acceptMime.add(Accept.IMAGE_GIF.getAccept());
        acceptMime.add(Accept.IMAGE_JPEG.getAccept());
        acceptMime.add(Accept.IMAGE_SVG_XML.getAccept());
        acceptMime.add(Accept.IMAGE_X_ICO.getAccept());
        acceptMime.add(Accept.TEXT_CSS.getAccept());
        mimeList.add(Mime.GIF.getMimeSuffix());
        mimeList.add(Mime.JPEG.getMimeSuffix());
        mimeList.add(Mime.JPG.getMimeSuffix());
        mimeList.add(Mime.PNG.getMimeSuffix());
        mimeList.add(Mime.CSS.getMimeSuffix());
        mimeList.add(Mime.JS.getMimeSuffix());
        mimeList.add(Mime.HTML.getMimeSuffix());
        mimeList.add(Mime.HTM.getMimeSuffix());
        mimeList.add(Mime.AVI.getMimeSuffix());
        mimeList.add(Mime.ICO.getMimeSuffix());
        mimeList.add(Mime.SVG.getMimeSuffix());
        mimeList.add(Mime.MP3.getMimeSuffix());
        mimeList.add(Mime.MP4.getMimeSuffix());
    }

    public static List<String> getMimeList() {
        return mimeList;
    }

    public static List<String> getAcceptMime() {
        return acceptMime;
    }

    public enum Mime{

        HTML(".html","text/html"),HTM(".htm","text/html"),
        JS(".js","application/x-javascript"),
        CSS(".css","text/css"),
        PNG(".png","image/png"),JPG(".jpg","image/jpeg"),JPEG(".jpeg","image/jpeg"),
        GIF(".gif","image/gif"),ICO(".ico","image/x-icon"),SVG(",svg","image/svg+xml"),
        MP3(".mp3","audio/mpeg"),
        AVI(".avi","video/x-msvideo"),MP4(".mp4","video/x-msvideo");

        private String mimeSuffix;
        private String acceptMime;
        Mime(String mimeSuffix,String acceptMime){
            this.mimeSuffix = mimeSuffix;
            this.acceptMime = acceptMime;
        }

        public String getMimeSuffix() {
            return mimeSuffix;
        }

        public String getAcceptMime() {
            return acceptMime;
        }
    }

    public enum Accept{
        TEXT_HTM("text/htm"),TEXT_HTML("text/html"),
        APPLICATION_X_JAVASCRIPT("application/x-javascript"),
        TEXT_CSS("text/css"),
        IMAGE_JPEG("image/jpeg"),IMAGE_GIF("image/gif"),IMAGE_SVG_XML("image/svg+xml"),IMAGE_X_ICO("image/x-ico"),
        AUDIO_MPEG("audio/mpeg"),
        VIDEO_X_MSVIDEO("vidoe/x-msvidoe");

        private String accept;

        Accept(String accept){
            this.accept = accept;
        }

        public String getAccept() {
            return accept;
        }
    }

    public static void main(String[] args) {
        System.out.println(Mime.HTML.getMimeSuffix());
    }
}