package httpserver.http.util;

import httpserver.http.HttpMethod;
import httpserver.http.URL;

public class Parser {

    public static URL getURL(String httpMessage){
        int startIndex = httpMessage.indexOf("Host");
        int endIndex = httpMessage.indexOf("Connection");
        URL url = new URL(httpMessage.substring(startIndex + 5,endIndex).trim());
        return url;
    }

    public static HttpMethod getMethod(String httpMessage){
        int endIndex = httpMessage.indexOf('/');
        String method = httpMessage.substring(0,endIndex).trim();

        switch (method){
            case "GET":
                return new HttpMethod(HttpMethod.Method.GET);
            case "POST":
                return new HttpMethod(HttpMethod.Method.POST);
            case "HEAD":
                return new HttpMethod(HttpMethod.Method.HEAD);
            case "PUT":
                return new HttpMethod(HttpMethod.Method.PUT);
            case "DELETE":
                return new HttpMethod(HttpMethod.Method.DELETE);
            case "OPTIONS":
                return new HttpMethod(HttpMethod.Method.OPTIONS);
        }
        return new HttpMethod(HttpMethod.Method.GET);
    }

    public static String getHttpProtocalVersion(String httpMessage){
        int startIndex = httpMessage.indexOf('/');
        int endIndex = httpMessage.indexOf('\n');
        return httpMessage.substring(startIndex+1,endIndex).trim();
    }




    public static void main(String[] args) {
        String httpMessage = "POST / HTTP/1.1\n" +
                "Host: localhost:8888/users/login\n" +
                "Connection: keep-alive\n" +
                "Content-Length: 20\n" +
                "Cache-Control: max-age=0\n" +
                "Upgrade-Insecure-Requests: 1\n" +
                "Origin: null\n" +
                "Content-Type: application/x-www-form-urlencoded\n" +
                "User-Agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.132 Safari/537.36\n" +
                "Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8\n" +
                "Accept-Encoding: gzip, deflate, br\n" +
                "Accept-Language: zh-CN,zh;q=0.9\n" +
                "\n" +
                "um=qwer&psd=qwer1234";
        System.out.println(getURL(httpMessage).getAbsoluteURL() + "\n" + getURL(httpMessage).getRelativeURL());
        System.out.println(getMethod(httpMessage).getMethod());
        System.out.println(getHttpProtocalVersion(httpMessage));
    }
}