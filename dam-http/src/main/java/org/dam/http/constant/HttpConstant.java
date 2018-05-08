package org.dam.http.constant;

import sun.dc.pr.PRError;

public class HttpConstant {

    public static final String HTTP_VERSION = "HTTP/1.1 ";
    public static final String HTTP_MESSAGE_LINEFEED = "\r\n";
    private static final String responseHeader = "HTTP/1.1 200 OK\r\n" +
            "Content-Length: 38\r\n" +
            "Content-Type: text/html\r\n" +
            "\r\n" +
            "\n" +
            "<html>" +
            "<header></header>" +
            "<body>" +
            "<h1>hello</h1><h2>dam</h2>" +
            "</body>" +
            "</html>";

    public enum HttpStatusCode {
        OK("200 OK", 200), Created("201 Created", 201), Accepted("202 Accepted", 202), NO_CONTENT("204 NO Content", 204),
        Multiple_Choices("300 Mutiple Choices", 300), Moved_Permanently("301 Moved Permanently", 301), Moved_Temporarily("302 Moved Temporarily", 302), Not_Modified("304 Not Modified", 304),
        Bad_Request("400 Bad Request", 400), Unauthorized("401 Unauthorized", 401), Forbidden("403 Forbidden", 403), Not_Found("Not Found", 404),
        Internal_Server_Error("500 Internal Server Error", 500), Not_Implemented("501 Not Implemented", 501), Bad_Gateway("502 Bad Gateway", 502), Service_Unavailable("503 Service Unavailable", 503);


        private String desc;
        private int code;

        HttpStatusCode(String desc, int code) {
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

//    4.5 General Header Fields
//
//    There are a few header fields which have general applicability for
//    both request and response messages, but which do not apply to the
//    entity being transferred. These header fields apply only to the
//
//    message being transmitted.
//
//            general-header = Cache-Control            ; Section 14.9
//            | Connection               ; Section 14.10
//            | Date                     ; Section 14.18
//            | Pragma                   ; Section 14.32
//            | Trailer                  ; Section 14.40
//            | Transfer-Encoding        ; Section 14.41
//            | Upgrade                  ; Section 14.42
//            | Via                      ; Section 14.45
//            | Warning                  ; Section 14.46
//
//    General-header field names can be extended reliably only in
//    combination with a change in the protocol version. However, new or
//    experimental header fields may be given the semantics of general
//    header fields if all parties in the communication recognize them to
//    be general-header fields. Unrecognized header fields are treated as
//    entity-header fields.


    public enum HttpGeneralHeader {
        Connection("Connection"),
        Date("Date"),
        Pragma("Pragma"),
        Trailer("Trailer"),
        Transfer_Encoding("Transfer-Encoding"),
        Upgrade("Upgrade"),
        Via("Via"),
        Warning("Waining");

        private String generalHeader;

        HttpGeneralHeader(String generalHeader) {
            this.generalHeader = generalHeader;
        }

        public String getGeneralHeader() {
            return generalHeader;
        }
    }


//    5.3 Request Header Fields

//    The request-header fields allow the client to pass additional
//    information about the request, and about the client itself, to the
//    server. These fields act as request modifiers, with semantics

//    equivalent to the parameters on a programming language method
//    invocation.

//           request-header = Accept                   ; Section 14.1
//                          | Accept-Charset           ; Section 14.2
//                          | Accept-Encoding          ; Section 14.3
//                          | Accept-Language          ; Section 14.4
//                          | Authorization            ; Section 14.8
//                          | From                     ; Section 14.22
//                          | Host                     ; Section 14.23
//                          | If-Modified-Since        ; Section 14.24
//                          | If-Match                 ; Section 14.25
//                          | If-None-Match            ; Section 14.26
//                          | If-Range                 ; Section 14.27
//                          | If-Unmodified-Since      ; Section 14.28
//                          | Max-Forwards             ; Section 14.31
//                          | Proxy-Authorization      ; Section 14.34
//                          | Range                    ; Section 14.36
//                          | Referer                  ; Section 14.37
//                          | User-Agent               ; Section 14.42

    public enum HttpRequestLine {
        Accept("Accept"), Accept_Charset("Accept-Charset"), Accept_Encoding("Accept-Encoding"), Accept_Language("Accept-Language"),
        Authorization("Authorization"), Form("Form"), Host("Host"),
        If_Modified_Since("If-Modified-Since"), If_Match("If-Match"), If_None_Match("If-None-Match"), If_Range("If-Range"), If_Unmodified_Since("If-Unmodified-Since"),
        Max_Forwards("Max-Forwards"), Proxy_Authorization("Proxy-Authorization"), Range("Range"), Referer("Referer"), User_Agent("User-Agent");
        private String requestLine;

        HttpRequestLine(String requestLine) {
            this.requestLine = requestLine;
        }

        public String getRequestLine() {
            return requestLine;
        }
    }

// 6.2 Response Header Fields

//    The response-header fields allow the server to pass additional
//    information about the response which cannot be placed in the Status-
//    Line. These header fields give information about the server and about
//    further access to the resource identified by the Request-URI.

//           response-header = Age                     ; Section 14.6
//                           | Location                ; Section 14.30
//                           | Proxy-Authenticate      ; Section 14.33
//                           | Public                  ; Section 14.35
//                           | Retry-After             ; Section 14.38
//                           | Server                  ; Section 14.39
//                           | Vary                    ; Section 14.43
//                           | Warning                 ; Section 14.45
//                           | WWW-Authenticate        ; Section 14.46

//    Response-header field names can be extended reliably only in
//    combination with a change in the protocol version. However, new or
//    experimental header fields MAY be given the semantics of response-
//    header fields if all parties in the communication recognize them to
//    be response-header fields. Unrecognized header fields are treated as
//    entity-header fields.

    public enum HttpResponseLine {

        Age("age"), Location("Location"), Proxy_Authenticate("Proxy-Authenticate"), Public("Public"),
        Retry_After("Retry-After"), Server("Server"), Vary("Vary"), Warning("Warning"),
        WWW_Authenticate("WWW-Authenticate");

        private String responseLine;

        HttpResponseLine(String responseLine) {
            this.responseLine = responseLine;
        }

        public String getResponseLine() {
            return responseLine;
        }
    }

//     7.1 Entity Header Fields

//    Entity-header fields define optional metainformation about the
//    entity-body or, if no body is present, about the resource identified
//    by the request.

//           entity-header  = Allow                    ; Section 14.7
//                          | Content-Base             ; Section 14.11
//                          | Content-Encoding         ; Section 14.12
//                          | Content-Language         ; Section 14.13
//                          | Content-Length           ; Section 14.14
//                          | Content-Location         ; Section 14.15
//                          | Content-MD5              ; Section 14.16
//                          | Content-Range            ; Section 14.17
//                          | Content-Type             ; Section 14.18
//                          | ETag                     ; Section 14.20
//                          | Expires                  ; Section 14.21
//                          | Last-Modified            ; Section 14.29
//                          | extension-header

//           extension-header = message-header

//    The extension-header mechanism allows additional entity-header fields
//    to be defined without changing the protocol, but these fields cannot
//    be assumed to be recognizable by the recipient. Unrecognized header
//    fields SHOULD be ignored by the recipient and forwarded by proxies.

    public enum HttpEntity {

        Allow("Allow"),
        Content_Base("Content-Base"), Content_Encoding("Content-Encoding"), Content_Language("Content-Language"), Content_Length("Content-Length"),
        Content_Location("Content-Location"), Content_MD5("Content-MD5"), Content_Range("Content-Range"), Content_Type("Content-Type"),
        ETag("ETag"), Expires("Expires"), Last_Modified("Last-Modified");

        private String entity;

        HttpEntity(String entity) {
            this.entity = entity;
        }

        public String getEntity() {
            return entity;
        }

    }
}
