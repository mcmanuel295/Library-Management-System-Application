package com.mcmanuel.domain.book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);

    Optional<Book> findByTitleContaining(String title);

    @Query("SELECT b.code FROM Book b")
    List<String> getAllCode();

    Optional<Book> findByCode(String code);
}
