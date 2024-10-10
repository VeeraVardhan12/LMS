package com.example.lms.Repository;

import com.example.lms.Model.Author;
import com.example.lms.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Repository
public interface BookRepo extends JpaRepository<Book,Integer> {

    List<Book> findByCategory(String category);
    List<Book> findByAuthor(Author author);

    @Transactional
    void deleteByAuthor(Author author);
}
