package test;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

public class TestFilter implements Filter {

	private static final Logger log = Logger.getLogger(Test.class);

	@Override
	public void destroy() {
		log.info("filter destroy");
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
		log.info("filter doFilter: " + req.getParameter("name"));

		filterChain.doFilter(req, resp);
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		log.info("filter init: " + config.getFilterName());
	}

}
