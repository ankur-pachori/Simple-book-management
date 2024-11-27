package com.example.coachbar.coachbar.repository;

import com.example.coachbar.coachbar.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BooksRepository extends JpaRepository<Books,Long> {
}
