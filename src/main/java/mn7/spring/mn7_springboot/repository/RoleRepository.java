package mn7.spring.mn7_springboot.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import mn7.spring.mn7_springboot.domain.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Roles save(Roles role);

    List<Roles> findByName(String name);

}
