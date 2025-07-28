package mn7.spring.mn7_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mn7.spring.mn7_springboot.domain.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    // Additional query methods can be defined here if needed
}
