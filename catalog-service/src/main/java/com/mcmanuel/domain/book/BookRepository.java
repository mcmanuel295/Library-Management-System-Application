package com.mcmanuel.domain.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface BookRepository extends JpaRepository<Book, UUID> {
    Optional<Book> findByTitle(String title);

    @Query("Select b from Book b where b.title like %:title%")
    Optional<Book> search(String word);
}
