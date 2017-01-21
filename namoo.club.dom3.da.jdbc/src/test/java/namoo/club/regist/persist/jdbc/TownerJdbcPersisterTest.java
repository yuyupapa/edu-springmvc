/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 14.
 */
package namoo.club.regist.persist.jdbc;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.TownerPersister;
import namoo.club.regist.persist.jdbc.TownerJdbcPersister;
import namoo.club.util.exception.EmptyResultException;

import org.junit.Before;
import org.junit.Test;

public class TownerJdbcPersisterTest extends BaseJdbcPersisterDbUnitTest {
	//
	private TownerPersister townerPersister;
	
	@Before
	public void setUp() throws Exception {
		//
		super.setUp();
		this.townerPersister = new TownerJdbcPersister(getDataSource());
	}
	
	@Override
	protected String getDatasetXml() {
		// 
		return "/dataset/towners.xml";
	}
	
	@Test
	public void testCreate() {
		//
		Towner towner = new Towner("홍길동", "hong@test.com", "1234");
		townerPersister.create(towner);
		String townerId = towner.getId();
		
		//
		towner = townerPersister.retrieve(townerId);
		assertThat("홍길동", is(towner.getName()));
		assertThat("hong@test.com", is(towner.getEmail()));
		assertThat("1234", is(towner.getPassword()));
	}
	
	@Test
	public void retrieve() {
		//
		Towner towner = townerPersister.retrieve("1");
		
		//
		assertThat("김현오", is(towner.getName()));
		assertThat("hyunohkim@nextree.co.kr", is(towner.getEmail()));
		assertThat("1234", is(towner.getPassword()));
	}
	
	@Test
	public void retrieveByEmail() {
		//
		Towner towner = townerPersister.retrieveByEmail("hyunohkim@nextree.co.kr");
		
		//
		assertThat("김현오", is(towner.getName()));
		assertThat("hyunohkim@nextree.co.kr", is(towner.getEmail()));
		assertThat("1234", is(towner.getPassword()));
	}
	
	@Test
	public void isExistEmail() {
		//
		assertTrue(townerPersister.isExistEmail("hyunohkim@nextree.co.kr"));
	}
	
	@Test
	public void update() {
		//
		Towner towner = townerPersister.retrieve("1");
		towner.setName("김기사");
		
		townerPersister.update(towner);
		//
		towner = townerPersister.retrieve("1");
		assertThat("김기사", is(towner.getName()));
	}
	
	@Test(expected = EmptyResultException.class)
	public void delete() {
		//
		Towner towner = townerPersister.retrieve("1");
		townerPersister.delete(towner);
		towner = townerPersister.retrieve("1");
	}

}
