package httpserver.http;

public class HttpRequest implements Request{

    private HttpHeader httpHeader;
    private HttpParameter httpParameter;
    private Cookie cookie;
    private Session session;

    public HttpRequest(HttpHeader httpHeader){
        this(httpHeader,new HttpParameter());
    }
    public HttpRequest(HttpHeader httpHeader,HttpParameter httpParameter){
        this(httpHeader,httpParameter,new Cookie());
    }
    public HttpRequest(HttpHeader httpHeader,HttpParameter httpParameter,Cookie cookie){
        this(httpHeader,httpParameter,cookie,new Session());
    }
    public HttpRequest(HttpHeader httpHeader,HttpParameter httpParameter,Cookie cookie,Session session){
        this.httpHeader = httpHeader;
        this.httpParameter = httpParameter;
        this.cookie = cookie;
        this.session = session;
    }

    @Override
    public HttpHeader getHeader() {
        return httpHeader;
    }

    @Override
    public Cookie getCookie() {
        return cookie;
    }

    @Override
    public Session getSession() {
        return session;
    }

    @Override
    public HttpParameter getParameter() {
        return httpParameter;
    }
}