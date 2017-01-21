/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.service;

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.service.dto.TownerCDto;
import namoo.club.util.exception.NamooClubException;

public interface TownerService {
	//
	/**
	 * 주민 등록
	 * <pre>
	 * 	이미 존재하는 주민일 경우 예외 발생
	 * </pre>
	 * 
	 * @param townerCDto
	 * 
	 * @throws NamooClubException
	 */
	public String registerTowner(TownerCDto townerCDto) throws NamooClubException;
	
	/**
	 * 이메일로 주민 조회
	 * <pre>
	 * 	존재하지 않는 주민일 경우 예외 발생
	 * </pre>
	 * @param email
	 * @return {@link Towner}
	 * 
	 * @throws NamooClubException
	 */
	public Towner findTownerByEmail(String email) throws NamooClubException;
	
	/**
	 * 주민 조회
	 * <pre>
	 * 	존재하지 않는 주민일 경우 예외 발생
	 * </pre>
	 * @param townerId
	 * @return {@link Towner}
	 * 
	 * @throws NamooClubException
	 */
	public Towner findTowner(String townerId) throws NamooClubException;
	
	/**
	 * 주민 정보 수정
	 * 
	 * @param towner
	 */
	public void modifyTowner(Towner towner);
	
	/**
	 * 주민 삭제
	 * <pre>
	 * 	클럽 회원으로 가입되어 있는 경우, 주민이 존재하지 않을 경우 예외 발생 
	 * </pre>
	 * 
	 * @param townerId
	 * 
	 * @throws NamooClubException
	 */
	public void removeTowner(String townerId) throws NamooClubException;
	
	/**
	 * 이메일로 주민 삭제
	 * <pre>
	 * 	클럽 회원으로 가입되어 있는 경우, 주민이 존재하지 않을 경우 예외 발생 
	 * </pre>
	 * 
	 * @param email
	 * 
	 * @throws NamooClubException
	 */
	public void removeTownerByEmail(String email) throws NamooClubException;
	
	/**
	 * 주민 로그인
	 * <pre>
	 * 	주민이 존재하지 않을 경우 예외 발생
	 * </pre>
	 * 
	 * @param email
	 * @param password
	 * @return boolean
	 * 
	 * @throws NamooClubException
	 */
	public boolean loginAsTowner(String email, String password) throws NamooClubException;
}
