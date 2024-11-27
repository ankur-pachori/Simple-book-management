package com.example.coachbar.coachbar.services;

import com.example.coachbar.coachbar.entity.Books;
import com.example.coachbar.coachbar.exceptions.CreateException;
import com.example.coachbar.coachbar.models.BookCreateRequest;
import com.example.coachbar.coachbar.models.GenericResponse;
import com.example.coachbar.coachbar.repository.BooksRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class CreateBooksService {

    private final BooksRepository booksRepository;


    public CreateBooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }

    @Transactional(rollbackFor = CreateException.class)
    public GenericResponse<String> createBook(BookCreateRequest bookCreateRequest) throws CreateException {

        if (bookCreateRequest.getAuthor() == null || bookCreateRequest.getAuthor().isEmpty()) {
            throw new CreateException("Book Author cannot be empty");
        }

        if (bookCreateRequest.getTitle() == null || bookCreateRequest.getTitle().isEmpty()) {
            throw new CreateException("Book Title cannot be empty");
        }

        if (bookCreateRequest.getPublicationYear() == null) {
            throw new CreateException("Book publication year cannot be empty");
        }

        try {
            Books book = new Books();
            book.setAuthor(bookCreateRequest.getAuthor());
            book.setTitle(bookCreateRequest.getTitle());
            book.setPublicationYear(bookCreateRequest.getPublicationYear());
            book.setCreatedOn(LocalDateTime.now());
            book.setUpdatedOn(LocalDateTime.now());
            book = booksRepository.save(book);

            return new GenericResponse<>(200L, "Success", String.valueOf(book.getId()));
        } catch (Exception e) {
            throw new CreateException("Failed to create book: " + e.getMessage());
        }
    }
}
