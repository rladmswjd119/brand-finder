package com.allclear.brandfinder.domain.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.allclear.brandfinder.domain.users.entity.Rank;

public interface RankRepository extends JpaRepository<Rank, Integer> {

    Rank findByName(String name);

}
