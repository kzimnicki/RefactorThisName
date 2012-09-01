package server.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * User: kzimnick
 * Date: 25.08.12
 * Time: 09:13
 */
public class CORSFilter implements Filter{

    public void init(FilterConfig filterConfig) throws ServletException {
        //do nothing
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (response instanceof HttpServletResponse) {
		    ((HttpServletResponse)response).setHeader("Access-Control-Allow-Origin","*");
		}
        chain.doFilter(request, response);
    }

    public void destroy() {
        //do nothing
    }
}
