package com.mcmanuel.domain;

import java.util.ArrayList;
import java.util.UUID;

public interface BorrowService {

    Book borrowBook(UUID userIs, UUID bookId);

    ArrayList<Book> getAllBook();
}
