package com.mcmanuel.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

interface BookRepository extends JpaRepository<Book, UUID> {


    Optional<Book> findBytitle(String title);

    @Query("Select b from Books b where b.title like %:word%")
    Book search(String word);
}
