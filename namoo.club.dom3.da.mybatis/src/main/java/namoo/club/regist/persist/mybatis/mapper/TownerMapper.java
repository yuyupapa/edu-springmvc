/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.mybatis.mapper;

import java.util.List;

import namoo.club.regist.domain.entity.Towner;

public interface TownerMapper {

	public void insertTowner(Towner towner);
	
	public Towner selectTowner(String townerId);
	public Towner selectTownerByEmail(String email);
	public List<Towner> selectAllTowners();
	
	public void updateTowner(Towner towner);
	public void deleteTowner(String townerId);
	
	public void countByEmail(String email);

}
