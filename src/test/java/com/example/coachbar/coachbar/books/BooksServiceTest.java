package com.example.coachbar.coachbar.books;

import com.example.coachbar.coachbar.entity.Books;
import com.example.coachbar.coachbar.exceptions.CreateException;
import com.example.coachbar.coachbar.exceptions.DeleteException;
import com.example.coachbar.coachbar.exceptions.SearchException;
import com.example.coachbar.coachbar.models.BookCreateRequest;
import com.example.coachbar.coachbar.models.BookUpdateRequest;
import com.example.coachbar.coachbar.models.BooksViewResponse;
import com.example.coachbar.coachbar.models.GenericResponse;
import com.example.coachbar.coachbar.repository.BooksRepository;
import com.example.coachbar.coachbar.services.CreateBooksService;
import com.example.coachbar.coachbar.services.DeleteBooksService;
import com.example.coachbar.coachbar.services.UpdateBooksService;
import com.example.coachbar.coachbar.services.ViewBooksService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BooksServiceTest {

    @Mock
    private BooksRepository booksRepository;

    @InjectMocks
    private CreateBooksService createBooksService;

    @InjectMocks
    private UpdateBooksService updateBooksService;

    @InjectMocks
    private ViewBooksService viewBooksService;

    @InjectMocks
    private DeleteBooksService deleteBooksService;


    @Test
    void testCreateBook_Success() throws CreateException {

        BookCreateRequest request = new BookCreateRequest();
        request.setAuthor("Author");
        request.setTitle("Title");
        request.setPublicationYear(2023);

        Books savedBook = new Books();
        savedBook.setId(1L);

        Mockito.when(booksRepository.save(Mockito.any(Books.class))).thenReturn(savedBook);


        GenericResponse<String> response = createBooksService.createBook(request);

        Assertions.assertEquals(200L, response.getResponseCode());
        Assertions.assertEquals("Success", response.getResponseMessage());
        Assertions.assertEquals("1", response.getResponse());
    }

    @Test
    void testCreateBook_ValidationFailure() {
        BookCreateRequest request = new BookCreateRequest();
        Assertions.assertThrows(CreateException.class, () -> createBooksService.createBook(request));
    }

    @Test
    void testUpdateBook_Success() throws SearchException, CreateException {

        BookUpdateRequest request = new BookUpdateRequest();
        request.setId(1L);
        request.setAuthor("Updated Author");
        request.setTitle("Updated Title");
        request.setPublicationYear(2024);

        Books existingBook = new Books();
        Mockito.when(booksRepository.findById(1L)).thenReturn(Optional.of(existingBook));

        Books updatedBook = new Books();
        updatedBook.setId(1L);

        Mockito.when(booksRepository.save(Mockito.any(Books.class))).thenReturn(updatedBook);


        GenericResponse<String> response = updateBooksService.updateBook(request);


        Assertions.assertEquals(200L, response.getResponseCode());
        Assertions.assertEquals("Success", response.getResponseMessage());
        Assertions.assertEquals("1", response.getResponse());
    }

    @Test
    void testUpdateBook_NotFound() {
        BookUpdateRequest request = new BookUpdateRequest();
        request.setId(1L);
        Mockito.when(booksRepository.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(SearchException.class, () -> updateBooksService.updateBook(request));
    }


    @Test
    void testViewBook_Success() throws SearchException {
        // Arrange
        Long bookId = 1L;
        Books book = new Books();
        book.setAuthor("Author");
        book.setTitle("Title");
        book.setPublicationYear(2023);

        Mockito.when(booksRepository.findById(bookId)).thenReturn(Optional.of(book));

        BooksViewResponse response = viewBooksService.viewBook(bookId);

        Assertions.assertEquals("Author", response.getAuthor());
        Assertions.assertEquals("Title", response.getTitle());
        Assertions.assertEquals(2023, response.getPublicationYear());
    }


    @Test
    void testViewBook_NotFound() {
        Long bookId = 1L;
        Mockito.when(booksRepository.findById(bookId)).thenReturn(Optional.empty());
        Assertions.assertThrows(SearchException.class, () -> viewBooksService.viewBook(bookId));
    }


    @Test
    void testDeleteBook_Success() throws SearchException, DeleteException {
        Long bookId = 1L;
        Books book = new Books();

        Mockito.when(booksRepository.findById(bookId)).thenReturn(Optional.of(book));
        Mockito.doNothing().when(booksRepository).delete(book);


        deleteBooksService.deleteBook(bookId);

        Mockito.verify(booksRepository, Mockito.times(1)).delete(book);
    }


    @Test
    void testDeleteBook_NotFound() {
        Long bookId = 1L;
        Mockito.when(booksRepository.findById(bookId)).thenReturn(Optional.empty());
        Assertions.assertThrows(SearchException.class, () -> deleteBooksService.deleteBook(bookId));
    }


}
