package com.mcmanuel.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);

//    @Query("SELECT b FROM Book b WHERE b.title LIKE CONCAT('%', :title, '%')")
    Optional<Book> findByTitleContaining(String title);
}
