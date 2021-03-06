package hu.hw.cloud.server.security;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
//import org.springframework.stereotype.Component;

//@Component
public class HttpLogoutSuccessHandler implements LogoutSuccessHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpLogoutSuccessHandler.class);
	
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException {
		LOGGER.info("onLogoutSuccess()");
		response.setStatus(HttpServletResponse.SC_OK);
		response.getWriter().flush();
	}
}
