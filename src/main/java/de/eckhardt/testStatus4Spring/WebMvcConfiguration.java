package de.eckhardt.testStatus4Spring;

import com.github.herrschwarz.status4spring.StatusController;
import com.github.herrschwarz.status4spring.cache.ConcurrentMapCacheStatsProvider;
import com.github.herrschwarz.status4spring.inspectors.HostInspector;
import com.google.common.collect.ImmutableMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.Map;

@Configuration
@EnableWebMvc
public class WebMvcConfiguration {

  @Autowired
  private CacheManager cacheManager;

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
    statusController.setVersion("0.0.1");
    statusController.setCustomHeaderEntries(customHeaderEntries());
    statusController.setPageTitle("Application status");
    statusController.setSessionEnabled(true);
    ConcurrentMapCacheStatsProvider cacheStatsProvider =
            new ConcurrentMapCacheStatsProvider((ConcurrentMapCacheManager) cacheManager);
    statusController.setCacheStatsProvider(cacheStatsProvider);
    return statusController;
  }

  @Bean
  public TestController testController() {
    return new TestController();
  }

  private Map customHeaderEntries() {
    return ImmutableMap.of("Fill session", "/internal/testSessionAttribute?name=test&value=42",
            "Cached request", "/internal/testCache?cacheEntry=3", "Cached request 2", "/internal/testCache2?cacheEntry=4");
  }
}
