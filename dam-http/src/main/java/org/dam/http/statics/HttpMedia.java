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

    enum Mime{

        HTML(".html"),HTM(".htm"),
        JS(".js"),
        CSS(".css"),
        PNG(".PNG"),JPG(".jpg"),JPEG(".jpeg"), GIF(".gif"),ICO(".ico"),SVG(",svg"),
        MP3(".mp3"),
        AVI(".avi"),MP4(".mp4");

        private String mimeSuffix;

        Mime(String mimeSuffix){
            this.mimeSuffix = mimeSuffix;
        }

        public String getMimeSuffix() {
            return mimeSuffix;
        }
    }

    enum Accept{
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