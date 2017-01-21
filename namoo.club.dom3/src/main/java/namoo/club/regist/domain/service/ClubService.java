/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.regist.domain.service;

import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.service.dto.ClubCDto;
import namoo.club.util.exception.NamooClubException;

public interface ClubService {

	// -------------------------------------------------------------------------
	// Club 관련 서비스
	
	/**
	 * [주민으로 등록된 경우] 클럽 개설
	 * <pre>
	 * 	이미 주민으로 가입되어 있는 경우 이메일만 필요하다.
	 * 	존재하지 않는 주민인 경우 예외가 발생한다.
	 * </pre> 
	 * 
	 * @param clubName
	 * @param description
	 * @param email
	 * @return 커뮤니티 ID
	 * 
	 * @throws NamooClubException
	 */
	public String registerClub(ClubCDto clubCDto) throws NamooClubException;

	/**
	 * 모든 클럽 목록 조회
	 * 
	 * @return {@link List}<{@link Club}>
	 */
	public List<Club> findAllClubs();
	
	/**
	 * 클럽 조회
	 * <pre>
	 * 	존재하지 않는 클럽일 경우 예외 발생
	 * </pre>
	 * 
	 * @param clubId
	 * @return
	 * @throws NamooClubException
	 */
	public Club findClub(String clubId) throws NamooClubException;
	
	/**
	 * 클럽 정보를 수정한다.
	 * 
	 * @param club
	 */
	public void modifyClub(Club club);
	
	/**
	 * 클럽 삭제
	 * <pre>
	 * 	클럽이 존재하지 않을 경우 예외 발생
	 * </pre>
	 * 
	 * @param clubId
	 * 
	 * @throws NamooClubException
	 */
	public void removeClub(String clubId) throws NamooClubException;

	
	// -------------------------------------------------------------------------
	// Club Member 관련 서비스
	
	/**
	 * [주민으로 등록된 경우] 클럽 가입
	 * 
	 * 이미 주민으로 가입되어 있는 경우 이메일만 필요하다.
	 * 존재하지 않는 주민인 경우 예외가 발생한다. 
	 * 
	 * @param clubId
	 * @param email
	 * 
	 * @throws NamooClubException
	 */
	public void joinAsMember(String clubId, String email) throws NamooClubException;
	
	/**
	 * 클럽 탈퇴
	 * <pre>
	 * 	클럽 관리자일 경우, 클럽이 존재하지 않을 경우 예외 발생
	 * </pre>
	 * 
	 * @param clubId
	 * @param email
	 * 
	 * @throws NamooClubException
	 */
	public void withdrawClub(String clubId, String email) throws NamooClubException;
	
	
	/**
	 * 이메일로 클럽 회원 조회
	 * 
	 * @param clubId
	 * @param email
	 * @return {@link ClubMember}
	 */
	public ClubMember findClubMember(String clubId, String email) throws NamooClubException;
	
	/**
	 * 클럽 회원목록 조회
	 * 
	 * @param clubId
	 * @return {@link List}<{@link ClubMember}>
	 */
	public List<ClubMember> findAllClubMember(String clubId) throws NamooClubException;
	
	/**
	 * 클럽 회원수 조회
	 * 
	 * @param clubId
	 * @return int
	 */
	public int countMembers(String clubId);
	
	/**
	 * 가입된 클럽 목록조회
	 * 
	 * @param email
	 * @return {@link List}<{@link Club}>
	 */
	public List<Club> findJoinedClubs(String email);
	
	/**
	 * 미가입된 클럽 목록조회
	 * 
	 * @param email
	 * @return {@link List}<{@link Club}>
	 */
	public List<Club> findUnjoinedClubs(String email) throws NamooClubException;
	
	/**
	 * 자신이 관리하는 클럽 목록조회
	 * 
	 * @param email
	 * @return {@link List}<{@link Club}>
	 */
	public List<Club> findManagedClubs(String email);
	
	/**
	 * 해당 클럽의 회원이 존재하는지 체크
	 * 
	 * @param clubId
	 * @param email
	 * @return boolean
	 */
	public boolean hasClubMember(String clubId, String email);

}