package httpserver.http;

public class URL {

    private String url = null;

    public URL(final String url){
        this.url = url;
    }

    public String getAbsoluteURL(){
        return url;
    }

    public String getRelativeURL(){
        int startIndex = url.indexOf('/');
        if(startIndex != -1){
            return url.substring(startIndex,url.length());
        }
        return getAbsoluteURL();
    }


}