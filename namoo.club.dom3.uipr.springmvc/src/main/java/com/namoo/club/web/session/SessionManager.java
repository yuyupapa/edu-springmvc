/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package com.namoo.club.web.session;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.service.TownerService;
import namoo.club.util.exception.NamooClubException;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class SessionManager {
	//
	private static final String LOGIN_TOWNER = "loginTowner";
	
	private HttpSession session;
	
	private SessionManager(HttpServletRequest req) {
		//
		this.session = req.getSession();
	}
	
	//--------------------------------------------------------------------------
	
	public static SessionManager getInstance(HttpServletRequest req) {
		//
		return new SessionManager(req);
	}

	public boolean isLogin() {
		//
		return (session.getAttribute(LOGIN_TOWNER) != null) ? true : false;
	}
	
	public boolean login(String email, String password) throws NamooClubException {
		//
		WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		TownerService townerService = context.getBean(TownerService.class);

		if (townerService.loginAsTowner(email, password)) {
			Towner loginTowner = townerService.findTownerByEmail(email);
			session.setAttribute(LOGIN_TOWNER, loginTowner);
			return true;
		} else {
			session.invalidate();
			return false;
		}
	}
	
	public void logout() {
		//
		session.invalidate();
	}

	public String getLoginEmail() {
		// 
		if (isLogin()) {
			Towner loginTowner = (Towner) session.getAttribute(LOGIN_TOWNER);
			return loginTowner.getEmail();
		}
		return null;
	}
}
