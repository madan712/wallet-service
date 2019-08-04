package com.leovegas.walletservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.leovegas.walletservice.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
}
