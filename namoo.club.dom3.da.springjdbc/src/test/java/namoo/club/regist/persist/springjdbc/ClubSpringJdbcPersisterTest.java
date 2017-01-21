/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 15.
 */
package namoo.club.regist.persist.springjdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;

import namoo.club.regist.domain.entity.Club;
import namoo.club.regist.domain.entity.ClubAdmin;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.util.exception.EmptyResultException;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = "/dataset/clubs.xml", type = DatabaseOperation.CLEAN_INSERT)
public class ClubSpringJdbcPersisterTest extends BaseSpringJdbcPersisterDbUnitTest {

	@Autowired
	private ClubPersister clubPersister;
	
	@Test
	public void testCreate() {
		//
		Towner towner = new Towner("1");
		towner.setEmail("hyunohkim@nextree.co.kr");
		
		ClubAdmin admin = new ClubAdmin(towner);
		Club club = new Club("프로그래밍 클럽", "프로그래밍에 관한 클럽입니다.", admin);
		
		String clubId = clubPersister.create(club);
		
		//
		club = clubPersister.retrieve(clubId);
		assertThat("프로그래밍 클럽", is(club.getName()));
		assertThat("프로그래밍에 관한 클럽입니다.", is(club.getDescription()));
		assertThat("김현오", is(club.getAdmin().getName()));
		assertThat("hyunohkim@nextree.co.kr", is(club.getAdmin().getEmail()));
	}

	@Test
	public void testRetrieve() {
		//
		Club club = clubPersister.retrieve("1");
		
		// 
		assertThat("축구 클럽", is(club.getName()));
		assertThat("축구로 몸과 마음을 단련하세요.", is(club.getDescription()));
	}

	@Test
	public void testRetrieveByName() {
		//
		Club club = clubPersister.retrieveByName("축구 클럽");
		
		// 
		assertThat("축구 클럽", is(club.getName()));
		assertThat("축구로 몸과 마음을 단련하세요.", is(club.getDescription()));
	}

	@Test
	public void testRetrieveAll() {
		//
		List<Club> clubs = clubPersister.retrieveAll();
		assertTrue(clubs.size() > 0);
	}

	@Test
	public void testUpdate() {
		//
		Club club = clubPersister.retrieve("1");
		club.setName("사커 클럽");
		club.setDescription("사커 클럽입니다.");
		
		clubPersister.update(club);
		
		//
		club = clubPersister.retrieve("1");
		assertThat("사커 클럽", is(club.getName()));
		assertThat("사커 클럽입니다.", is(club.getDescription()));
	}

	@Test(expected = EmptyResultException.class)
	public void testDelete() {
		//
		Club club = clubPersister.retrieve("1");
		clubPersister.delete(club);
		
		club = clubPersister.retrieve("1");
	}

	@Test
	public void testIsExistClubByName() {
		//
		assertTrue(clubPersister.isExistClubByName("축구 클럽"));
	}

	@Test
	public void testChangeAdmin() {
		//
		Towner towner = new Towner("2");
		towner.setName("홍반장");
		towner.setEmail("hong@testcaese.com");
		
		ClubAdmin newAdmin = new ClubAdmin(towner);
		
		clubPersister.changeAdmin("1", newAdmin);
	}

	@Test
	public void testRetreiveAdmin() {
		//
		ClubAdmin admin = clubPersister.retreiveAdmin("1");
		
		assertThat("김현오", is(admin.getName()));
		assertThat("hyunohkim@nextree.co.kr", is(admin.getEmail()));
	}

	@Test
	public void testRetrieveManagedClubs() {
		//
		List<Club> clubs = clubPersister.retrieveManagedClubs("hyunohkim@nextree.co.kr");
		
		assertEquals(1, clubs.size());
	}

}
