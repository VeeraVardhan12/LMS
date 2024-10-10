package com.example.lms.Service;

import com.example.lms.Model.Author;
import com.example.lms.Repository.AuthorRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {

    @Autowired
    private AuthorRepo authorRepo;

    public Author addAuthor(Author author){
        return authorRepo.save(author);
    }

    public List<Author> getAllAuthors() {
        return authorRepo.findAll();
    }

    public void DeleteAuthor(int id){
        authorRepo.deleteById(id);
    }
}
