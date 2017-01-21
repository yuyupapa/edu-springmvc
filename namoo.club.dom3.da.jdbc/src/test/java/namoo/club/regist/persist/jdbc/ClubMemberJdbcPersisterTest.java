/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 14.
 */
package namoo.club.regist.persist.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubMember;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.persist.jdbc.ClubMemberJdbcPersister;
import namoo.club.util.exception.EmptyResultException;

import org.junit.Before;
import org.junit.Test;

public class ClubMemberJdbcPersisterTest extends BaseJdbcPersisterDbUnitTest {
	//
	private ClubMemberJdbcPersister persister;

	@Before
	public void setUp() throws Exception {
		//
		super.setUp();
		this.persister = new ClubMemberJdbcPersister(getDataSource());
	}

	@Override
	protected String getDatasetXml() {
		//
		return "/dataset/club_members.xml";
	}
	
	@Test
	public void testAddMember() {
		//
		String clubId = "1"; // 축구 클럽
		
		String townerId = "2";
		String email = "tsong@nextree.co.kr";
		
		assertFalse(persister.hasMember(clubId, email));
		
		Club club = new Club(clubId);
		
		Towner towner = new Towner(townerId);
		towner.setEmail(email);
		ClubMember clubMember = new ClubMember(club, towner);
		persister.addMember(clubId, clubMember);
		
		//
		assertTrue(persister.hasMember(clubId, email));
	}

	@Test
	public void testRetrieve() {
		//
		ClubMember clubMember = persister.retrieve("1", "hyunohkim@nextree.co.kr");
		
		//
		assertThat("김현오", is(clubMember.getName()));
		assertThat("hyunohkim@nextree.co.kr", is(clubMember.getEmail()));
	}

	@Test
	public void testRetrieveAll() {
		//
		List<ClubMember> members = persister.retrieveAll("1");
		assertThat(1, is(members.size()));
		
		ClubMember clubMember = members.get(0);
		assertThat("김현오", is(clubMember.getName()));
		assertThat("hyunohkim@nextree.co.kr", is(clubMember.getEmail()));
	}

	@Test
	public void testRetrieveJoinedClub() {
		//
		List<Club> clubs = persister.retrieveJoinedClub("hyunohkim@nextree.co.kr");
		
		assertThat(1, is(clubs.size()));
		assertThat("축구 클럽", is(clubs.get(0).getName()));
	}

	@Test
	public void testRetrieveUnjoinedClub() {
		//
		List<Club> clubs = persister.retrieveUnjoinedClub("hyunohkim@nextree.co.kr");
		
		assertThat(1, is(clubs.size()));
		assertThat("농구 클럽", is(clubs.get(0).getName()));
	}

	@Test(expected = EmptyResultException.class)
	public void testDelete() {
		//
		persister.delete("1", "hyunohkim@nextree.co.kr");
		
		// will be exception
		persister.retrieve("1", "hyunohkim@nextree.co.kr");
	}

	@Test
	public void testHasMember() {
		//
		assertTrue(persister.hasMember("1", "hyunohkim@nextree.co.kr"));
		assertFalse(persister.hasMember("1", "tsong@nextree.co.kr"));
	}

	@Test
	public void testCountMembers() {
		//
		int count = persister.countMembers("1");
		assertThat(1, is(count));
	}

	@Test
	public void testHasJoinedClubs() {
		//
		assertTrue(persister.hasJoinedClubs("hyunohkim@nextree.co.kr"));
		assertFalse(persister.hasJoinedClubs("tsong@nextree.co.kr"));
	}

	@Test
	public void testCountJoinedClubs() {
		//
		assertThat(1, is(persister.countJoinedClubs("hyunohkim@nextree.co.kr")));
		assertThat(0, is(persister.countJoinedClubs("tsong@nextree.co.kr")));
	}
}
