package httpserver.http;

public class HttpHeader {

    private URL url;

    private HttpMethod httpMethod;

    private String httpProtocalVersion;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getHttpProtocalVersion() {
        return httpProtocalVersion;
    }

    public void setHttpProtocalVersion(String httpProtocalVersion) {
        this.httpProtocalVersion = httpProtocalVersion;
    }
}