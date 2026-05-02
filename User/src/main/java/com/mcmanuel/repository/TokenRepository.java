package com.mcmanuel.repository;

import com.mcmanuel.pojo.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token,Integer> {
}
