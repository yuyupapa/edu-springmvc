package namoo.spring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AnnotatedMonitor {
	//
	@Before(value = "execution(* *.perform(..))")
	public void greetPerformer() {
		System.out.println("Greeting performer");
	}

	@AfterReturning(value = "execution(* *.perform(..))")
	public void saySomethingNice() {
		System.out.println("That was great");
	}

	@AfterThrowing(value = "execution(* *.perform(..))")
	public void saySomethingAnyway() {
		System.out.println("It wasn't great.");
	}

	@Around(value = "execution(* *.perform(..))")
	public void around(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("around...before");
		pjp.proceed();
		System.out.println("around...after");
	}
}