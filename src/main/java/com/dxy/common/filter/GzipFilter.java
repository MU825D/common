package com.dxy.common.filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GzipFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse res,FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest request = (HttpServletRequest)req;
         HttpServletResponse response = (HttpServletResponse)res;
         
         GzipResponseWrapper responseWrapper = new GzipResponseWrapper(response);
         
         chain.doFilter(request, responseWrapper);
         
         String responseText = responseWrapper.getResponseText();
         responseWrapper.close();
         
         ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
         GZIPOutputStream gzip = new GZIPOutputStream(byteOut);
         gzip.write(responseText.getBytes("UTF-8"));
         gzip.close();
         
         byte[] bytes = byteOut.toByteArray();
         response.addHeader("Content-Length", String.valueOf(bytes.length));
         response.addHeader("Content-Encoding", "gzip");
         
         ServletOutputStream out = response.getOutputStream();
         out.write(bytes);
         out.flush();
         out.close();
	}

	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	public void destroy() {
		// TODO Auto-generated method stub
		
	}
}
