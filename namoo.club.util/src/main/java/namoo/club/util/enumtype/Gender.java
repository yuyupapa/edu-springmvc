/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:tsong@nextree.co.kr">Song, Taegook</a>
 * @since 2014. 5. 10.
 */

package namoo.club.util.enumtype;

public enum Gender {
	//
	Male("M", "남성"),
	Female("F", "여성");
	
	private String code;
	private String krName;
	
	//--------------------------------------------------------------------------
	private Gender(String code, String krName) {
		//
		this.code = code;
		this.krName = krName;
	}
	
	public String code() {
		return this.code;
	}
	
	public String krName() {
		return this.krName;
	}
	
	public static Gender getByCode(String code) {
		//
		if (Gender.Male.code.equals("M")) {
			return Gender.Male;
		}
		else if (Gender.Female.code.equals("F")) {
			return Gender.Female; 
		}
		
		return null;
	}
}
