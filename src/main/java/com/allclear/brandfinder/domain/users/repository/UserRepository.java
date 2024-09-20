package com.allclear.brandfinder.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allclear.brandfinder.domain.users.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findByEmail(String email);

}
