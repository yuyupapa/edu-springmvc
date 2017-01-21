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
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubAdmin;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.util.enumtype.Gender;
import namoo.club.util.exception.EmptyResultException;

public class ClubJdbcPersister implements ClubPersister {
	//
	private DataSource dataSource;
	
	//--------------------------------------------------------------------------
	
	public ClubJdbcPersister(DataSource dataSource) {
		//
		this.dataSource = dataSource;
	}
	
	//--------------------------------------------------------------------------

	@Override
	public String create(Club club) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet keys = null;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO ")
				.append("club (club_name, description, admin_id, open_date) ")
				.append("VALUES (?, ?, ?, sysdate())");
			
			pstmt = conn.prepareStatement(sql.toString(), 
					Statement.RETURN_GENERATED_KEYS);
			
			// parameters
			pstmt.setString(1, club.getName());
			pstmt.setString(2, club.getDescription());
			pstmt.setString(3, club.getAdmin().getId());

			// execute
			pstmt.executeUpdate();
			
			// generated key
			keys = pstmt.getGeneratedKeys();
			if (keys.next()) {
				club.setId(keys.getString(1));
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to create club.");
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
		
		return club.getId();
	}
	
	@Override
	public Club retrieve(String clubId) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Club club = null;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.club_id, a.club_name, a.description, a.open_date ")
				.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
				.append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ")
				.append("WHERE club_id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubId);

			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				club = createClubDomain(rs);
			} 
			else {
				throw new EmptyResultException("ClubId --> " + clubId);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve club.");
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
		return club;
	}

	@Override
	public Club retrieveByName(String clubName) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Club club = null;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.club_id, a.club_name, a.description, a.open_date ")
				.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
				.append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ")
				.append("WHERE a.club_name = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubName);

			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				club = createClubDomain(rs);
			} 
			else {
				throw new EmptyResultException("clubName --> " + clubName);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve club by name.");
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
		return club;
	}

	@Override
	public List<Club> retrieveAll() {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Club> clubs = new ArrayList<Club>();
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.club_id, a.club_name, a.description, a.open_date ")
				.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
				.append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			while (rs.next()) {
				clubs.add(createClubDomain(rs));
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve all clubs.");
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
		return clubs;
	}

	@Override
	public void update(Club club) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE club ")
				.append("SET club_name = ?, description = ? ")
				.append("WHERE club_id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, club.getName());
			pstmt.setString(2, club.getDescription());
			pstmt.setString(3, club.getId());

			// execute
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to update club.");
		} 
		finally {
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
	public void delete(Club club) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM club WHERE club_id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, club.getId());

			// execute
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to delete club.");
		} 
		finally {
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
	public boolean isExistClubByName(String clubName) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isExist = false;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(*) FROM club WHERE club_name = ?");
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubName);

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
			throw new RuntimeException("error to check exist club by name.");
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

	@Override
	public void changeAdmin(String clubId, ClubAdmin admin) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("UPDATE club SET admin_id = ? WHERE club_id = ?");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, admin.getId());
			pstmt.setString(2, clubId);

			// execute
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to change club admin.");
		} 
		finally {
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
	public ClubAdmin retreiveAdmin(String clubId) {
		//
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ClubAdmin clubAdmin = null;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT b.towner_id, b.towner_name, b.email, b.gender, b.password ")
				.append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ")
				.append("WHERE a.club_id = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubId);

			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				Towner towner = new Towner(rs.getString("towner_id"));
				towner.setName(rs.getString("towner_name"));
				towner.setEmail(rs.getString("email"));
				towner.setGender(Gender.getByCode(rs.getString("gender")));
				towner.setPassword(rs.getString("password"));
				
				clubAdmin = new ClubAdmin(towner);
			} 
			else {
				throw new EmptyResultException("clubId --> " + clubId);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve club admin.");
		} 
		finally {
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
			
			if (connection != null) {
				try {
					connection.close();
				} catch (Exception e) { }
			}
		}
		return clubAdmin; 
	}

	@Override
	public List<Club> retrieveManagedClubs(String email) {
		//
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Club> clubs = new ArrayList<Club>();
		
		try {
			conn = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT club_id, club_name, description, open_date ")
				.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
				.append("FROM club a ")
				.append("JOIN towner b ON a.admin_id = b.towner_id ")
				.append("WHERE b.email = ? ");
			
			pstmt = conn.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, email);
			
			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			while (rs.next()) {
				clubs.add(createClubDomain(rs));
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve managed clubs.");
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
		return clubs;
	}
	

	// private methods
	
	private Club createClubDomain(ResultSet rs) throws SQLException {
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
