package httpserver.http;

/**
 * Created by geeche on 2018/1/27.
 */
public class HttpField {

    private HttpHeader httpHeader;

    private HttpMethod httpMethod;

    private Cookie cookie;

    public HttpField(){
        this(null,null,null);
    }

    public HttpField(HttpHeader httpHeader){
        this(httpHeader,null,null);
    }

    public HttpField(HttpHeader httpHeader,HttpMethod httpMethod){
        this(httpHeader,httpMethod,null);
    }

    public HttpField(HttpHeader httpHeader,HttpMethod httpMethod,Cookie cookie){
        this.httpHeader = httpHeader;
        this.httpMethod = httpMethod;
        this.cookie = cookie;
    }

    public HttpHeader getHttpHeader() {
        return httpHeader;
    }

    public void setHttpHeader(HttpHeader httpHeader) {
        this.httpHeader = httpHeader;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public Cookie getCookie() {
        return cookie;
    }

    public void setCookie(Cookie cookie) {
        this.cookie = cookie;
    }
}
