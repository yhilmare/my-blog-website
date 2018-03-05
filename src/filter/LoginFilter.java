package filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import domain.TipMessage;
import net.sf.json.JSONObject;

public class LoginFilter implements Filter {

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		String loginID = request.getParameter("loginID");
		if (loginID == null){
			TipMessage msg = new TipMessage();
			msg.setMessageCode("-100");
			msg.setMessageDetail("µÇÂ¼ÑéÖ¤ÂëÎ´´«ËÍ");
			JSONObject obj = JSONObject.fromObject(msg);
			response.getWriter().write(obj.toString());
		}else{
			chain.doFilter(request, response);
		}	
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
