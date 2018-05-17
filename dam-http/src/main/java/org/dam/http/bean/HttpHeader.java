package org.dam.http.bean;

public class HttpHeader {

    private HttpRequestHeader requestHeader;

    private HttpResponseHeader responseHeader;

    public HttpHeader(HttpRequestHeaderBuilder builder) {
        this.requestHeader = builder.builde();
    }

    public HttpHeader(HttpResponseHeaderBuilder builder) {
        this.responseHeader = builder.builder();
    }

    public HttpRequestHeader getRequestHeader() {
        return requestHeader;
    }

    public void setRequestHeader(HttpRequestHeader requestHeader) {
        this.requestHeader = requestHeader;
    }

    public HttpResponseHeader getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(HttpResponseHeader responseHeader) {
        this.responseHeader = responseHeader;
    }

    public static class HttpRequestHeader {

        private URL url;

        private String Connection;
        private String Date;
        private String Pragma;
        private String Trailer;
        private String Transfer_Encoding;
        private String Upgrade;
        private String Via;
        private String Warning;

        private String Accept;

        private String Accept_Charset;

        private String Accept_Encoding;

        private String Accept_Language;

        private String Content_Type;

        private String Authorization;

        private String Form;

        private String Host;

        private String If_Modified_Since;

        private String If_Match;

        private String If_None_Match;

        private String If_Range;

        private String If_Unmodified_Since;

        private String Max_Forwards;

        private String Proxy_Authorization;

        private String Range;

        private String Referer;

        private String User_Agent;

        public HttpRequestHeader(HttpRequestHeaderBuilder builder) {
            this.url = builder.url;
            this.Accept = builder.Accept;
            this.Accept_Charset = builder.Accept_Charset;
            this.Accept_Encoding = builder.Accept_Encoding;
            this.Accept_Language = builder.Accept_Language;
            this.Authorization = builder.Authorization;
            this.Form = builder.Form;
            this.Host = builder.Host;
            this.If_Match = builder.If_Match;
            this.If_Modified_Since = builder.If_Modified_Since;
            this.If_None_Match = builder.If_None_Match;
            this.If_Range = builder.If_Range;
            this.If_Unmodified_Since = builder.If_Unmodified_Since;
            this.Max_Forwards = builder.Max_Forwards;
            this.Proxy_Authorization = builder.Proxy_Authorization;
            this.Range = builder.Range;
            this.Referer = builder.Referer;
            this.User_Agent = builder.User_Agent;
            this.Connection = builder.Connection;
            this.Date = builder.Date;
            this.Pragma = builder.Pragma;
            this.Trailer = builder.Trailer;
            this.Transfer_Encoding = builder.Transfer_Encoding;
            this.Upgrade = builder.Upgrade;
            this.Via = builder.Via;
            this.Warning = builder.Warning;
            this.Content_Type = builder.Content_Type;
        }

        public URL getUrl() {
            return url;
        }

        public String getContent_Type() {
            return Content_Type;
        }

        public String getAbsolutelyUrl(){
            if(getHost() == null || "".equalsIgnoreCase(getHost())){
                return getUrl().getRelativeUrl();
            }
            return getHost() + "/" + getUrl().getRelativeUrl();
        }
        public String getAccept() {
            return Accept;
        }

        public String getAccept_Charset() {
            return Accept_Charset;
        }

        public String getAccept_Encoding() {
            return Accept_Encoding;
        }

        public String getAccept_Language() {
            return Accept_Language;
        }

        public String getAuthorization() {
            return Authorization;
        }

        public String getForm() {
            return Form;
        }

        public String getHost() {
            return Host;
        }

        public String getIf_Modified_Since() {
            return If_Modified_Since;
        }

        public String getIf_Match() {
            return If_Match;
        }

        public String getIf_None_Match() {
            return If_None_Match;
        }

        public String getIf_Range() {
            return If_Range;
        }

        public String getIf_Unmodified_Since() {
            return If_Unmodified_Since;
        }

        public String getMax_Forwards() {
            return Max_Forwards;
        }

        public String getProxy_Authorization() {
            return Proxy_Authorization;
        }

        public String getRange() {
            return Range;
        }

        public String getReferer() {
            return Referer;
        }

        public String getUser_Agent() {
            return User_Agent;
        }

        public String getConnection() {
            return Connection;
        }

        public String getDate() {
            return Date;
        }

        public String getPragma() {
            return Pragma;
        }

        public String getTrailer() {
            return Trailer;
        }

        public String getTransfer_Encoding() {
            return Transfer_Encoding;
        }

        public String getUpgrade() {
            return Upgrade;
        }

        public String getVia() {
            return Via;
        }

        public String getWarning() {
            return Warning;
        }
    }

    public static class HttpRequestHeaderBuilder {

        private URL url;
        private String Connection;
        private String Date;
        private String Pragma;
        private String Trailer;
        private String Transfer_Encoding;
        private String Upgrade;
        private String Via;
        private String Warning;
        private String Accept;

        private String Accept_Charset;

        private String Accept_Encoding;

        private String Accept_Language;

        private String Content_Type;
        private String Authorization;

        private String Form;

        private String Host;

        private String If_Modified_Since;

        private String If_Match;

        private String If_None_Match;

        private String If_Range;

        private String If_Unmodified_Since;

        private String Max_Forwards;

        private String Proxy_Authorization;

        private String Range;

        private String Referer;

        private String User_Agent;


        public HttpRequestHeaderBuilder setUrl(URL url) {
            this.url = url;
            return this;
        }
        public HttpRequestHeaderBuilder setContent_Type(String contentType) {
            this.Content_Type = contentType;
            return this;
        }

        public String getContent_Type() {
            return Content_Type;
        }

        public HttpRequestHeaderBuilder setAccept(String accept) {
            Accept = accept;
            return this;
        }

        public HttpRequestHeaderBuilder setAccept_Charset(String accept_Charset) {
            Accept_Charset = accept_Charset;
            return this;
        }

        public HttpRequestHeaderBuilder setAccept_Encoding(String accept_Encoding) {
            Accept_Encoding = accept_Encoding;
            return this;
        }

        public HttpRequestHeaderBuilder setAccept_Language(String accept_Language) {
            Accept_Language = accept_Language;
            return this;
        }

        public HttpRequestHeaderBuilder setAuthorization(String authorization) {
            Authorization = authorization;
            return this;
        }

        public HttpRequestHeaderBuilder setForm(String form) {
            Form = form;
            return this;
        }

        public HttpRequestHeaderBuilder setHost(String host) {
            Host = host;
            return this;
        }

        public HttpRequestHeaderBuilder setIf_Modified_Since(String if_Modified_Since) {
            If_Modified_Since = if_Modified_Since;
            return this;
        }

        public HttpRequestHeaderBuilder setIf_Match(String if_Match) {
            If_Match = if_Match;
            return this;
        }

        public HttpRequestHeaderBuilder setIf_None_Match(String if_None_Match) {
            If_None_Match = if_None_Match;
            return this;
        }

        public HttpRequestHeaderBuilder setIf_Range(String if_Range) {
            If_Range = if_Range;
            return this;
        }

        public HttpRequestHeaderBuilder setIf_Unmodified_Since(String if_Unmodified_Since) {
            If_Unmodified_Since = if_Unmodified_Since;
            return this;
        }

        public HttpRequestHeaderBuilder setMax_Forwards(String max_Forwards) {
            Max_Forwards = max_Forwards;
            return this;
        }

        public HttpRequestHeaderBuilder setProxy_Authorization(String proxy_Authorization) {
            Proxy_Authorization = proxy_Authorization;
            return this;
        }

        public HttpRequestHeaderBuilder setRange(String range) {
            Range = range;
            return this;
        }

        public HttpRequestHeaderBuilder setReferer(String referer) {
            Referer = referer;
            return this;
        }

        public HttpRequestHeaderBuilder setUser_Agent(String user_Agent) {
            User_Agent = user_Agent;
            return this;
        }

        public HttpRequestHeaderBuilder setConnection(String connection) {
            Connection = connection;
            return this;
        }

        public HttpRequestHeaderBuilder setDate(String date) {
            Date = date;
            return this;
        }

        public HttpRequestHeaderBuilder setPragma(String pragma) {
            Pragma = pragma;
            return this;
        }

        public HttpRequestHeaderBuilder setTrailer(String trailer) {
            Trailer = trailer;
            return this;
        }

        public HttpRequestHeaderBuilder setTransfer_Encoding(String transfer_Encoding) {
            Transfer_Encoding = transfer_Encoding;
            return this;
        }

        public HttpRequestHeaderBuilder setUpgrade(String upgrade) {
            Upgrade = upgrade;
            return this;
        }

        public HttpRequestHeaderBuilder setVia(String via) {
            Via = via;
            return this;
        }

        public HttpRequestHeaderBuilder setWarning(String warning) {
            Warning = warning;
            return this;
        }

        public HttpRequestHeader builde() {
            return new HttpRequestHeader(this);
        }
    }

    public static class HttpResponseHeader {
        private String statusCode;
        private String httpVersion;
        private String Connection;
        private String Date;
        private String Pragma;
        private String Trailer;
        private String Transfer_Encoding;
        private String Upgrade;
        private String Via;
        private String Accept;
        private String Age;
        private String Location;
        private String Proxy_Authenticate;
        private String Public;
        private String Retry_After;
        private String Server;
        private String Vary;
        private String Warning;
        private String WWW_Authenticate;
        private String Allow;
        private String Content_Base;
        private String Content_Language;
        private String Content_Length;
        private String Content_Location;
        private String Content_MD5;
        private String Content_Range;
        private String Content_Type;
        private String Content_Encoding;
        private String ETag;
        private String Expires;
        private String Last_Modified;
        private String Accept_Ranges;
        private String Content_Disposition;
        public String getContent_Disposition() {
            return Content_Disposition;
        }

        public HttpResponseHeader setContent_Disposition(String content_Disposition) {
            Content_Disposition = content_Disposition;
            return this;
        }
        public HttpResponseHeader(HttpResponseHeaderBuilder builder) {
            this.Age = builder.Age;
            this.Location = builder.Location;
            this.Proxy_Authenticate = builder.Proxy_Authenticate;
            this.Public = builder.Public;
            this.Retry_After = builder.Retry_After;
            this.Warning = builder.Warning;
            this.WWW_Authenticate = builder.WWW_Authenticate;
            this.Server = builder.Server;
            this.Vary = builder.Vary;
            this.Allow = builder.Allow;
            this.Content_Base = builder.Content_Base;
            this.Content_Encoding = builder.Content_Encoding;
            this.Content_Language = builder.Content_Language;
            this.Content_Length = builder.Content_Length;
            this.Content_Location = builder.Content_Location;
            this.Content_MD5 = builder.Content_MD5;
            this.Content_Range = builder.Content_Range;
            this.Content_Type = builder.Content_Type;
            this.ETag = builder.ETag;
            this.Expires = builder.Expires;
            this.Last_Modified = builder.Last_Modified;
            this.Connection = builder.Connection;
            this.Date = builder.Date;
            this.Pragma = builder.Pragma;
            this.Trailer = builder.Trailer;
            this.Transfer_Encoding = builder.Transfer_Encoding;
            this.Upgrade = builder.Upgrade;
            this.Via = builder.Via;
            this.httpVersion = builder.httpVersion;
            this.statusCode = builder.statusCode;
            this.Accept_Ranges = builder.Accept_Ranges;
            this.Content_Disposition = builder.Content_Disposition;
        }

        public String getAccept_Ranges() {
            return Accept_Ranges;
        }

        public void setAccept_Ranges(String accept_Ranges) {
            Accept_Ranges = accept_Ranges;
        }

        public String getConnection() {
            return Connection;
        }

        public String getDate() {
            return Date;
        }

        public String getPragma() {
            return Pragma;
        }

        public String getTrailer() {
            return Trailer;
        }

        public String getTransfer_Encoding() {
            return Transfer_Encoding;
        }

        public String getUpgrade() {
            return Upgrade;
        }

        public String getVia() {
            return Via;
        }

        public String getAccept() {
            return Accept;
        }

        public String getAge() {
            return Age;
        }

        public String getLocation() {
            return Location;
        }

        public String getProxy_Authenticate() {
            return Proxy_Authenticate;
        }

        public String getPublic() {
            return Public;
        }

        public String getRetry_After() {
            return Retry_After;
        }

        public String getServer() {
            return Server;
        }

        public String getVary() {
            return Vary;
        }

        public String getWarning() {
            return Warning;
        }

        public String getWWW_Authenticate() {
            return WWW_Authenticate;
        }

        public String getAllow() {
            return Allow;
        }

        public String getContent_Base() {
            return Content_Base;
        }

        public String getContent_Encoding() {
            return Content_Encoding;
        }

        public String getContent_Language() {
            return Content_Language;
        }

        public String getContent_Length() {
            return Content_Length;
        }

        public String getContent_Location() {
            return Content_Location;
        }

        public String getContent_MD5() {
            return Content_MD5;
        }

        public String getContent_Range() {
            return Content_Range;
        }

        public String getContent_Type() {
            return Content_Type;
        }

        public String getETag() {
            return ETag;
        }

        public String getExpires() {
            return Expires;
        }

        public String getLast_Modified() {
            return Last_Modified;
        }

        public String getHttpVersion() {
            return httpVersion;
        }

        public String getStatusCode() {
            return statusCode;
        }

        public void setStatusCode(String statusCode) {
            this.statusCode = statusCode;
        }

        public void setHttpVersion(String httpVersion) {
            this.httpVersion = httpVersion;
        }

        public void setConnection(String connection) {
            Connection = connection;
        }

        public void setDate(String date) {
            Date = date;
        }

        public void setPragma(String pragma) {
            Pragma = pragma;
        }

        public void setTrailer(String trailer) {
            Trailer = trailer;
        }

        public void setTransfer_Encoding(String transfer_Encoding) {
            Transfer_Encoding = transfer_Encoding;
        }

        public void setUpgrade(String upgrade) {
            Upgrade = upgrade;
        }

        public void setVia(String via) {
            Via = via;
        }

        public void setAccept(String accept) {
            Accept = accept;
        }

        public void setAge(String age) {
            Age = age;
        }

        public void setLocation(String location) {
            Location = location;
        }

        public void setProxy_Authenticate(String proxy_Authenticate) {
            Proxy_Authenticate = proxy_Authenticate;
        }

        public void setPublic(String aPublic) {
            Public = aPublic;
        }

        public void setRetry_After(String retry_After) {
            Retry_After = retry_After;
        }

        public void setServer(String server) {
            Server = server;
        }

        public void setVary(String vary) {
            Vary = vary;
        }

        public void setWarning(String warning) {
            Warning = warning;
        }

        public void setWWW_Authenticate(String WWW_Authenticate) {
            this.WWW_Authenticate = WWW_Authenticate;
        }

        public void setAllow(String allow) {
            Allow = allow;
        }

        public void setContent_Base(String content_Base) {
            Content_Base = content_Base;
        }

        public void setContent_Encoding(String content_Encoding) {
            Content_Encoding = content_Encoding;
        }

        public void setContent_Language(String content_Language) {
            Content_Language = content_Language;
        }

        public void setContent_Length(String content_Length) {
            Content_Length = content_Length;
        }

        public void setContent_Location(String content_Location) {
            Content_Location = content_Location;
        }

        public void setContent_MD5(String content_MD5) {
            Content_MD5 = content_MD5;
        }

        public void setContent_Range(String content_Range) {
            Content_Range = content_Range;
        }

        public void setContent_Type(String content_Type) {
            Content_Type = content_Type;
        }

        public void setETag(String ETag) {
            this.ETag = ETag;
        }

        public void setExpires(String expires) {
            Expires = expires;
        }

        public void setLast_Modified(String last_Modified) {
            Last_Modified = last_Modified;
        }
    }

    public static class HttpResponseHeaderBuilder {
        private String Content_Disposition;
        private String Accept_Ranges;
        private String Age;
        private String Location;
        private String Proxy_Authenticate;
        private String Public;
        private String Retry_After;
        private String Server;
        private String Vary;
        private String Warning;
        private String WWW_Authenticate;
        private String Allow;
        private String Content_Base;
        private String Content_Encoding;
        private String Content_Language;
        private String Content_Length;
        private String Content_Location;
        private String Content_MD5;
        private String Content_Range;
        private String Content_Type;
        private String ETag;
        private String Expires;
        private String Last_Modified;
        private String Connection;
        private String Date;
        private String Pragma;
        private String Trailer;
        private String Transfer_Encoding;
        private String Upgrade;
        private String Via;
        private String httpVersion;
        private String statusCode;

        public HttpResponseHeaderBuilder setStatusCode(String statuscode) {
            statusCode = statuscode;
            return this;
        }
        public HttpResponseHeaderBuilder setContent_Disposition(String content_Disposition) {
            Content_Disposition = content_Disposition;
            return this;
        }
        public HttpResponseHeaderBuilder setAccept_Ranges(String accept_Ranges) {
            Accept_Ranges = accept_Ranges;
            return this;
        }

        public HttpResponseHeaderBuilder setHttpVersion(String httpversion) {
            httpVersion = httpversion;
            return this;
        }

        public HttpResponseHeaderBuilder setAge(String age) {
            Age = age;
            return this;
        }

        public HttpResponseHeaderBuilder setLocation(String location) {
            Location = location;
            return this;
        }

        public HttpResponseHeaderBuilder setProxy_Authenticate(String proxy_Authenticate) {
            Proxy_Authenticate = proxy_Authenticate;
            return this;
        }

        public HttpResponseHeaderBuilder setPublic(String aPublic) {
            Public = aPublic;
            return this;
        }

        public HttpResponseHeaderBuilder setRetry_After(String retry_After) {
            Retry_After = retry_After;
            return this;
        }

        public HttpResponseHeaderBuilder setServer(String server) {
            Server = server;
            return this;
        }

        public HttpResponseHeaderBuilder setVary(String vary) {
            Vary = vary;
            return this;
        }

        public HttpResponseHeaderBuilder setWarning(String warning) {
            Warning = warning;
            return this;
        }

        public HttpResponseHeaderBuilder setWWW_Authenticate(String WWW_Authenticate) {
            this.WWW_Authenticate = WWW_Authenticate;
            return this;
        }

        public HttpResponseHeaderBuilder setAllow(String allow) {
            Allow = allow;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_Base(String content_Base) {
            Content_Base = content_Base;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_Encoding(String content_Encoding) {
            Content_Encoding = content_Encoding;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_Language(String content_Language) {
            Content_Language = content_Language;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_Length(String content_Length) {
            Content_Length = content_Length;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_Location(String content_Location) {
            Content_Location = content_Location;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_MD5(String content_MD5) {
            Content_MD5 = content_MD5;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_Range(String content_Range) {
            Content_Range = content_Range;
            return this;
        }

        public HttpResponseHeaderBuilder setContent_Type(String content_Type) {
            Content_Type = content_Type;
            return this;
        }

        public HttpResponseHeaderBuilder setETag(String ETag) {
            this.ETag = ETag;
            return this;
        }

        public HttpResponseHeaderBuilder setExpires(String expires) {
            Expires = expires;
            return this;
        }

        public HttpResponseHeaderBuilder setLast_Modified(String last_Modified) {
            Last_Modified = last_Modified;
            return this;
        }

        public HttpResponseHeaderBuilder setConnection(String connection) {
            Connection = connection;
            return this;
        }

        public HttpResponseHeaderBuilder setDate(String date) {
            Date = date;
            return this;
        }

        public HttpResponseHeaderBuilder setPragma(String pragma) {
            Pragma = pragma;
            return this;
        }

        public HttpResponseHeaderBuilder setTrailer(String trailer) {
            Trailer = trailer;
            return this;
        }

        public HttpResponseHeaderBuilder setTransfer_Encoding(String transfer_Encoding) {
            Transfer_Encoding = transfer_Encoding;
            return this;
        }

        public HttpResponseHeaderBuilder setUpgrade(String upgrade) {
            Upgrade = upgrade;
            return this;
        }

        public HttpResponseHeaderBuilder setVia(String via) {
            Via = via;
            return this;
        }

        public HttpResponseHeader builder() {
            return new HttpResponseHeader(this);
        }
    }

    public static class URL {

        //    GET /itsenlin/article/details/53107304 HTTP/1.1
        //    Host: blog.csdn.net
        private String urlLine;

        public URL(final String urlLine) {
            this.urlLine = urlLine;
        }

        public String getRelativeUrl() {
            int startIndex = urlLine.indexOf('/');
            int endIndex = urlLine.indexOf("HTTP");
            if (startIndex != -1 && endIndex != -1) {
                return urlLine.substring(startIndex, endIndex);
            }
            return "/";
        }

        public HttpMethod getHttpMethod() {
            return methodCase();
        }

        private HttpHeader.HttpMethod methodCase() {
            if (urlLine.contains(Method.GET.toString())) {
                return new HttpHeader.HttpMethod(HttpHeader.Method.GET);
            }
            if (urlLine.contains(Method.POST.toString())) {
                return new HttpHeader.HttpMethod(HttpHeader.Method.POST);
            }
            if (urlLine.contains(Method.HEAD.toString())) {
                return new HttpHeader.HttpMethod(HttpHeader.Method.HEAD);
            }
            if (urlLine.contains(Method.PUT.toString())) {
                return new HttpHeader.HttpMethod(HttpHeader.Method.PUT);
            }
            if (urlLine.contains(Method.DELETE.toString())) {
                return new HttpHeader.HttpMethod(HttpHeader.Method.DELETE);
            }
            if (urlLine.contains(Method.OPTIONS.toString())) {
                return new HttpHeader.HttpMethod(HttpHeader.Method.OPTIONS);
            }
            return new HttpHeader.HttpMethod(HttpHeader.Method.GET);
        }

        public String getHttpVersion() {
            int startIndex = urlLine.indexOf("HTTP");
            return urlLine.substring(startIndex, urlLine.length());
        }
    }

    public static class HttpMethod {

        private Method method;

        public HttpMethod(Method method) {
            this.method = method;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }
    }

    public enum Method {
        GET, POST, HEAD, PUT, DELETE, OPTIONS
    }
}