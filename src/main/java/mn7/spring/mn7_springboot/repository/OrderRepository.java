package mn7.spring.mn7_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mn7.spring.mn7_springboot.domain.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
