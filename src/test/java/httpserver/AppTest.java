package httpserver;

import httpserver.connector.Constant;
import httpserver.util.thread.QueueThreadPool;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.nio.ByteBuffer;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
//        final ByteBuffer byteBuffer = ByteBuffer.allocate(88);
//        ByteBufferHandler byteBufferHandler = new ByteBufferHandler(byteBuffer);
//        for(int i=0;i<20;i++){
//            BufferReader bufferReader = new BufferReader(byteBuffer,byteBufferHandler);
//            new Thread(bufferReader).start();
//            BufferWriter bufferWriter = new BufferWriter(byteBuffer,"writer + "+ i,byteBufferHandler);
//            new Thread(bufferWriter).start();
//        }
//        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
//        System.out.println("Position:"+byteBuffer.position()
//                +"Remaining:"+byteBuffer.hasRemaining()+"limit:"+byteBuffer.limit()
//                +"capacity:"+byteBuffer.capacity()+"mark:"+byteBuffer.mark());
//        for(int i=0;i<256;i++){
//            byteBuffer.putChar('a');
//        }
//        System.out.println("Position:"+byteBuffer.position()
//                +"Remaining:"+byteBuffer.hasRemaining()+"limit:"+byteBuffer.limit()
//                +"capacity:"+byteBuffer.capacity()+"mark:"+byteBuffer.mark());
//        byteBuffer.clear();
//        System.out.println("Position:"+byteBuffer.position()
//                +"Remaining:"+byteBuffer.hasRemaining()+"limit:"+byteBuffer.limit()
//                +"capacity:"+byteBuffer.capacity()+"mark:"+byteBuffer.mark());
//        for(int i=0;i<256;i++){
//            byteBuffer.putChar('a');
//        }
//        System.out.println("Position:"+byteBuffer.position()
//                +"Remaining:"+byteBuffer.hasRemaining()+"limit:"+byteBuffer.limit()
//                +"capacity:"+byteBuffer.capacity()+"mark:"+byteBuffer.mark());
//        byteBuffer.flip();
//        System.out.println("Position:"+byteBuffer.position()
//                +"Remaining:"+byteBuffer.hasRemaining()+"limit:"+byteBuffer.limit()
//                +"capacity:"+byteBuffer.capacity()+"mark:"+byteBuffer.mark());
//        ByteBuffer temp = ByteBuffer.allocate(512);
//        int bufferLength = 5000;
//        for(int i=0;i<temp.capacity()/2;i++){
//            temp.putChar('a');
//        }
//        while (!temp.hasRemaining()){
//            System.out.println(temp.hasRemaining());
//            temp = createBuffer(false,bufferLength);
//            temp.clear();
//            System.out.println(temp.capacity());
//            for(int i=0;i<2400;i++){
//                temp.putChar('a');
//            }
//        }
//        byte[] dst = new byte[2400];
//        temp.get(dst,0,2400);
//        temp.clear();
//        temp = null;
//        System.out.println(new String(dst));
        System.out.println(new QueueThreadPool.ThreadBuilder().maxThread(100).build().toString());
        assertTrue( true );
    }

    private ByteBuffer createBuffer(boolean direct,int length){
        if(direct){
            if(length < Constant.KB){
                return  ByteBuffer.allocateDirect(Constant.KB);
            }else if(length < Constant.KB*4){
                return  ByteBuffer.allocateDirect(Constant.KB*4);
            }else if(length < Constant.KB*128){
                return ByteBuffer.allocateDirect(Constant.KB*128);
            }else if(length < Constant.MB){
                return  ByteBuffer.allocateDirect(Constant.MB);
            }else{
                return ByteBuffer.allocateDirect(Constant.MB*4);
            }
        }
        if(length < Constant.KB){
            return ByteBuffer.allocate(Constant.KB);
        }else if(length < Constant.KB*4){
            return  ByteBuffer.allocate(Constant.KB*4);
        }else if(length < Constant.KB*128){
            return ByteBuffer.allocate(Constant.KB*128);
        }else if(length < Constant.MB){
            return  ByteBuffer.allocate(Constant.MB);
        }else{
            return  ByteBuffer.allocate(Constant.MB*4);
        }
    }
}
