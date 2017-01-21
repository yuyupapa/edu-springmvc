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

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.TownerPersister;
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
public class TownerSpringJdbcPersister implements TownerPersister {
	//
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	// -------------------------------------------------------------------------
	
	@Override
	public String create(final Towner towner) {
		// 
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				//
				StringBuilder sql = new StringBuilder();
				sql.append("INSERT INTO ")
					.append(" towner (towner_name, email, password, gender, reg_date) ")
					.append(" VALUES (?, ?, ?, ?, sysdate())");
				
				PreparedStatement pstmt = con.prepareStatement(sql.toString(), 
						Statement.RETURN_GENERATED_KEYS);
				
				// parameters
				pstmt.setString(1, towner.getName());
				pstmt.setString(2, towner.getEmail());
				pstmt.setString(3, towner.getPassword());
				Gender gender = towner.getGender();
				pstmt.setString(4, gender != null ? gender.code() : null);
				
				return pstmt;
			}
		}, keyHolder);
		
		towner.setId(keyHolder.getKey().toString());
		return towner.getId();
	}
	
	@Override
	public Towner retrieve(String townerId) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT towner_id, towner_name, email, password, gender ")
			.append("FROM towner ")
			.append("WHERE towner_id = ?");
		
		try {
			return jdbcTemplate.queryForObject(sql.toString(), 
					new TownerRowMapper(), townerId);
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultException("townerId --> " + townerId);
		}
	}

	@Override
	public Towner retrieveByEmail(String email) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT towner_id, towner_name, email, password, gender ")
			.append("FROM towner ")
			.append("WHERE email = ?");
		
		try {
			return jdbcTemplate.queryForObject(sql.toString(), 
					new TownerRowMapper(), email);
		} catch (EmptyResultDataAccessException e) {
			throw new EmptyResultException("email --> " + email);
		}
	}

	@Override
	public boolean isExistEmail(String email) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT count(*) FROM towner WHERE email = ?");
		
		int count = jdbcTemplate.queryForObject(sql.toString(), 
				Integer.class, email);
		
		return (count > 0) ? true : false;
	}

	@Override
	public void update(Towner towner) {
		//
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE towner ")
			.append("SET towner_name = ?, password = ?, gender = ? ")
			.append("WHERE towner_id = ?");
		
		Gender gender = towner.getGender();
		jdbcTemplate.update(sql.toString(),
				towner.getName(),
				towner.getPassword(),
				gender != null ? gender.code() : null,
				towner.getId());
	}
	
	@Override
	public void delete(Towner towner) {
		// 
		StringBuilder sql = new StringBuilder();
		sql.append("DELETE FROM towner WHERE towner_id = ? ");
		
		jdbcTemplate.update(sql.toString(), towner.getId());
	}
	
	//--------------------------------------------------------------------------
	
	private static class TownerRowMapper implements RowMapper<Towner> {

		@Override
		public Towner mapRow(ResultSet rs, int rowNum) throws SQLException {
			// 
			String townerId = rs.getString("towner_id");
			String townerName = rs.getString("towner_name");
			String townerEmail = rs.getString("email");
			String password = rs.getString("password");
			String gender = rs.getString("gender");

			Towner towner = new Towner(townerName, townerEmail, password);
			towner.setId(townerId);
			towner.setGender(Gender.getByCode(gender));
			
			return towner;
		}
	}
}
