package mn7.spring.mn7_springboot.controller.client;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import mn7.spring.mn7_springboot.domain.Products;
import mn7.spring.mn7_springboot.domain.User;
import mn7.spring.mn7_springboot.domain.dto.RegisterDto;
import mn7.spring.mn7_springboot.service.ProductService;
import mn7.spring.mn7_springboot.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/")
    public String home(Model model) {
        List<Products> product = this.productService.getAllProducts();
        model.addAttribute("products", product);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerForm", new RegisterDto());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String handleRegister(
            @Valid @ModelAttribute("registerForm") RegisterDto registerDto,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {
            return "client/auth/register"; // hoặc đường dẫn đúng của file JSP
        }

        User user = userService.registerDTOtoUser(registerDto);
        String hashPassword = this.passwordEncoder.encode(user.getPassword());

        user.setPassword(hashPassword);
        user.setRole(this.userService.getRoleByName("USER"));

        this.userService.handleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "client/auth/login";
    }

    @GetMapping("/access-denied")
    public String getDenyPage(Model model) {
        return "client/auth/deny"; // Redirect to deny page
    }

}
