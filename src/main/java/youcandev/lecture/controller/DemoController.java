package youcandev.lecture.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @GetMapping("/public")
    public String publicPage() {
        return "This is a public page. Accessible by anyone.";
    }

    @GetMapping("/user")
    public String userPage() {
        return "This is a user page. Accessible by authenticated users.";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "This is an admin page. Accessible only by admins.";
    }
}