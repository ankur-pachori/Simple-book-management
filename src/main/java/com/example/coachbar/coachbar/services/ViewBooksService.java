package com.example.coachbar.coachbar.services;


import com.example.coachbar.coachbar.entity.Books;
import com.example.coachbar.coachbar.exceptions.SearchException;
import com.example.coachbar.coachbar.models.BooksViewResponse;
import com.example.coachbar.coachbar.models.GenericResponse;
import com.example.coachbar.coachbar.repository.BooksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class ViewBooksService {


    private final BooksRepository booksRepository;

    public ViewBooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    @Transactional(readOnly = true)
    public BooksViewResponse viewBook(Long id) throws SearchException {
        Books books = booksRepository.findById(id)
                .orElseThrow(() -> new SearchException("Book not found"));
        return new BooksViewResponse(books.getAuthor(), books.getTitle(), books.getPublicationYear());
    }


}
