package com.example.lms.Service;

import com.example.lms.Model.Author;
import com.example.lms.Model.Book;
import com.example.lms.Model.Desc;
import com.example.lms.Repository.AuthorRepo;
import com.example.lms.Repository.BookRepo;
import com.example.lms.exception.AuthorNotFoundException;
import com.example.lms.exception.BookNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class H2Database implements BookService {

    @Autowired
    private BookRepo bookrepo;
    @Autowired
    private AuthorRepo authorRepo;
    public List<Book> getBooks(){
        try{
            return bookrepo.findAll();
        }catch (Exception e){
            throw e;
        }
//        return bookrepo.findAll();
    }

    public Optional<Book> getBookById(int bookId){
//        return books.stream()
//                .filter(p -> p.getBookdId() == bookId)
//                .findFirst().get();
        try{
            Optional<Book> bookOptional = bookrepo.findById(bookId);
            if(bookOptional.isPresent()) {
                return bookOptional;
            }else{
                throw new BookNotFoundException("Book not found with ID: "+bookId);
            }
        }catch(BookNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Unexcepted error while updating book descrption");
        }
//        return bookrepo.findById(bookId);
    }

    public List<Book> filterBooks(Map<String,String> queryParams){
        List<Book> filteredBooks = bookrepo.findAll();

        for (Map.Entry<String, String> entry : queryParams.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            switch (key.toLowerCase()) {
                case "bookid":
                    int bookId = Integer.parseInt(value);
                    filteredBooks = filteredBooks.stream()
                            .filter(book -> book.getId() == bookId)
                            .collect(Collectors.toList());
                    break;
                case "category":
                    filteredBooks = filteredBooks.stream()
                            .filter(book -> book.getCategory().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "description":
                    filteredBooks = filteredBooks.stream()
                            .filter(book -> book.getDescription().toLowerCase().contains(value.toLowerCase()))
                            .collect(Collectors.toList());
                    break;
                case "authorname":
//                    int authorId = Integer.parseInt(value);
                    filteredBooks = filteredBooks.stream()
                            .filter(book -> book.getAuthor().getName().equalsIgnoreCase(value))
                            .collect(Collectors.toList());
                    break;
                case "authorid":
                    int authorId = Integer.parseInt(value);
                    filteredBooks = filteredBooks.stream()
                            .filter(book -> book.getAuthor().getId() == authorId)
                            .collect(Collectors.toList());
                default:
                    break;
            }
        }
        return filteredBooks;
    }

    public String updateBook(int bookId,Map<String,Object> updatedBook){

        try{
            Book book = bookrepo.findById(bookId).orElseThrow(()->new BookNotFoundException("No book found with ID: "+bookId));

            for(Map.Entry<String,Object> entry: updatedBook.entrySet()){
                String key = entry.getKey().toLowerCase(); // Convert key to lowercase for consistent matching
                Object value = entry.getValue();

                switch (key) {
                    case "title":
                        book.setTitle((String) value);
                        break;
                    case "description":
                        book.setDescription((String) value);
                        break;
                    case "category":
                        book.setCategory((String) value);
                        break;
                    case "author":
                        int authorId = (int) value;
                        Author author = authorRepo.findById(authorId)
                                .orElseThrow(() -> new RuntimeException("No author found with ID: " + authorId));
                        book.setAuthor(author);
                        break;
                    //new lines added
                    case "quantity":
                        book.setQuantity((int) value);
                        break;

                    default:
                        throw new RuntimeException("Unknown attribute: " + key);
                }
            }
            bookrepo.save(book);
            return "Book with ID: " + bookId + " updated successfully";
        }catch(BookNotFoundException e){
            return  e.getMessage();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public List<Book> getBooksByCategory(String category) {
        return bookrepo.findByCategory(category);
    }

    public List<Book> getBooksByAuthorId(int authorId){
        try{
            Optional<Author> authorOptional = authorRepo.findById(authorId);
            if(authorOptional.isPresent()){
                Author author = authorOptional.get();
                return bookrepo.findByAuthor(author);
            }else{
                throw new AuthorNotFoundException("Author id: "+authorId+" is not found");
            }
        }catch(AuthorNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Unexpected error while fetching books by author id");
        }
//        Author author = authorRepo.findById(authorId).orElse(null);
//        return bookrepo.findByAuthor(author);
    }

    public Book addBook(Book book){
//        books.add(book);
//        System.out.println(book);
        try{
            Optional<Author> authorOptional = authorRepo.findById(book.getAuthor().getId());
            if(authorOptional.isPresent()){
                return bookrepo.save(book);
            }else{
                throw new AuthorNotFoundException("Cannot add the book. Author ID "+book.getAuthor().getId()+" is not present");
            }
        }catch(AuthorNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Unexpected error while adding book");
        }
//        return bookrepo.save(book);
    }

    public String updateBookDescription(int bookId, Desc description){
        try{
            Optional<Book> bookOptional = bookrepo.findById(bookId);
            if(bookOptional.isPresent()) {
                Book book = bookOptional.get();
                book.setDescription(description.getDescription());
                bookrepo.save(book);
                return "Book with ID: " + bookId + " updated successfully.";
            }else{
                throw new BookNotFoundException("Book not found with ID: "+bookId);
            }
        }catch(BookNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Unexpected error while updating book description");
        }
    }

    public void deleteBook(int bookId){
        bookrepo.deleteById(bookId);
    }
    public String deleteBookByAuthorId(int authorId){
        try{
            Optional<Author> authorOptional = authorRepo.findById(authorId);
            if(authorOptional.isPresent()){
                Author author = authorOptional.get();
                bookrepo.deleteByAuthor(author);
                return "Books with Author "+authorId+" are deleted successfully";
            }else{
                throw new AuthorNotFoundException("Author id: "+authorId+" is not found");
            }
        }catch(AuthorNotFoundException e){
            throw e;
        }catch (Exception e){
            throw new RuntimeException("Unexpected error while fetching books by author id");
        }
//        Author author = authorRepo.findById(authorId).orElse(null);
//        bookrepo.deleteByAuthor(author);
    }
}
