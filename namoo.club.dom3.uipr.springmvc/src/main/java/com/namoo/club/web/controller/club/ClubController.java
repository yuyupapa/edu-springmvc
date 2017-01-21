/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 6. 15.
 */

package com.namoo.club.web.controller.club;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.service.ClubService;
import namoo.club.regist.domain.service.dto.ClubCDto;
import namoo.club.util.exception.NamooClubException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.namoo.club.web.session.LoginRequired;
import com.namoo.club.web.session.SessionManager;
import com.namoo.club.web.util.MessagePage;

@Controller
@RequestMapping("/club")
@LoginRequired
public class ClubController {
	//
	@Autowired
	private ClubService clubService;
	
	//--------------------------------------------------------------------------
	// 클럽목록 (메인)

	@LoginRequired(false)
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String main(HttpServletRequest req) throws NamooClubException {
		// 
		String loginEmail = SessionManager.getInstance(req).getLoginEmail();
		
		if (!StringUtils.isEmpty(loginEmail)) {
			// 로그인 : 가입/미가입 클럽 목록
			List<Club> joined = clubService.findJoinedClubs(loginEmail);
			List<Club> unjoined = clubService.findUnjoinedClubs(loginEmail);

			req.setAttribute("joinedList", joined);
			req.setAttribute("unjoinedList", unjoined);
			
		} else {
			// 비로그인 : 전체 클럽 목록 
			List<Club> allClubs = clubService.findAllClubs();
			req.setAttribute("allClubs", allClubs);
		}
		return "club/list";
	}
	
	//--------------------------------------------------------------------------
	// 클럽개설
	
	@RequestMapping(value = "/open", method = RequestMethod.GET)
	public String open() {
		//
		return "club/open";
	}
	
	@RequestMapping(value = "/open", method = RequestMethod.POST)
	public ModelAndView open(
			@RequestParam("clubName") String clubName, 
			@RequestParam("description") String description, 
			HttpServletRequest req) throws NamooClubException {
		// 
		String adminEmail = SessionManager.getInstance(req).getLoginEmail();

		clubService.registerClub(new ClubCDto(clubName, description, adminEmail));
		
		String message = "클럽 생성이 완료되었습니다.";
		String linkURL = "/club/list";

		return MessagePage.information(message, linkURL);
	}
	
	//--------------------------------------------------------------------------
	// 클럽 회원으로 가입
	
	@RequestMapping(value = "/{clubId}/join/confirm", method = RequestMethod.GET)
	public ModelAndView confirmJoinAsMember(
			@PathVariable("clubId") String clubId, 
			HttpServletRequest req) throws NamooClubException {
		// 
		Club club = clubService.findClub(clubId);
		
		String email = SessionManager.getInstance(req).getLoginEmail();
		req.setAttribute("club", club);
		
		if (clubService.hasClubMember(clubId, email)) {
			//
			String message = club.getName() + " 는 이미 가입된 클럽 입니다.";
			String confirmURL = "/club/list";
			
			return MessagePage.error(message, confirmURL);
		} else {
			//
			String message = club.getName() + " 회원으로 가입하시겠습니까?";
			String confirmURL = "/club/" + clubId + "/join";
			String cancelURL = "/club/list";
			
			return MessagePage.confirm(message, confirmURL, cancelURL);
		}
	}
	
	@RequestMapping(value = "/{clubId}/join", method = RequestMethod.GET)
	public ModelAndView processJoinAsMember(
			@PathVariable("clubId") String clubId,
			HttpServletRequest req) throws NamooClubException {
		//
		String email = SessionManager.getInstance(req).getLoginEmail();
		
		clubService.joinAsMember(clubId, email);
		
		String message = "클럽 회원으로 가입되었습니다.";
		String linkURL = "/club/list"; 
		return MessagePage.information(message, linkURL);
	}
	
	//--------------------------------------------------------------------------
	// 클럽 회원에서 탈퇴
	
	@RequestMapping(value = "/{clubId}/withdrawal/confirm", method = RequestMethod.GET)
	public ModelAndView confirmWithdrawalMember(
			@PathVariable("clubId") String clubId) throws NamooClubException {
		//
		Club club = clubService.findClub(clubId);
		
		String message = "["+club.getName()+"] 에서 탈퇴신청 하시겠습니까?";
		String linkURL = "/club/" + clubId + "/withdrawal"; 
		
		ModelAndView mav = MessagePage.confirm(message, linkURL);
		mav.addObject("club", club);
		
		return mav;
	}
	
	@RequestMapping(value = "{clubId}/withdrawal")
	public ModelAndView processWithdrawalMember(
			@PathVariable("clubId") String clubId,
			HttpServletRequest req) throws NamooClubException {
		//
		String email = SessionManager.getInstance(req).getLoginEmail();
		
		clubService.withdrawClub(clubId, email);
		
		String message = "클럽에서 탈퇴되었습니다.";
		String linkURL = "/club/list"; 
		return MessagePage.information(message, linkURL);
	}
}
