package com.mcmanuel.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

interface BookRepository extends JpaRepository<UUID, Books> {

}