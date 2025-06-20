package mn7.spring.mn7_springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mn7.spring.mn7_springboot.domain.Products;

@Repository
public interface ProductRepository extends JpaRepository<Products, Long> {

}
