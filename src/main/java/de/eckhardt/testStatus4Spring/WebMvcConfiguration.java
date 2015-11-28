package de.eckhardt.testStatus4Spring;

import de.herrschwarz.status4spring.StatusController;
import de.herrschwarz.status4spring.inspectors.HostInspector;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@PropertySource("classpath:application.yml")
public class WebMvcConfiguration {

  @Value("${internal.urls.status}")
  private String statusUrl;

  @Bean
  public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }

  @Bean
  MessageSource messageSource() {
    ReloadableResourceBundleMessageSource messageSource =
        new ReloadableResourceBundleMessageSource();
    messageSource.setBasenames("classpath:messages/status_messages");
    return messageSource;
  }

  @Bean
  public StatusController statusController() {
    StatusController statusController = new StatusController();
    statusController.addHealthInspector(new HostInspector("Mail Server", "zimbra.silpion.de", 25));
    statusController.addHealthInspector(new HostInspector("Mail Server", "zmbra.silpion.de", 25));
    statusController.setVersion("0.0.1 - " + statusUrl);
    // statusController.setHeader("header :: header");
    return statusController;
  }
}
