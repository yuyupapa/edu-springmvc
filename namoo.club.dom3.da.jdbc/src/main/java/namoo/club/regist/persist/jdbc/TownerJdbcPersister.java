/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 14.
 */
package namoo.club.regist.persist.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.TownerPersister;
import namoo.club.util.enumtype.Gender;
import namoo.club.util.exception.EmptyResultException;

public class TownerJdbcPersister implements TownerPersister {
	//
	private DataSource dataSource;
	
	//--------------------------------------------------------------------------
	
	public TownerJdbcPersister(DataSource dataSource) {
		//
		this.dataSource = dataSource;
	}
	
	// -------------------------------------------------------------------------
	
	@Override
	public String create(Towner towner) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet keys = null;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ")
				.append(" towner (towner_name, email, password, gender, reg_date) ")
				.append(" VALUES (?, ?, ?, ?, sysdate())");
			
			pstmt = conn.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			// parameters
			pstmt.setString(1, towner.getName());
			pstmt.setString(2, towner.getEmail());
			pstmt.setString(3, towner.getPassword());
			Gender gender = towner.getGender();
			pstmt.setString(4, gender != null ? gender.code() : null);

			// execute
			pstmt.executeUpdate();
			
			// generated key
			keys = pstmt.getGeneratedKeys();
			if (keys.next()) {
				towner.setId(keys.getString(1));
			}
			
			return towner.getId();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to create towner.");
		} 
		finally {
			if (keys != null) {
				try {
					keys.close();
				} catch(Exception e) { }
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) { }
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) { }
			}
		}
	}
	
	@Override
	public Towner retrieve(String townerId) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Towner towner = null;
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT towner_id, towner_name, email, password, gender ")
				.append("FROM towner ")
				.append("WHERE towner_id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, townerId);

			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				towner = createTownerDomain(rs);
			} 
			else {
				throw new EmptyResultException("TownerId --> " + townerId);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve towner.");
		} 
		finally { // release database resources
			if (rs != null) {
				try {
					rs.close();
				} catch(Exception e) { }
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) { }
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) { }
			}
		}
		return towner;
	}

	@Override
	public Towner retrieveByEmail(String email) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Towner towner = null;
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT towner_id, towner_name, email, password, gender ")
				.append("FROM towner ")
				.append("WHERE email = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, email);

			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				towner = createTownerDomain(rs);
			} 
			else {
				throw new EmptyResultException("Towner Email --> " + email);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve towner.");
		} 
		finally { // release database resources
			if (rs != null) {
				try {
					rs.close();
				} catch(Exception e) { }
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) { }
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) { }
			}
		}
		return towner;
	}

	@Override
	public void update(Towner towner) {
		//
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE towner ")
				.append("SET towner_name = ?, password = ?, gender = ? ")
				.append("WHERE towner_id = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, towner.getName());
			pstmt.setString(2, towner.getPassword());
			Gender gender = towner.getGender();
			pstmt.setString(3, gender != null ? gender.code() : null);
			pstmt.setString(4, towner.getId());
			
			// update
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to update towner.");
		} 
		finally { // release database resources
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) { }
			}
			
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) { }
			}
		}
	}
	
	@Override
	public void delete(Towner towner) {
		// 
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM towner WHERE towner_id = ? ");
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, towner.getId());
			
			// execute
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to delete towner");
		} 
		finally { // release database resources
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) { }
			}
			
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) { }
			}
		}
	}
	
	@Override
	public boolean isExistEmail(String email) {
		// 
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isExist = false;
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(*) FROM towner WHERE email = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, email);

			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				int count = rs.getInt(1);
				isExist = (count > 0) ? true : false;
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to check exist towner by email.");
		} 
		finally { // release database resources
			if (rs != null) {
				try {
					rs.close();
				} catch(Exception e) { }
			}
			
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e) { }
			}
			
			if (conn != null) {
				try {
					conn.close();
				} catch (Exception e) { }
			}
		}
		return isExist;
	}
	
	// private methods
	
	private Towner createTownerDomain(ResultSet rs) throws SQLException {
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
