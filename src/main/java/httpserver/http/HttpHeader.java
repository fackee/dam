package httpserver.http;

public class HttpHeader {

    private URL url;
    private HttpMethod httpMethod;
    private String httpProtocalVersion;
    private String accept;
    private String acceptEncoding;
    private String acceptLanguage;
    private String cacheControl;
    private String connection;
    private String host;
    private String userAgent;

    public HttpHeader(HttpHeaderBuilder builder){
        url = builder.url;
        httpMethod = builder.httpMethod;
        httpProtocalVersion = builder.httpProtocalVersion;
        accept = builder.accept;
        acceptEncoding = builder.acceptEncoding;
        acceptLanguage = builder.acceptLanguage;
        cacheControl = builder.cacheControl;
        connection = builder.connection;
        host = builder.host;
        userAgent = builder.userAgent;
    }

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

    public String getAccept() {
        return accept;
    }

    public void setAccept(String accept) {
        this.accept = accept;
    }

    public String getAcceptEncoding() {
        return acceptEncoding;
    }

    public void setAcceptEncoding(String acceptEncoding) {
        this.acceptEncoding = acceptEncoding;
    }

    public String getAcceptLanguage() {
        return acceptLanguage;
    }

    public void setAcceptLanguage(String acceptLanguage) {
        this.acceptLanguage = acceptLanguage;
    }

    public String getCacheControl() {
        return cacheControl;
    }

    public void setCacheControl(String cacheControl) {
        this.cacheControl = cacheControl;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public static class URL{
        private String url;

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

    public static class HttpMethod{

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

    public enum Method{
        GET,POST,HEAD,PUT,DELETE,OPTIONS
    }


    public static class HttpHeaderBuilder{
        private URL url;
        private HttpMethod httpMethod;
        private String httpProtocalVersion;
        private String accept;
        private String acceptEncoding;
        private String acceptLanguage;
        private String cacheControl;
        private String connection;
        private String host;
        private String userAgent;

        public HttpHeaderBuilder url(URL url) {
            this.url = url;
            return this;
        }

        public HttpHeaderBuilder httpMethod(HttpMethod httpMethod) {
            this.httpMethod = httpMethod;
            return this;
        }

        public HttpHeaderBuilder httpProtocalVersion(String httpProtocalVersion) {
            this.httpProtocalVersion = httpProtocalVersion;
            return this;
        }

        public HttpHeaderBuilder accept(String accept) {
            this.accept = accept;
            return this;
        }

        public HttpHeaderBuilder acceptEncoding(String acceptEncoding) {
            this.acceptEncoding = acceptEncoding;
            return this;
        }

        public HttpHeaderBuilder acceptLanguage(String acceptLanguage) {
            this.acceptLanguage = acceptLanguage;
            return this;
        }

        public HttpHeaderBuilder cacheControl(String cacheControl) {
            this.cacheControl = cacheControl;
            return this;
        }

        public HttpHeaderBuilder connection(String connection) {
            this.connection = connection;
            return this;
        }

        public HttpHeaderBuilder host(String host) {
            this.host = host;
            return this;
        }

        public HttpHeaderBuilder userAgent(String userAgent) {
            this.userAgent = userAgent;
            return this;
        }

        public HttpHeader builde(){
            return new HttpHeader(this);
        }
    }

}