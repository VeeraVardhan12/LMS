package com.example.lms.controller;

import com.example.lms.Model.Book;
import com.example.lms.Model.Desc;
import com.example.lms.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    @Qualifier("h2Database")
    private BookService service;

//    @GetMapping
//    public List<Book> getBooks(){
//        return service.getBooks();
//    }

//    @GetMapping
//    public ResponseEntity<Object> getBookById(@RequestParam("bookId") int bookId){
//        Optional<Book> book = service.getBookById(bookId);
//        if(book.isPresent()){
//            return  ResponseEntity.ok(book.get());
//        }
//        return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                .body("No book found with ID: "+bookId);
//    }

    //To filter the books based on the query params we have passed
    @GetMapping
    public ResponseEntity<List<Book>> filterBooks(@RequestParam Map<String,String> queryParams){
        List<Book> books = service.filterBooks(queryParams);

        if(books.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(books);
        }
        return ResponseEntity.ok(books);
    }

//    @GetMapping("/category/{category}")
//    public List<Book> getBooksByCategory(@PathVariable String category){
//        return service.getBooksByCategory(category);
//    }

    @GetMapping("author/{authorId}")
    public List<Book> getBooksByAuthorId(@PathVariable int authorId){
        return service.getBooksByAuthorId(authorId);
    }

    @PostMapping
    public Book addBook(@RequestBody Book book){
        return service.addBook(book);
    }

//    @PutMapping("/{bookId}/description")
//    public String updateBookDescription(@PathVariable int bookId,@RequestBody Desc description) throws  Throwable{
//
//        return service.updateBookDescription(bookId,description);
//    }
    @PutMapping
    public ResponseEntity<String> updateBook(@RequestParam int bookId, @RequestBody Map<String,Object> updatedBook){
        String result = service.updateBook(bookId,updatedBook);
        return ResponseEntity.ok(result);
    }

    @DeleteMapping
    public void deleteBook(@RequestParam("bookId") int bookId){
         service.deleteBook(bookId);
    }

    @DeleteMapping("/author")
    public String deleteBookByAuthorId(@RequestParam("authorId") int authorId){
        return service.deleteBookByAuthorId(authorId);
    }
}
