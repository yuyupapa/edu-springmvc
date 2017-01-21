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
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.namoo.club.web.session.SessionManager;
import com.namoo.club.web.util.MessagePage;

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
	public ModelAndView login(
			@RequestParam("inputEmail") String email, 
			@RequestParam("inputPassword") String password, 
			@RequestParam(value = "redirectUrl", required = false) String redirectUrl,
			HttpServletRequest req) throws NamooClubException {
		// 
		SessionManager sessionManager = SessionManager.getInstance(req);
		if (sessionManager.login(email, password)) {
			//
			if (StringUtils.isEmpty(redirectUrl)) {
				redirectUrl = "/club/list";
			}
			
			return new ModelAndView(new RedirectView(redirectUrl, true));
		} else {
			//
			req.setAttribute("email", email);
			req.setAttribute("password", password);
			
			String message = "로그인에 실패하였습니다. 회원정보를 확인하세요";
			String linkURL = "towner/login";
			
			return MessagePage.error(message, linkURL);
		}
	}
	
	@RequestMapping(value = "logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest req) {
		//
		SessionManager.getInstance(req).logout();
		
		return "redirect:/club/list";
	}
}
