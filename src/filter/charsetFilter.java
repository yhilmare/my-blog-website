package filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class charsetFilter implements Filter {
	
	private FilterConfig fConfig;
	
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		
		request.setCharacterEncoding(fConfig.getInitParameter("charset"));
		response.setCharacterEncoding(fConfig.getInitParameter("charset"));
		response.setContentType("text/html;charset=" + fConfig.getInitParameter("charset"));
		
		chain.doFilter((HttpServletRequest)Proxy.newProxyInstance(charsetFilter.class.getClassLoader(), request.getClass().getInterfaces(), new InvocationHandler(){

			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				
				if(method.getName().equals("getParameter")){
					Object value = method.invoke(request, args);
					if(value == null) return null;
					String msg = (String) value;
					if(request.getMethod().equalsIgnoreCase("get")){
						String params = new String(msg.getBytes("ISO8859-1"), fConfig.getInitParameter("charset"));
						return params;
					}else{
						return msg;
					}
				}else{
					return method.invoke(request, args);
				}
			}
			
		}), response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		this.fConfig = fConfig;
	}
	
	public void destroy() {
		// TODO Auto-generated method stub
	}
}

//class CasingHttpServletRequest extends HttpServletRequestWrapper{
//
//	private HttpServletRequest request = null;
//	public CasingHttpServletRequest(HttpServletRequest request) {
//		super(request);
//		this.request = request;
//	}
//	
//	public String getParameter(String args){
//		String value = request.getParameter(args);
//		if(value == null) return null;
//		if(request.getMethod().equalsIgnoreCase("get")){
//			String msg = null;
//			try {
//				msg = new String(value.getBytes("ISO8859-1"),"UTF-8");
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			return msg;
//		}else{
//			return value;
//		}
//	}
//	
//}
