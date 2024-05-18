package team.kubermaptes.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.kubermaptes.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
}