package com.allclear.brandfinder.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allclear.brandfinder.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

}
