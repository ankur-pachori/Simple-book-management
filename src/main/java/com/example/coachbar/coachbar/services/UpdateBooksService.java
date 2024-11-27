package com.example.coachbar.coachbar.services;


import com.example.coachbar.coachbar.entity.Books;
import com.example.coachbar.coachbar.exceptions.CreateException;
import com.example.coachbar.coachbar.exceptions.SearchException;
import com.example.coachbar.coachbar.models.BookUpdateRequest;
import com.example.coachbar.coachbar.models.GenericResponse;
import com.example.coachbar.coachbar.repository.BooksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Slf4j
public class UpdateBooksService {


    private final BooksRepository booksRepository;

    public UpdateBooksService(BooksRepository booksRepository) {

        this.booksRepository = booksRepository;
    }


    @Transactional(rollbackFor = CreateException.class)
    public GenericResponse<String> updateBook(BookUpdateRequest bookUpdateRequest) throws SearchException, CreateException {


        Books books = booksRepository.findById(bookUpdateRequest.getId()).orElseThrow(()-> new SearchException("Book not found"));
        try {

            books.setAuthor(bookUpdateRequest.getAuthor());
            books.setTitle(bookUpdateRequest.getTitle());
            books.setPublicationYear(bookUpdateRequest.getPublicationYear());

            LocalDateTime now = LocalDateTime.now();
            books.setUpdatedOn(now);
            books = booksRepository.save(books);

            return new GenericResponse<>(200L, "Success", String.valueOf(books.getId()));

        } catch (Exception e) {
           throw new CreateException(e.getMessage());
        }
    }
}
