package mn7.spring.mn7_springboot.controller.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import mn7.spring.mn7_springboot.domain.Cart;
import mn7.spring.mn7_springboot.domain.CartDetail;
import mn7.spring.mn7_springboot.domain.Products;
import mn7.spring.mn7_springboot.domain.User;
import mn7.spring.mn7_springboot.repository.UserRepository;
import mn7.spring.mn7_springboot.service.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class ItemController {

    private final ProductService productService;
    private final UserRepository userRepository;

    public ItemController(ProductService productService, UserRepository userRepository) {
        this.productService = productService;
        this.userRepository = userRepository;
    }

    @GetMapping("/product/{id}")
    public String productDetail(Model model, @PathVariable long id) {
        Products product = this.productService.getProductById(id).get();
        model.addAttribute("products", product);
        model.addAttribute("id", id);
        return "client/product/detail";
    }

    @PostMapping("add-product-to-cart/{id}")
    public String addProductToCart(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long productId = id;
        String email = (String) session.getAttribute("email");
        this.productService.handleAddProductToCart(email, productId, session);

        return "redirect:/";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");

        User currentUser = this.userRepository.findById(id).orElse(null);
        if (currentUser == null) {
            // xử lý nếu user không tồn tại, có thể redirect hoặc báo lỗi
            return "redirect:/login";
        }

        Cart cart = this.productService.getCartByUser(currentUser);
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        double totalPrice = 0;
        for (CartDetail detail : cartDetails) {
            totalPrice += detail.getProduct().getPrice() * detail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String deleteCartProduct(@PathVariable long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.handleDeleteCartProduct(cartDetailId, session);

        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.getCartByUser(currentUser);
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        double totalPrice = 0;
        for (CartDetail detail : cartDetails) {
            totalPrice += detail.getProduct().getPrice() * detail.getQuantity();
        }
        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);
        // model.addAttribute("cart", cart);
        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String confirmCheckout(@ModelAttribute("cart") Cart cart) {
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";

    }

    @PostMapping("/place-order")
    public String placeOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);
        this.productService.handlePlaceOrder(currentUser, session, receiverName, receiverAddress, receiverPhone);

        return "redirect:/thank-you";
    }

    @GetMapping("/thank-you")
    public String getThankYouPage() {
        return "client/cart/thanks";
    }

}
