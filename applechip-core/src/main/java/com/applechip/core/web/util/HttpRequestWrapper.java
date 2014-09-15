package com.applechip.core.web.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class HttpRequestWrapper extends HttpServletRequestWrapper {

	private byte[] bodyData;

	public HttpRequestWrapper(HttpServletRequest request) throws IOException {
		super(request);
		InputStream is = super.getInputStream();
		bodyData = IOUtils.toByteArray(is);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream bis = new ByteArrayInputStream(bodyData);
		return new ServletImpl(bis);
	}
}

class ServletImpl extends ServletInputStream {

	private InputStream is;

	public ServletImpl(InputStream bis) {
		is = bis;
	}

	@Override
	public int read() throws IOException {
		return is.read();
	}

	@Override
	public int read(byte[] b) throws IOException {
		return is.read(b);
	}

//	@Override
	public boolean isFinished() {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
	public boolean isReady() {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
	public void setReadListener(ReadListener readListener) {
		// TODO Auto-generated method stub

	}

}