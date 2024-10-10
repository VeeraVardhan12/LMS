package com.example.lms.Service;
import com.example.lms.Model.Book;
import com.example.lms.Model.Desc;

import java.util.*;
public interface BookService {

    public List<Book> getBooks();

    public Optional<Book> getBookById(int bookId);

    public List<Book> filterBooks(Map<String,String> queryParams);
    public String updateBook(int bookId,Map<String,Object> updateBook);

    public List<Book> getBooksByCategory(String category);

    public List<Book> getBooksByAuthorId(int authorId);

    public Book addBook(Book book);

    public String updateBookDescription(int bookId, Desc description);

    public void deleteBook(int bookId);

    public String deleteBookByAuthorId(int authorId);
}
