package org.dam.server.util;

import org.dam.http.HttpHeader;
import org.dam.http.constant.HttpConstant;
import org.dam.utils.util.serialize.Serialize;

public class HttpSerialization implements Serialize {

    private HttpHeader httpHeader;

    private byte[] source;

    private final byte[] result;

    private int headerIndex = 0;

    public HttpSerialization(){
        result = new byte[500];
    }

    public  HttpSerialization(HttpHeader httpHeader,byte[] source){
        this.httpHeader = httpHeader;
        this.source = source;
        result = new byte[source.length + 1000];
    }


    @Override
    public byte[] serializeHeader() {


        return null;
    }

    @Override
    public byte[] serializeBody() {
        return null;
    }

    private String buildServerErrorMessage(){
        return HttpConstant.HTTP_VERSION
                + HttpConstant.HttpStatusCode.Internal_Server_Error.getDesc()
                + HttpConstant.HTTP_MESSAGE_LINEFEED;
    }

    private void byteCopy(byte[] src,byte[] dest,int destIndex){
        int index = 0;
        while (index < src.length){
            dest[destIndex] = src[index];
            index++;
            destIndex++;
        }
    }

    public static void main(String[] args) {
        String s = "access-control-allow-origin: *\n" +
                "age: 423937\n" +
                "content-encoding: gzip\n" +
                "content-length: 6540\n" +
                "content-md5: f6sJQ1OakNF1KVw009Zg6g==\n" +
                "content-type: text/css; charset=utf-8\n" +
                "date: Sat, 28 Apr 2018 03:31:56 GMT\n" +
                "eagleid: 73e7479515253102536073002e\n" +
                "last-modified: Sat, 28 Apr 2018 03:30:10 GMT\n" +
                "server: Tengine\n" +
                "status: 200\n" +
                "timing-allow-origin: *\n" +
                "vary: Accept-Encoding\n" +
                "via: cache22.l2cm10-1[0,200-0,H], cache10.l2cm10-1[1,0], cache19.cn930[0,200-0,H], cache1.cn930[1,0]\n" +
                "x-cache: HIT TCP_MEM_HIT dirn:0:229317154 mlen:-1\n" +
                "x-oss-hash-crc64ecma: 614567063033863950\n" +
                "x-oss-object-type: Normal\n" +
                "x-oss-request-id: 5AE3EB2CC3FACBBAD441748C\n" +
                "x-oss-server-time: 7\n" +
                "x-oss-storage-class: Standard\n" +
                "x-swift-cachetime: 604800\n" +
                "x-swift-savetime: Sat, 28 Apr 2018 03:31:59 GMT";
        System.out.println(s.length());
    }
}