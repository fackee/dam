package httpserver.http;

public interface Response {

    public void setContent(String content);
    public String getContent();
    public void setHeader(String key,String value);
    public void addAttribute(String key,String value);

}