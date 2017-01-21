/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package com.namoo.club.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.namoo.club.web.session.SessionManager;

@Controller
public class HomeController {

	@RequestMapping("/")
	public String init(HttpServletRequest req) {
		//
		if (SessionManager.getInstance(req).isLogin()) {
			return "redirect:/club/list";
		} else {
			return "redirect:/towner/login";
		}
	}
}
