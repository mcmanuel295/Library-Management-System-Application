package com.mcmanuel.domain.token;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface TokenRepository extends JpaRepository<Token, UUID> {}
