package com.dingxy.common.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.log4j.Logger;

public class GzipResponseWrapper extends HttpServletResponseWrapper {
	private final static Logger LOGGER = Logger
			.getLogger(GzipResponseWrapper.class);

	private ByteArrayOutputStream responseStream = null;
	private String responseText = "";

	public GzipResponseWrapper(HttpServletResponse response) {
		super(response);
		responseStream = new ByteArrayOutputStream();
	}

	public String getResponseText() {
		if ("".equals(responseText)) {
			try {
				responseText = new String(responseStream.toByteArray(), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				LOGGER.error(e.getLocalizedMessage(), e);
			}
		}
		return responseText;
	}

	public byte[] getStreamBytes() {
		return responseStream.toByteArray();
	}

	public void close() {
		try {
			responseStream.close();
		} catch (IOException e) {
			LOGGER.error(e.getLocalizedMessage(), e);
		}
	}

	@Override
	public ServletOutputStream getOutputStream() {
		return (new WxServletOutPutStream());
	}

	@Override
	public PrintWriter getWriter() {
		return (new WxPrintWriter(new PrintWriter(new WxServletOutPutStream())));
	}

	class WxServletOutPutStream extends ServletOutputStream {

		@Override
		public void flush() throws IOException {
			super.flush();
		}

		@Override
		public void close() throws IOException {
			super.close();
		}

		@Override
		public void write(int b) throws IOException {
			write((byte) b);
		}

		@Override
		public void write(byte[] b) throws IOException {
			write(b, 0, b.length);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			responseStream.write(b, off, len);
		}

		@Override
		public boolean isReady() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			// TODO Auto-generated method stub

		}

	}

	class WxPrintWriter extends PrintWriter {

		public WxPrintWriter(Writer out) {
			super(out);
		}

		@Override
		public void flush() {
		}

		@Override
		public void close() {
		}

		@Override
		public void write(char[] buf, int off, int len) {
			responseText += String.copyValueOf(buf, off, len);
		}

		@Override
		public void write(char[] buf) {
			write(buf, 0, buf.length);
		}

		@Override
		public void write(String s, int off, int len) {
			responseText += s.subSequence(off, off + len);
		}

		@Override
		public void println(int x) {
			char[] chars = new char[] { (char) x };
			write(chars);
		}

		@Override
		public void write(String s) {
			responseText += s;
		}

	}
}
