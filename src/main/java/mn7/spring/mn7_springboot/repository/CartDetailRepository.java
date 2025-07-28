package mn7.spring.mn7_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mn7.spring.mn7_springboot.domain.Cart;
import mn7.spring.mn7_springboot.domain.CartDetail;
import mn7.spring.mn7_springboot.domain.Products;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Long> {

    boolean existsByCartAndProduct(Cart cart, Products products);

    CartDetail findByCartAndProduct(Cart cart, Products products);

    List<CartDetail> findByCart(Cart cart);

}
