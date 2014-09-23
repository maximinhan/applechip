package com.applechip.core.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.io.EndianUtils;
import org.apache.commons.net.SocketClient;

@Slf4j
public class CustomSocketClient extends SocketClient {

	public static final String SOCKET_CLIENT_STRING = "AppleChip";

	public static final int PACKET_52060 = 52060;

	private static int padding = 4;

	private static int port = 443;

	private DataInputStream dataInputStream;

	private DataOutputStream dataOutputStream;

	public CustomSocketClient() {
		this(port);
	}

	public CustomSocketClient(int port) {
		super.setDefaultPort(port);
	}

	public boolean exists() {
		boolean result = false;

		dataInputStream = new DataInputStream(_input_);
		dataOutputStream = new DataOutputStream(_output_);
		ByteBuffer byteBuffer = ByteBuffer.allocate(1024).order(ByteOrder.LITTLE_ENDIAN);
		byte[] socket = StringUtils.getBytesUtf8(SOCKET_CLIENT_STRING);
		try {
			byteBuffer.putInt(PACKET_52060);
			byteBuffer.putInt(socket.length);
			byteBuffer.put(socket);
			byteBuffer.putInt(0);
			byteBuffer.putInt(0);
			byteBuffer.putInt(0);
			byteBuffer.putInt(0);
			byteBuffer.flip();
			byte[] bytes = new byte[byteBuffer.limit() + padding];
			System.arraycopy(ByteBuffer.allocate(padding).putInt(Integer.reverseBytes(byteBuffer.limit() - padding))
					.array(), 0, bytes, 0, padding);
			System.arraycopy(byteBuffer.array(), 0, bytes, padding, byteBuffer.limit());
			log.debug("size: {}, send: {}", bytes.length, Hex.encodeHexString(bytes));
			byteBuffer.clear();
			dataOutputStream.write(bytes);
			dataOutputStream.flush();
		}
		catch (IOException ioe) {
			log.error("error");
			// throw ioe;
		}
		try {
			boolean header = false;
			int size = EndianUtils.readSwappedInteger(dataInputStream);
			log.debug("size: {}", size);
			if (size > 0)
				header = true;

			if (header) {
				int type = EndianUtils.readSwappedInteger(dataInputStream);
				log.debug("type: {}", type);
				if (type == PACKET_52060) {
					result = true;
				}
			}
		}
		catch (IOException ioe) {
			log.error("error");
			// throw ioe;
		}
		return result;

	}
}
