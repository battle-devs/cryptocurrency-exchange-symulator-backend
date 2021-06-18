package main.repository;

import java.util.List;

import main.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    User findByNickName(String username);
    List<User> findAll();
}