/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 15.
 */
package namoo.club.regist.persist.springjdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubAdmin;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.util.enumtype.Gender;
import namoo.club.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ClubMemberSpringJdbcPersister implements ClubMemberPersister {
	//
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// -------------------------------------------------------------------------
		
	@Override
	public void addMember(String clubId, ClubMember member) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO club_member (member_id, club_id, towner_id, towner_email, reg_date) ")
			.append("VALUES (?, ?, ?, ?, sysdate())");
		
		jdbcTemplate.update(sql.toString(), 
				member.getMemberId(),
				clubId,
				member.getTownerId(),
				member.getEmail());
	}
	
	@Override
	public ClubMember retrieve(String clubId, String memberEmail) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.club_id, a.member_id")
			.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
			.append("FROM club_member a ")
			.append("JOIN towner b ON a.towner_id = b.towner_id ")
			.append("WHERE a.club_id = ? AND a.towner_email = ?");
		
		try {
			return jdbcTemplate.queryForObject(sql.toString(), 
					new ClubMemberRowMapper(), clubId, memberEmail);
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultException("memberEmail --> " + memberEmail);
		}
	}

	@Override
	public List<ClubMember> retrieveAll(String clubId) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.club_id, a.member_id")
			.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
			.append("FROM club_member a ")
			.append("JOIN towner b ON a.towner_id = b.towner_id ")
			.append("WHERE a.club_id = ?");
		
		return jdbcTemplate.query(sql.toString(), 
				new ClubMemberRowMapper(), clubId);
	}

	@Override
	public List<Club> retrieveJoinedClub(String memberEmail) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b.club_id, b.club_name, b.description, b.open_date ")
			.append("		,c.towner_id, c.towner_name, c.email, c.password, c.gender ")
			.append("FROM club_member a ")
			.append("JOIN club b ON a.club_id = b.club_id ")
			.append("JOIN towner c ON b.admin_id = c.towner_id ")
			.append("WHERE a.towner_email = ?");
		
		return jdbcTemplate.query(sql.toString(), 
				new ClubRowMapper(), memberEmail);
	}

	@Override
	public List<Club> retrieveUnjoinedClub(String memberEmail) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT c.club_id, c.club_name, c.description, c.open_date ")
			.append("		, adm.towner_id, adm.towner_name, adm.email, adm.password, adm.gender ")
			.append("FROM club c ")
			.append("JOIN towner adm ON c.admin_id = adm.towner_id ")
			.append("WHERE NOT EXISTS ( ")
			.append("    SELECT * ")
			.append("    FROM club_member cm")
			.append("    JOIN towner tw ON cm.towner_id = tw.towner_id")
			.append("    WHERE cm.club_id = c.club_id AND tw.email = ? ")
			.append(") ");
		
		return jdbcTemplate.query(sql.toString(), 
				new ClubRowMapper(), memberEmail);
	}

	@Override
	public void delete(String clubId, String memberEmail) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM club_member ")
			.append("WHERE club_id = ? AND towner_email = ?");
		
		jdbcTemplate.update(sql.toString(), clubId, memberEmail);
	}
	
	@Override
	public boolean hasMember(String clubId, String email) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) ")
			.append("FROM club_member a ")
			.append("WHERE a.club_id = ? AND a.towner_email = ?");
		
		int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, clubId, email);
		return (count > 0) ? true : false;
	}

	@Override
	public int countMembers(String clubId) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) ")
			.append("FROM club_member a ")
			.append("WHERE a.club_id = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), Integer.class, clubId);
	}

	@Override
	public boolean hasJoinedClubs(String memberEmail) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) ")
			.append("FROM club_member ")
			.append("WHERE towner_email = ?");
		
		int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, memberEmail);
		return (count > 0) ? true : false;
	}

	@Override
	public int countJoinedClubs(String memberEmail) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) ")
			.append("FROM club_member ")
			.append("WHERE towner_email = ?");
		
		return jdbcTemplate.queryForObject(sql.toString(), Integer.class, memberEmail);
	}	

	
	//--------------------------------------------------------------------------
	
	private static class ClubMemberRowMapper implements RowMapper<ClubMember> {

		@Override
		public ClubMember mapRow(ResultSet rs, int rowNum) throws SQLException {
			// 
			Club club = new Club(rs.getString("club_id"));
			Towner towner = new Towner(rs.getString("towner_id"));
			towner.setName(rs.getString("towner_name"));
			towner.setEmail(rs.getString("email"));
			towner.setPassword(rs.getString("password"));
			towner.setGender(Gender.getByCode(rs.getString("gender")));
			
			return new ClubMember(club, towner);
		}
		
	}
	
	private static class ClubRowMapper implements RowMapper<Club> {
		//
		@Override
		public Club mapRow(ResultSet rs, int rowNum) throws SQLException {
			// 
			Club club = new Club(rs.getString("club_id"));
			club.setName(rs.getString("club_name"));
			club.setDescription(rs.getString("description"));
			club.setOpenDate(rs.getTimestamp("open_date"));

			Towner towner = new Towner("towner_id");
			towner.setName("towner_name");
			towner.setEmail("email");
			towner.setPassword("password");
			towner.setGender(Gender.getByCode(rs.getString("gender")));
			
			ClubAdmin admin = new ClubAdmin(towner);
			club.setAdmin(admin);
			
			return club;
		}
	}
}
