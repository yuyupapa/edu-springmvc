/*
 * COPYRIGHT (c) Nextree Consulting 2014
 * This software is the proprietary of Nextree Consulting.  
 * 
 * @author <a href="mailto:hyunohkim@nextree.co.kr">Kim, HyunOh</a>
 * @since 2014. 5. 14.
 */
package namoo.club.util.exception;

/**
 * 결과 데이터가 없을 때 사용하는 예외 클래스
 */
public class EmptyResultException extends RuntimeException {

	private static final long serialVersionUID = -4815565961483872182L;

	public EmptyResultException(String message) {
		// 
		super(message);
	}
}
