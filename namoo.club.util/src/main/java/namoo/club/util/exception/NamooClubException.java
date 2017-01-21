/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hkkang@nextree.co.kr">Kang, HyoungKoo</a>
 * @since 2014. 5. 14.
 */
package namoo.club.util.exception;

public class NamooClubException extends RuntimeException {
	//
	private static final long serialVersionUID = -8718311926650745043L;

	public NamooClubException(String message) {
		// 
		super(message, null, false, false); 
	}
	
	public NamooClubException(Exception exception) {
		//
		super(exception.getClass().getName() + ": " + exception.getMessage(), null, false, false);
	}
}
