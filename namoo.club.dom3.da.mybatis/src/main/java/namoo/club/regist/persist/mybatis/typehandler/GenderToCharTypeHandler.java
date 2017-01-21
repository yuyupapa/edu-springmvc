/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 6. 14.
 */

package namoo.club.regist.persist.mybatis.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import namoo.club.util.enumtype.Gender;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

public class GenderToCharTypeHandler implements TypeHandler<Gender> {

	@Override
	public void setParameter(PreparedStatement ps, int i, Gender parameter, 
			JdbcType jdbcType) throws SQLException {
		// 
		if (parameter != null) {
			ps.setString(i, parameter.code());
		}
		else {
			ps.setString(i, "");
		}
	}

	@Override
	public Gender getResult(ResultSet rs, String columnName) throws SQLException {
		// 
		String code = rs.getString(columnName);
		return Gender.getByCode(code);
	}

	@Override
	public Gender getResult(ResultSet rs, int columnIndex) throws SQLException {
		// 
		String code = rs.getString(columnIndex);
		return Gender.getByCode(code);
	}

	@Override
	public Gender getResult(CallableStatement cs, int columnIndex) throws SQLException {
		// 
		return null;
	}
}
