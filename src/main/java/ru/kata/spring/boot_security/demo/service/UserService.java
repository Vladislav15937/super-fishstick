package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    public void save(User user);

    public List<User> show();

    public Optional<User> findByUsername(String username);

    public void updateById(Long id, User user);

    public void delete(Long id);

    public Optional<User> getUserById(Long id);
}
