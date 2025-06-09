package mn7.spring.mn7_springboot.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/admin")
    public String home() {
        return "admin/dashboard/show";
    }
}
