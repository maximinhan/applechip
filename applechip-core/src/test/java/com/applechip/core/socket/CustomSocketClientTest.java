package com.applechip.core.socket;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;


public class CustomSocketClientTest {

	@Test
	public void testExists() throws Exception {
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
		byte[] magicStringBytes = "AppleChip".getBytes("UTF-8");
		byteBuffer.putInt(52060);
		byteBuffer.putInt(magicStringBytes.length);
		byteBuffer.put(magicStringBytes);
		byteBuffer.putInt(0);
		byteBuffer.putInt(0);
		byteBuffer.putInt(0);
		byteBuffer.putInt(0);
		byteBuffer.flip();
		
		System.out.println(byteBuffer.limit());
		System.out.println(byteBuffer.limit() - 4);
		System.out.println(Integer.reverseBytes(byteBuffer.limit() - 4));
		byte[] b1 = toBytes(Integer.reverseBytes(byteBuffer.limit() - 4));
		byte[] b2 = ByteBuffer.allocate(4).putInt(Integer.reverseBytes(byteBuffer.limit() - 4)).array();
		System.out.println(b1.length);
		System.out.println(b2.length);
		System.out.println(Hex.encodeHexString(b1));
		System.out.println(Hex.encodeHexString(b2));
		System.out.println();
	}
	
	public static byte[] toBytes(int value) {
        byte[] dest = new byte[4];
        toBytes(value, dest, 0);
        return dest;
    }
	
	public static void toBytes(int value, byte[] dest, int destPos) {
        for (int i = 0; i < 4; i++) {
            dest[i + destPos] = (byte)(value >> ((7 - i) * 8));
        }
    }

}
