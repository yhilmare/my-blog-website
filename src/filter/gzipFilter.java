package filter;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
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

public class gzipFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		PrintWriter print = new PrintWriter(new OutputStreamWriter(bout, response.getCharacterEncoding()));
		chain.doFilter(req, (ServletResponse) Proxy.newProxyInstance(gzipFilter.class.getClassLoader(), response.getClass().getInterfaces(), new InvocationHandler(){

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				if(method.getName().equals("getOutputStream")){
					return new GZIPServletOutputStream(bout);
				}else if(method.getName().equals("getWriter")){
					return print;
				}
				return method.invoke(response, args);
			}
			
		}));
		
		bout.close();
		print.close();
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		GZIPOutputStream gout = new GZIPOutputStream(buffer);
		gout.write(bout.toByteArray());
		gout.close();
		
		response.setHeader("content-encoding", "gzip");
		response.setContentLength(buffer.toByteArray().length);
		response.getOutputStream().write(buffer.toByteArray());
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}
	
	public void destroy() {
		
	}
}

class GZIPServletOutputStream extends ServletOutputStream{

	private ByteArrayOutputStream buffer = null;
	
	public GZIPServletOutputStream(ByteArrayOutputStream buffer){
		this.buffer = buffer;
	}
	@Override
	public void write(int b) throws IOException {
		buffer.write(b);
	}
}
