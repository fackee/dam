package httpserver.http;

public class HttpMethod {

    public enum Method{
        GET,POST,HEAD,PUT,DELETE,OPTIONS
    }

    private Method method;

    public HttpMethod(Method method){
        this.method = method;
    }

    public Method getMethod(){
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }
}