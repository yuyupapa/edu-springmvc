/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package com.namoo.club.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.namoo.club.web.session.LoginRequired;
import com.namoo.club.web.session.SessionManager;

public class LoginRequiredInterceptor extends HandlerInterceptorAdapter {
	//
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 
		if (handler instanceof HandlerMethod) {
			// #### 로그인 체크를 해야하는 상황 ####
			// 1. 요청메소드에 LoginRequired(true) 어노테이션이 붙어 있는 경우
			// 2. Controller에 LoginRequired(true) 어노테이션이 붙어 있는 경우
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			
			LoginRequired annoMethod = 
					handlerMethod.getMethodAnnotation(LoginRequired.class);
			LoginRequired annoType = 
					handlerMethod.getBeanType().getAnnotation(LoginRequired.class);
			
			if (annoMethod != null && annoMethod.value() || 
					(annoMethod == null && annoType != null && annoType.value()))  {
				// 로그인 체크
				SessionManager manager = SessionManager.getInstance(request);
				if (!manager.isLogin()) {
					// 로그인이 되어있지 않으면 로그인 페이지로 리다이렉트 
					String contextPath = request.getContextPath();
					response.sendRedirect(contextPath + "/towner/login");
					
					return false;
				}
			}
		}
		return true;
	}
}
