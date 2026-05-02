package com.mcmanuel.domain.repository;

import com.mcmanuel.domain.book.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Books, UUID> {


    Optional<Books> findBytitle(String title);

    @Query("Select b from Books b where b.title like %:word%")
    Books search(String word);
}
