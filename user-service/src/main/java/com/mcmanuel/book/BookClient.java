package com.mcmanuel.book;

import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "catalog-service", url = "${CATALOG_SERVICE_URL:http://localhost:8081/api/v1/books}")
public interface BookClient {

    @GetMapping("/")
    Page<BookDto> getAllBook(
            @Valid @RequestParam(defaultValue = "0", required = false) int pageNo,
            @Valid @RequestParam(defaultValue = "10", required = false) int size,
            @Valid @RequestParam(required = false, defaultValue = "title") String sort);

    @GetMapping("/{bookId}")
    BookDto getBook(@Valid @PathVariable UUID bookId);

    @PostMapping("/borrow")
    String borrowBook(@RequestParam UUID userId, @RequestParam @Valid UUID bookId);

    @PostMapping("/return")
    String returnBook(@RequestParam UUID userId, @RequestParam UUID bookId);
}
