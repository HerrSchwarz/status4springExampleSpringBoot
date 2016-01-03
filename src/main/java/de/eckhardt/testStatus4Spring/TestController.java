package de.eckhardt.testStatus4Spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
public class TestController {

    @RequestMapping(value = "/internal/testSessionAttribute")
    public void test(HttpSession session, @RequestParam String name, @RequestParam String value) {
        session.setAttribute(name, value);
    }
}
