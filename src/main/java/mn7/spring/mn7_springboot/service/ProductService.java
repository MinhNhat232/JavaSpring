package mn7.spring.mn7_springboot.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import mn7.spring.mn7_springboot.domain.Cart;
import mn7.spring.mn7_springboot.domain.CartDetail;
import mn7.spring.mn7_springboot.domain.OrderDetail;
import mn7.spring.mn7_springboot.domain.Orders;
import mn7.spring.mn7_springboot.domain.Products;
import mn7.spring.mn7_springboot.domain.User;
import mn7.spring.mn7_springboot.repository.CartDetailRepository;
import mn7.spring.mn7_springboot.repository.CartRepository;
import mn7.spring.mn7_springboot.repository.OrderDetailRepository;
import mn7.spring.mn7_springboot.repository.OrderRepository;
import mn7.spring.mn7_springboot.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailRepository cartDetailRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    // Constructor injection for repositories
    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailRepository cartDetailRepository, UserService userService,
            OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    public List<Products> getAllProducts() {
        return productRepository.findAll();
    }

    public Products saveProducts(Products product) {
        return productRepository.save(product);
    }

    public Optional<Products> getProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void handleAddProductToCart(String email, long productId, HttpSession session) {
        User user = userService.getUserByEmail(email);

        if (user != null) {
            // Find the user's cart
            Cart cart = cartRepository.findByUser(user);

            // If the cart does not exist, create a new one
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);

                cart = this.cartRepository.save(newCart);
            }
            Optional<Products> p = this.productRepository.findById(productId);
            if (p.isPresent()) {
                Products realProducts = p.get();

                // Check if the product is already in the cart
                CartDetail oldDetail = this.cartDetailRepository.findByCartAndProduct(cart, realProducts);

                if (oldDetail == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(realProducts);
                    cartDetail.setQuantity(1);
                    cartDetail.setPrice(realProducts.getPrice());
                    this.cartDetailRepository.save(cartDetail);

                    // Update the cart's total sum
                    int s = cart.getSum() + 1;
                    cart.setSum(s);
                    this.cartRepository.save(cart);
                    session.setAttribute("sum", s);
                } else {
                    // If the product is already in the cart, update the quantity
                    oldDetail.setQuantity(oldDetail.getQuantity() + 1);
                    this.cartDetailRepository.save(oldDetail);
                }

            }

        }
    }

    public Cart getCartByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    @Transactional
    public void handleDeleteCartProduct(long cartDetailId, HttpSession session) {
        Optional<CartDetail> optional = cartDetailRepository.findById(cartDetailId);

        if (optional.isPresent()) {
            CartDetail cartDetail = optional.get();
            Cart cart = cartDetail.getCart();

            // Gỡ liên kết hai chiều (rất quan trọng)
            cartDetail.setCart(null);
            cart.getCartDetails().remove(cartDetail); // Gỡ khỏi list cartDetails trong Cart

            // Xóa CartDetail
            cartDetailRepository.delete(cartDetail);
            cartDetailRepository.flush();

            // Cập nhật Cart
            int newSum = cart.getSum() - 1;
            cart.setSum(newSum);

            if (newSum > 0) {
                cartRepository.save(cart);
                session.setAttribute("sum", newSum);
            } else {
                cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }
        }
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        // Update the cart details with the new quantities
        for (CartDetail cartdetail : cartDetails) {
            Optional<CartDetail> optionalDetail = this.cartDetailRepository.findById(cartdetail.getId());
            if (optionalDetail.isPresent()) {
                CartDetail existingDetail = optionalDetail.get();
                existingDetail.setQuantity(cartdetail.getQuantity());
                this.cartDetailRepository.save(existingDetail);

            }

        }
    }

    public void handlePlaceOrder(User currentUser, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {
        Orders order = new Orders();
        order.setUser(currentUser);
        order.setReceiverName(receiverName);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverPhone(receiverPhone);
        order = this.orderRepository.save(order);

        Cart cart = this.cartRepository.findByUser(currentUser);
        if (cart != null) {
            List<CartDetail> cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                for (CartDetail detail : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(detail.getProduct());
                    orderDetail.setQuantity(detail.getQuantity());
                    orderDetail.setPrice(detail.getPrice());
                    this.orderDetailRepository.save(orderDetail);
                }
                for (CartDetail detail : cartDetails) {
                    this.cartDetailRepository.deleteById(detail.getId());
                }
                this.cartRepository.deleteById(cart.getId());
                session.setAttribute("sum", 0); // Reset cart sum in session
            }

        }
    }
}