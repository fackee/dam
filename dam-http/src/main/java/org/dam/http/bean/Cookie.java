package org.dam.http.bean;

import org.dam.utils.util.StringUtil;

import java.util.Date;

public class Cookie {
    private String name;
    private String value;
    private Date expires;
    private String path;
    private String domain;
    private boolean secure = false;
    private boolean httpOnly = false;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getExpires() {
        return expires;
    }

    public Cookie setExpires(Date expires) {
        this.expires = expires;
        return this;
    }

    public String getPath() {
        return path;
    }

    public Cookie setPath(String path) {
        this.path = path;
        return this;
    }

    public String getDomain() {
        return domain;
    }

    public Cookie setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public boolean isSecure() {
        return secure;
    }

    public Cookie setSecure(boolean secure) {
        this.secure = secure;
        return this;
    }

    public boolean isHttpOnly() {
        return httpOnly;
    }

    public Cookie setHttpOnly(boolean httpOnly) {
        this.httpOnly = httpOnly;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder cookieString = new StringBuilder("Set-Cookie: ");
        cookieString.append(name+"="+value+"; ");
        if(expires != null){
            cookieString.append("expires="+StringUtil.getGMTDate(expires)+"; ");
        }
        if(path != null){
            cookieString.append("path="+path+"; ");
        }
        if(domain != null){
            cookieString.append("domain="+domain+"; ");
        }
        if(secure){
            cookieString.append("secure; ");
        }
        if(httpOnly){
            cookieString.append("HttpOnly; ");
        }
        return cookieString.substring(0,cookieString.length() - 2);
    }
}