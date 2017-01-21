package namoo.spring.aop;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AnnotatedAopApp {
	public static void main(String[] args) throws Exception {
		ApplicationContext context = new ClassPathXmlApplicationContext("application-context-test.xml");
		Performer singer = context.getBean("bo", Performer.class);
		singer.perform();
	}
}