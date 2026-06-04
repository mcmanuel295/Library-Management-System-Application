package com.mcmanuel.domain.book;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByTitleContaining(String title);
}
