package gumuwka.crud_on_boot.repository;

import gumuwka.crud_on_boot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {
}
