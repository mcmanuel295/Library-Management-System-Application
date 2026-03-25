package com.mcmanuel.LibraryManagementSystem.pojo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookRequest {
    private String title;

    private boolean available;

    private LocalDateTime createdDate;
}
