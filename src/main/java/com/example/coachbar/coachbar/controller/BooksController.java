package com.example.coachbar.coachbar.controller;


import com.example.coachbar.coachbar.exceptions.CreateException;
import com.example.coachbar.coachbar.exceptions.DeleteException;
import com.example.coachbar.coachbar.exceptions.SearchException;
import com.example.coachbar.coachbar.models.BookCreateRequest;
import com.example.coachbar.coachbar.models.BookUpdateRequest;
import com.example.coachbar.coachbar.models.BooksViewResponse;
import com.example.coachbar.coachbar.models.GenericResponse;
import com.example.coachbar.coachbar.services.CreateBooksService;
import com.example.coachbar.coachbar.services.DeleteBooksService;
import com.example.coachbar.coachbar.services.UpdateBooksService;
import com.example.coachbar.coachbar.services.ViewBooksService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
public class BooksController {

    private final ViewBooksService viewBooksService;
    private final CreateBooksService createBooksService;
    private final UpdateBooksService updateBooksService;
    private final DeleteBooksService deleteBooksService;

    public BooksController(ViewBooksService viewBooksService, CreateBooksService createBooksService,
                           UpdateBooksService updateBooksService, DeleteBooksService deleteBooksService) {
        this.viewBooksService = viewBooksService;
        this.createBooksService = createBooksService;
        this.updateBooksService = updateBooksService;
        this.deleteBooksService = deleteBooksService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/create", produces = "application/json")
    public ResponseEntity<GenericResponse<String>> createUser(@RequestBody @Valid BookCreateRequest bookCreateRequest) {
        try {
            GenericResponse<String> response = createBooksService.createBook(bookCreateRequest);
            if (response.getResponseCode() == 200L) {
                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(new GenericResponse<>(201L, "Book created successfully", response.getResponse()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new GenericResponse<>(400L, response.getResponseMessage()));
            }
        } catch (CreateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(400L, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(500L, "An unexpected error occurred: " + e.getMessage()));
        }
    }


    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(path = "/update", produces = "application/json")
    public ResponseEntity<GenericResponse<Void>> updateUser(@RequestBody @Valid BookUpdateRequest bookUpdateRequest) {
        try {
            updateBooksService.updateBook(bookUpdateRequest);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GenericResponse<>(200L, "Book updated successfully"));
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse<>(404L, e.getMessage()));
        } catch (CreateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(400L, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(500L, "An unexpected error occurred: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse<BooksViewResponse>> viewBooks(@PathVariable("id") Long id) {
        try {
            BooksViewResponse booksViewResponse = viewBooksService.viewBook(id);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new GenericResponse<>(200L, "Book retrieved successfully", booksViewResponse));
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse<>(404L, e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(500L, "An unexpected error occurred: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(path = "/{id}", produces = "application/json")
    public ResponseEntity<GenericResponse<Boolean>> deleteBook(@PathVariable("id") Long id) {
        try {
            deleteBooksService.deleteBook(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT)
                    .body(new GenericResponse<>(204L, "Book deleted successfully", true));
        } catch (SearchException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new GenericResponse<>(404L, "Book not found", false));
        } catch (DeleteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GenericResponse<>(400L, e.getMessage(), false));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new GenericResponse<>(500L, "An unexpected error occurred: " + e.getMessage(), false));
        }
    }
}
