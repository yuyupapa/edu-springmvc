/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 15.
 */
package namoo.club.regist.persist.springjdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubAdmin;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.util.enumtype.Gender;
import namoo.club.util.exception.EmptyResultException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ClubSpringJdbcPersister implements ClubPersister {
	//
	@Autowired
	private JdbcTemplate jdbcTemplate;

	// --------------------------------------------------------------------------

	@Override
	public String create(final Club club) {
		//
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {

			@Override
			public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
				//
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO ").append("club (club_name, description, admin_id, open_date) ").append("VALUES (?, ?, ?, sysdate())");

				PreparedStatement pstmt = con.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);

				// parameters
				pstmt.setString(1, club.getName());
				pstmt.setString(2, club.getDescription());
				pstmt.setString(3, club.getAdmin().getId());

				return pstmt;
			}
		}, keyHolder);

		club.setId(keyHolder.getKey().toString());
		return club.getId();
	}


	@Override
	public Club retrieve(String clubId) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.club_id, a.club_name, a.description, a.open_date ").append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ").append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ").append("WHERE club_id = ?");

		 try {
		 return jdbcTemplate.queryForObject(sql.toString(), new
		 ClubRowMapper(), clubId);
		 } catch (EmptyResultDataAccessException e) {
		 throw new EmptyResultException("clubId --> " + clubId);
		 }
	}

	@Override
	public Club retrieveByName(String clubName) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.club_id, a.club_name, a.description, a.open_date ").append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ").append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ").append("WHERE a.club_name = ?");

		try {
			return jdbcTemplate.queryForObject(sql.toString(), new ClubRowMapper(), clubName);
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultException("clubName --> " + clubName);
		}
	}

	@Override
	public List<Club> retrieveAll() {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT a.club_id, a.club_name, a.description, a.open_date ").append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ").append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ");

		return jdbcTemplate.query(sql.toString(), new ClubRowMapper());
	}

	@Override
	public void update(Club club) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE club ").append("SET club_name = ?, description = ? ").append("WHERE club_id = ?");

		jdbcTemplate.update(sql.toString(), club.getName(), club.getDescription(), club.getId());
	}

	@Override
	public void delete(Club club) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM club WHERE club_id = ?");

		jdbcTemplate.update(sql.toString(), club.getId());
	}

	@Override
	public boolean isExistClubByName(String clubName) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) FROM club WHERE club_name = ?");

		int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class, clubName);
		return (count > 0) ? true : false;
	}

	@Override
	public void changeAdmin(String clubId, ClubAdmin admin) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE club SET admin_id = ? WHERE club_id = ?");

		jdbcTemplate.update(sql.toString(), admin.getId(), clubId);
	}

	@Override
	public ClubAdmin retreiveAdmin(String clubId) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT b.towner_id, b.towner_name, b.email, b.gender, b.password ").append("FROM club a ").append("JOIN towner b ON a.admin_id = b.towner_id ").append("WHERE a.club_id = ?");

		try {
			return jdbcTemplate.queryForObject(sql.toString(), new RowMapper<ClubAdmin>() {
				@Override
				public ClubAdmin mapRow(ResultSet rs, int rowNum) throws SQLException {
					//
					Towner towner = new Towner(rs.getString("towner_id"));
					towner.setName(rs.getString("towner_name"));
					towner.setEmail(rs.getString("email"));
					towner.setGender(Gender.getByCode(rs.getString("gender")));
					towner.setPassword(rs.getString("password"));

					return new ClubAdmin(towner);
				}
			}, clubId);
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultException("clubId --> " + clubId);
		}
	}

	@Override
	public List<Club> retrieveManagedClubs(String email) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT club_id, club_name, description, open_date ").append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ").append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ").append("WHERE b.email = ? ");

		return jdbcTemplate.query(sql.toString(), new ClubRowMapper(), email);
	}

	// --------------------------------------------------------------------------

	private static class ClubRowMapper implements RowMapper<Club> {
		//
		@Override
		public Club mapRow(ResultSet rs, int rowNum) throws SQLException {
			//
			Club club = new Club(rs.getString("club_id"));
			club.setName(rs.getString("club_name"));
			club.setDescription(rs.getString("description"));
			club.setOpenDate(rs.getTimestamp("open_date"));

			Towner towner = new Towner(rs.getString("towner_id"));
			towner.setName(rs.getString("towner_name"));
			towner.setEmail(rs.getString("email"));
			towner.setGender(Gender.getByCode(rs.getString("gender")));
			towner.setPassword(rs.getString("password"));

			club.setAdmin(new ClubAdmin(towner));

			return club;
		}
	}
}
