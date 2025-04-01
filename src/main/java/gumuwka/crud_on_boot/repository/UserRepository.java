package gumuwka.crud_on_boot.repository;

import gumuwka.crud_on_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;
import java.util.Set;


public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);
   Set<User> findByUsername(String username);
}
