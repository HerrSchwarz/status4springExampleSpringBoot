package de.eckhardt.testStatus4Spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import java.util.Arrays;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        System.out.println("Let's inspect the beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
            if ("thymeleafViewResolver".equals(beanName)) {
                ThymeleafViewResolver resolver = (ThymeleafViewResolver) ctx.getBean(beanName);
                for (String name : resolver.getViewNames()) {
                    System.out.println("View Name --> " + name);
                }
            }
        }

        ClassPathResource templates = new ClassPathResource("/templates/status.html");
        System.out.println("/templates/status.html exists on classpath: " + templates.exists());
    }

}
