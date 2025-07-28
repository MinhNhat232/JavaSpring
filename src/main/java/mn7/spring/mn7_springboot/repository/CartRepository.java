package mn7.spring.mn7_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mn7.spring.mn7_springboot.domain.Cart;
import mn7.spring.mn7_springboot.domain.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUser(User user);

}
