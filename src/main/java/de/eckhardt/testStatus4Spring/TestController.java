package de.eckhardt.testStatus4Spring;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    @Value("${spring.cacheDelay}")
    private Long cacheDelay;

    @RequestMapping(value = "/internal/testSessionAttribute")
    public void testSessionAttribute(HttpSession session, @RequestParam String name, @RequestParam String value) {
        session.setAttribute(name, value);
    }

    @Cacheable("testCache")
    @RequestMapping(value = "/internal/testCache")
    public void testCache(@RequestParam String cacheEntry) {
        try {
            Thread.sleep(cacheDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return;
    }

    @Cacheable("testCache2")
    @RequestMapping(value = "/internal/testCache2")
    public String testCache2(@RequestParam String cacheEntry) {
        try {
            Thread.sleep(cacheDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "internal/testCache";
    }

    @ModelAttribute("cacheDelay")
    public Long cacheDelay() {
        return cacheDelay;
    }
}
