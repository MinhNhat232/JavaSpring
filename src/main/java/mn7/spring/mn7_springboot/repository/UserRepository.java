package mn7.spring.mn7_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mn7.spring.mn7_springboot.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User mn7);

    List<User> findByEmailAndAddress(String email, String address);

    List<User> findByEmail(String email);

}
