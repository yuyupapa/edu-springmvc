/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package com.namoo.club.web.controller.towner;

import javax.servlet.http.HttpServletRequest;

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.service.TownerService;
import namoo.club.regist.domain.service.dto.TownerCDto;
import namoo.club.util.enumtype.Gender;
import namoo.club.util.exception.NamooClubException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.namoo.club.web.controller.towner.cmd.TownerCommand;
import com.namoo.club.web.session.SessionManager;
import com.namoo.club.web.shared.ResponseMessage;

@Controller
@RequestMapping("/towner")
public class TownerController {
	//
	@Autowired
	private TownerService townerService;

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String registerTownerForm() {
		//
		return "towner/register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage registerTowner(TownerCommand command) throws NamooClubException {
		//
		if (!command.isValid()) {
			return new ResponseMessage(false, "모든 정보를 입력하세요.");
		}
		
		String name = command.getName();
		String email = command.getEmail();
		String password = command.getPassword();
		String password2 = command.getPassword2();

		if (!password.equals(password2)) {
			return new ResponseMessage(false, "패스워드를 확인하세요.");
		}
		
		// 회원가입
		try {
			townerService.registerTowner(new TownerCDto(name, email, password));
			
			// 부가정보 세팅 후 저장
			Towner towner = townerService.findTownerByEmail(email);
			towner.setGender(Gender.getByCode(command.getGender()));
			townerService.modifyTowner(towner);
		} catch (NamooClubException e) {
			return new ResponseMessage(e);
		}
		
		return new ResponseMessage(true);
	}

	@RequestMapping(value = "/withdrawal", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage withdrawal(HttpServletRequest req) throws NamooClubException {
		//
		String loginEmail = SessionManager.getInstance(req).getLoginEmail();
		try {
			townerService.removeTownerByEmail(loginEmail);
			SessionManager.getInstance(req).logout();
		} catch (NamooClubException e) {
			return new ResponseMessage(e);
		}
		
		return new ResponseMessage(true);
	}
}
