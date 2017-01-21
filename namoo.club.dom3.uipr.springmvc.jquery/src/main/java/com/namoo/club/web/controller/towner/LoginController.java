/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package com.namoo.club.web.controller.towner;

import javax.servlet.http.HttpServletRequest;

import namoo.club.util.exception.NamooClubException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.namoo.club.web.session.SessionManager;
import com.namoo.club.web.shared.ResponseMessage;

@Controller
@RequestMapping("/towner")
public class LoginController {
	//
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		//
		return "towner/login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage login(
			@RequestParam("email") String email, 
			@RequestParam("password") String password,
			HttpServletRequest req) throws NamooClubException {
		// 
		SessionManager sessionManager = SessionManager.getInstance(req);
		
		return sessionManager.login(email, password);
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		//
		SessionManager.getInstance(req).logout();
		
		return "redirect:/club/list";
	}
}
