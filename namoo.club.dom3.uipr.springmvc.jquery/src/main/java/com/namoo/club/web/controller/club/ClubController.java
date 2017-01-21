/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package com.namoo.club.web.controller.club;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.service.ClubService;
import namoo.club.regist.domain.service.dto.ClubCDto;
import namoo.club.util.exception.NamooClubException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.namoo.club.web.session.LoginRequired;
import com.namoo.club.web.session.SessionManager;
import com.namoo.club.web.shared.ResponseMessage;

//@RestController
@Controller
@RequestMapping("/club")
@LoginRequired
public class ClubController {
	//
	@Autowired
	private ClubService clubService;
	
	//--------------------------------------------------------------------------
	// 클럽목록 (메인)

	@RequestMapping(method = RequestMethod.GET)
	public String main() {
		//
		return "club/list";
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody ResponseMessage list(HttpServletRequest req) throws NamooClubException {
		// 
		String loginEmail = SessionManager.getInstance(req).getLoginEmail();
		
		// 가입/미가입 클럽 목록
		List<Club> joined = clubService.findJoinedClubs(loginEmail);
		List<Club> unjoined = clubService.findUnjoinedClubs(loginEmail);

		Map<String, Object> clubsMap = new HashMap<String, Object>();
		clubsMap.put("joinedList", joined);
		clubsMap.put("unjoinedList", unjoined);
		
		return new ResponseMessage(true, clubsMap);
	}
	
	//--------------------------------------------------------------------------
	// 클럽개설
	
	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public String open() {
		//
		return "club/open";
	}
	
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage open(
			@RequestParam("clubName") String clubName, 
			@RequestParam("description") String description, 
			HttpServletRequest req) throws NamooClubException {
		// 
		String adminEmail = SessionManager.getInstance(req).getLoginEmail();
		clubService.registerClub(new ClubCDto(clubName, description, adminEmail));
		
		return new ResponseMessage(true);
	}
	
	//--------------------------------------------------------------------------
	// 클럽 회원으로 가입
	
	@RequestMapping(value = "/{clubId}/join", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage processJoinAsMember(
			@PathVariable("clubId") String clubId,
			HttpServletRequest req) throws NamooClubException {
		//
		String email = SessionManager.getInstance(req).getLoginEmail();
		
		try {
			clubService.joinAsMember(clubId, email);
		} catch (NamooClubException e) {
			return new ResponseMessage(e);
		}
		return new ResponseMessage(true);
	}
	
	//--------------------------------------------------------------------------
	// 클럽 회원에서 탈퇴
	
	@RequestMapping(value = "{clubId}/withdrawal", method = RequestMethod.POST)
	public @ResponseBody ResponseMessage processWithdrawalMember(
			@PathVariable("clubId") String clubId,
			HttpServletRequest req) throws NamooClubException {
		//
		String email = SessionManager.getInstance(req).getLoginEmail();
		
		try {
			clubService.withdrawClub(clubId, email);
		} catch (NamooClubException e) {
			return new ResponseMessage(e);
		}
		return new ResponseMessage(true);
	}
}
