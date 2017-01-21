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
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubAdmin;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.util.enumtype.Gender;
import namoo.club.util.exception.EmptyResultException;

public class ClubMemberJdbcPersister implements ClubMemberPersister {
	//
	private DataSource dataSource;
	
	// -------------------------------------------------------------------------
	
	public ClubMemberJdbcPersister(DataSource dataSource) {
		//
		this.dataSource = dataSource;
	}
	
	// -------------------------------------------------------------------------
		
	@Override
	public void addMember(String clubId, ClubMember member) {
		// 
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO club_member (member_id, club_id, towner_id, towner_email, reg_date) ")
				.append("VALUES (?, ?, ?, ?, sysdate())");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, clubId);
			pstmt.setString(3, member.getTownerId());
			pstmt.setString(4, member.getEmail());

			// execute
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to add club member.");
		} 
		finally {
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
	public ClubMember retrieve(String clubId, String memberEmail) {
		// 
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ClubMember clubMember = null;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.club_id, a.member_id")
				.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
				.append("FROM club_member a ")
				.append("JOIN towner b ON a.towner_id = b.towner_id ")
				.append("WHERE a.club_id = ? AND a.towner_email = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubId);
			pstmt.setString(2, memberEmail);

			// result mapping
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				clubMember = createClubMemberDomain(rs);
			} 
			else {
				throw new EmptyResultException("memberEmail --> " + memberEmail);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve club member.");
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
		return clubMember; 
	}

	@Override
	public List<ClubMember> retrieveAll(String clubId) {
		//
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<ClubMember> clubMembers = new ArrayList<ClubMember>();
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT a.club_id, a.member_id")
				.append("		, b.towner_id, b.towner_name, b.email, b.gender, b.password ")
				.append("FROM club_member a ")
				.append("JOIN towner b ON a.towner_id = b.towner_id ")
				.append("WHERE a.club_id = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubId);
			
			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			while (rs.next()) {
				clubMembers.add(createClubMemberDomain(rs));
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve all club members.");
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
		return clubMembers;
	}

	@Override
	public List<Club> retrieveJoinedClub(String memberEmail) {
		// 
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Club> clubs = new ArrayList<Club>();
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT b.club_id, b.club_name, b.description, b.open_date ")
				.append("		,c.towner_id, c.towner_name, c.email, c.password, c.gender ")
				.append("FROM club_member a ")
				.append("JOIN club b ON a.club_id = b.club_id ")
				.append("JOIN towner c ON b.admin_id = c.towner_id ")
				.append("WHERE a.towner_email = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, memberEmail);
			
			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			while (rs.next()) {
				clubs.add(createClub(rs));
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve joined clubs.");
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
		return clubs;
	}

	@Override
	public List<Club> retrieveUnjoinedClub(String memberEmail) {
		// 
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Club> clubs = new ArrayList<Club>();
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
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
			
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, memberEmail);
			
			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			while (rs.next()) {
				clubs.add(createClub(rs));
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to retrieve unjoined clubs.");
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
		return clubs;
	}

	@Override
	public void delete(String clubId, String memberEmail) {
		//
		Connection connection = null;
		PreparedStatement pstmt = null;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM club_member ")
				.append("WHERE club_id = ? AND towner_email = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubId);
			pstmt.setString(2, memberEmail);
			
			// execute
			pstmt.executeUpdate();
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to delete club member.");
		} 
		finally {
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
	public boolean hasMember(String clubId, String email) {
		//
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean isExist = false;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(*) ")
				.append("FROM club_member a ")
				.append("WHERE a.club_id = ? AND a.towner_email = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubId);
			pstmt.setString(2, email);

			// result mapping
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				int count = rs.getInt(1);
				isExist = (count > 0) ? true : false;
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to check exist club member.");
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
		return isExist; 
	}

	@Override
	public int countMembers(String clubId) {
		// 
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(*) ")
				.append("FROM club_member a ")
				.append("WHERE a.club_id = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, clubId);

			// result mapping
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				count = rs.getInt(1);
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to count club members.");
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
		return count; 
	}

	@Override
	public boolean hasJoinedClubs(String memberEmail) {
		//
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean hasJoined = false;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(*) ")
				.append("FROM club_member ")
				.append("WHERE towner_email = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, memberEmail);
			
			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				int count = rs.getInt(1);
				hasJoined = (count > 0) ? true : false;
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to check has joined clubs.");
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
		return hasJoined;
	}

	@Override
	public int countJoinedClubs(String memberEmail) {
		// 
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int count = 0;
		
		try {
			connection = dataSource.getConnection();
			
			// prepare statement
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT count(*) ")
				.append("FROM club_member ")
				.append("WHERE towner_email = ?");
			
			pstmt = connection.prepareStatement(sql.toString());
			
			// parameters
			pstmt.setString(1, memberEmail);
			
			// execute
			rs = pstmt.executeQuery();
			
			// result mapping
			if (rs.next()) {
				count = rs.getInt(1);
			} 
		} 
		catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("error to count joined clubs.");
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
		return count;
	}	

	
	// private methods
	
	private ClubMember createClubMemberDomain(ResultSet rs) throws SQLException {
		//
		Club club = new Club(rs.getString("club_id"));
		Towner towner = new Towner(rs.getString("towner_id"));
		towner.setName(rs.getString("towner_name"));
		towner.setEmail(rs.getString("email"));
		towner.setPassword(rs.getString("password"));
		towner.setGender(Gender.getByCode(rs.getString("gender")));
		
		return new ClubMember(club, towner);
	}
	
	private Club createClub(ResultSet rs) throws SQLException {
		// 
		Club club = new Club(rs.getString("club_id"));
		club.setName(rs.getString("club_name"));
		club.setDescription(rs.getString("description"));
		club.setOpenDate(rs.getTimestamp("open_date"));

		Towner towner = new Towner(rs.getString("towner_id"));
		towner.setName(rs.getString("towner_name"));
		towner.setEmail(rs.getString("email"));
		towner.setPassword(rs.getString("password"));
		towner.setGender(Gender.getByCode(rs.getString("gender")));
		
		ClubAdmin admin = new ClubAdmin(towner);
		club.setAdmin(admin);
		
		return club;
	}
}
