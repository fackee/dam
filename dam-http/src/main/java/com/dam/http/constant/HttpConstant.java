package com.dam.http.constant;

public class HttpConstant {

    private static final String responseHeader = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 38\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n" +
            "\n" +
            "<html>"+
            "<header></header>"+
            "<body>" +
            "<h1>hello</h1><h2>dam</h2>"+
            "</body>"+
            "</html>";

    enum HttpStatusCode{
        OK("OK",200),Created("Created",201),Accepted("Accepted",202),NO_CONTENT("NO Content",204),
        Multiple_Choices("Mutiple Choices",300),Moved_Permanently("Moved Permanently",301),Moved_Temporarily("Moved Temporarily",302),Not_Modified("Not Modified",304),
        Bad_Request("Bad Request",400),Unauthorized("Unauthorized",401),Forbidden("Forbidden",403),Not_Found("Not Found",404),
        Internal_Server_Error("Internal Server Error",500),Not_Implemented("Not Implemented",501),Bad_Gateway("Bad Gateway",502),Service_Unavailable("Service Unavailable",503);



        private String desc;
        private int code;

        HttpStatusCode(String desc,int code){
            this.desc = desc;
            this.code = code;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }
}