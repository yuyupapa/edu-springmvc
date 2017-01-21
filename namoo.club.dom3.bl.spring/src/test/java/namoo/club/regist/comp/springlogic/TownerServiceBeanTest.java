/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 15.
 */

package namoo.club.regist.comp.springlogic;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import namoo.club.regist.domain.entity.Towner;
import namoo.club.regist.domain.service.TownerService;
import namoo.club.regist.domain.service.dto.TownerCDto;
import namoo.club.util.exception.NamooClubException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseOperation;
import com.github.springtestdbunit.annotation.DatabaseSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-test.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class,
    TransactionalTestExecutionListener.class,
    DbUnitTestExecutionListener.class })
@DatabaseSetup(value = {"/dataset/towners.xml"}, type = DatabaseOperation.CLEAN_INSERT)
public class TownerServiceBeanTest {

	@Autowired
	private TownerService townerService;
	
	@Test
	public void test() throws NamooClubException {
		//
		TownerCDto townerCDto = new TownerCDto("새사람", "newperson@nextree.co.kr", "1234");
		townerService.registerTowner(townerCDto);
		
		Towner towner = townerService.findTownerByEmail("newperson@nextree.co.kr");
		assertThat("새사람", is(towner.getName()));
	}
}
