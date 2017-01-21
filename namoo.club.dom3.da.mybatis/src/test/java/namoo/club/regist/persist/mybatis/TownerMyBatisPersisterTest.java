/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.mybatis;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.persist.TownerPersister;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@DatabaseSetup(value = "/dataset/towners.xml", type = DatabaseOperation.CLEAN_INSERT)
public class TownerMyBatisPersisterTest extends BaseMyBatisPersisterDbUnitTest {
	//
	@Autowired
	private TownerPersister townerPersister;

	@Test
	public void testCreate() {
		//
		Towner towner = new Towner("홍길동", "hong@test.com", "1234");
		String townerId = townerPersister.create(towner);

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

	@Test
	public void delete() {
		//
		Towner towner = townerPersister.retrieve("1");
		townerPersister.delete(towner);
		towner = townerPersister.retrieve("1");

		assertTrue(towner == null);
	}

}
