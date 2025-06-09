package mn7.spring.mn7_springboot.service;

import java.util.List;

import org.springframework.stereotype.Service;

import mn7.spring.mn7_springboot.domain.Roles;
import mn7.spring.mn7_springboot.domain.User;
import mn7.spring.mn7_springboot.repository.RoleRepository;
import mn7.spring.mn7_springboot.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public String handleHello() {
        return "hello from service";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }

    public User getUserByID(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public User handleSaveUser(User user) {
        // Logic to save the user can be implemented here.
        // For example, you could call a repository method to save the user.

        return userRepository.save(user);
    }

    public void deleteUser(long id) {
        this.userRepository.deleteById(id);
    }

    public Roles getRoleByName(String name) {
        return this.roleRepository.findByName(name)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Role not found with name: " + name));
    }
}
