package br.com.mateus.services;

import br.com.mateus.controllers.BookController;
import br.com.mateus.data.vo.v1.BookVO;
import br.com.mateus.domain.Book;
import br.com.mateus.exception.ResourceNotFoundException;
import br.com.mateus.mapper.Mapper;
import br.com.mateus.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    private final BookRepository repository;
    @Autowired
    public BookServices(BookRepository repository) {
        this.repository = repository;
    }

    public List<BookVO> findAll() {
        logger.info("Finding all book!");
        var Books = Mapper.parseListObjects(repository.findAll(), BookVO.class);
        for (BookVO p : Books) {
            p.add(linkTo(methodOn(BookController.class).findById(p.getId())).withSelfRel());
        }
        return Books;
    }

    public BookVO findById(Long id){
        logger.info("Findind one book!");
        Book entity = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found this ID!"));
        BookVO vo = Mapper.parseObject(entity, BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
        return vo;
    }

    public BookVO create(BookVO book){
        logger.info("Creating one book!");
        Book entity = Mapper.parseObject(book, Book.class);
        BookVO vo = Mapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel());
        return vo;
    }

    public BookVO update(BookVO book){
        logger.info("Updating one book!");

        Book entity = repository.findById(book.getId())
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        entity.setAuthor(book.getAuthor());
        entity.setLaunchDate(book.getLaunchDate());
        entity.setPrice(book.getPrice());
        entity.setTitle(book.getTitle());

        BookVO vo = Mapper.parseObject(repository.save(entity), BookVO.class);
        vo.add(linkTo(methodOn(BookController.class).findById(vo.getId())).withSelfRel());
        return vo;
    }

    public void delete(Long id) {
        logger.info("Deleting one book!");

        Book entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));

        repository.delete(entity);
    }
}
