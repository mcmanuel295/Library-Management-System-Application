package com.mcmanuel.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByTitleContaining(String title);
}
