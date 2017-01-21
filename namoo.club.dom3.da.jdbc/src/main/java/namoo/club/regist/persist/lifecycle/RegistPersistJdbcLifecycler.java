/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 14.
 */
package namoo.club.regist.persist.lifecycle;

import javax.sql.DataSource;

import namoo.club.regist.domain.lifecycle.RegistPersistLifecycler;
import namoo.club.regist.domain.persist.ClubMemberPersister;
import namoo.club.regist.domain.persist.ClubPersister;
import namoo.club.regist.domain.persist.TownerPersister;
import namoo.club.regist.persist.jdbc.ClubJdbcPersister;
import namoo.club.regist.persist.jdbc.ClubMemberJdbcPersister;
import namoo.club.regist.persist.jdbc.TownerJdbcPersister;

public class RegistPersistJdbcLifecycler implements RegistPersistLifecycler {
	//
	private static RegistPersistJdbcLifecycler lifecycler = new RegistPersistJdbcLifecycler(); 
	
	private DataSource dataSource;
	private TownerPersister townerPersister;
	private ClubPersister clubPersister;
	private ClubMemberPersister clubMemberPersister;
	
	//--------------------------------------------------------------------------
	
	private RegistPersistJdbcLifecycler() {
		//
	}
	
	public static RegistPersistLifecycler getInstance(DataSource dataSource) {
		//
		lifecycler.dataSource = dataSource;
		return lifecycler; 
	}
	
	//--------------------------------------------------------------------------
	
	public ClubPersister getClubPersister() {
		// 
		if (clubPersister == null) {
			clubPersister = new ClubJdbcPersister(dataSource);
		}
		return clubPersister;
	}

	public ClubMemberPersister getClubMemberPersister() {
		// 
		if (clubMemberPersister == null) {
			clubMemberPersister = new ClubMemberJdbcPersister(dataSource);
		}
		return clubMemberPersister;
	}

	public TownerPersister getTownerPersister() {
		// 
		if (townerPersister == null) {
			townerPersister = new TownerJdbcPersister(dataSource);
		}
		return townerPersister;
	}
}
