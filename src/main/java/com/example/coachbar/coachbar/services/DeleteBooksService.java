package com.example.coachbar.coachbar.services;

import com.example.coachbar.coachbar.entity.Books;
import com.example.coachbar.coachbar.exceptions.DeleteException;
import com.example.coachbar.coachbar.exceptions.SearchException;
import com.example.coachbar.coachbar.models.GenericResponse;
import com.example.coachbar.coachbar.repository.BooksRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class DeleteBooksService {

    private final BooksRepository booksRepository;

    public DeleteBooksService(BooksRepository booksRepository) {
        this.booksRepository = booksRepository;
    }


    @Transactional(rollbackFor = {DeleteException.class, SearchException.class})
    public void deleteBook(Long id) throws SearchException, DeleteException {
        Books books = booksRepository.findById(id)
                .orElseThrow(() -> new SearchException("Book not found"));
        try {
            booksRepository.delete(books);
        } catch (Exception e) {
            throw new DeleteException("Failed to delete book: " + e.getMessage());
        }
    }

}
