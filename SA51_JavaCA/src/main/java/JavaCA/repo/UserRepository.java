package JavaCA.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import JavaCA.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
