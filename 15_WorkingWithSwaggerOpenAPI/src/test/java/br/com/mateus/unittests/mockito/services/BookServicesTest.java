package br.com.mateus.unittests.mockito.services;

import br.com.mateus.data.vo.v1.BookVO;
import br.com.mateus.data.vo.v1.PersonVO;
import br.com.mateus.repositories.BookRepository;
import br.com.mateus.services.BookServices;
import br.com.mateus.unittests.mapper.mocks.MockBook;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.mateus.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class BookServicesTest {

    private MockBook mockBook;
    private BookServices service;
    private BookRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockBook = new MockBook();
        repository = mock(BookRepository.class);
        service = new BookServices(repository);
    }

    @Test
    void findAll() {
        List<Book> books = mockBook.mockEntityList();
        when(repository.findAll()).thenReturn(books);
        List<BookVO> result = assertDoesNotThrow(() -> service.findAll());
        assertFalse(result.isEmpty());
    }

    @Test
    void findById() {
        Book bookDB = new Book();
        bookDB.setId(1L);

        when(repository.findById(bookDB.getId())).thenReturn(Optional.of(bookDB));
        BookVO vo = assertDoesNotThrow(() -> service.findById(bookDB.getId()));

        assertNotNull(vo);
        assertNotNull(vo.getId());
        assertNotNull(vo.getLinks());
        System.out.println(vo.toString());
        assertTrue(vo.toString().contains("[</api/book/v1/1>;rel=\"self\"]"));
    }

    @Test
    void create() {
        Book bookDB = new Book();
        bookDB.setId(1L);
        BookVO bookVO = new BookVO();
        bookVO.setId(1L);

        when(repository.save(bookDB)).thenReturn(bookDB);
        BookVO resultado = assertDoesNotThrow(() -> service.create(bookVO));
        assertEquals(bookDB.getId(), bookVO.getId());
    }

    @Test
    void update() {
        Book bookDB = new Book();
        bookDB.setId(1L);
        BookVO bookVO = new BookVO();
        bookVO.setId(1L);

        when(repository.findById(bookDB.getId())).thenReturn(Optional.of(bookDB));
        when(repository.save(bookDB)).thenReturn(bookDB);

        BookVO resultado = assertDoesNotThrow(() -> service.update(bookVO));
        assertNotNull(resultado);
        assertEquals(bookDB.getId(), bookVO.getId());
    }

    @Test
    void delete() {
        Book bookDB = new Book();
        bookDB.setId(1L);

        when(repository.findById(bookDB.getId())).thenReturn(Optional.of(bookDB));
        doNothing().when(repository).delete(bookDB);
        assertDoesNotThrow(() -> service.delete(bookDB.getId()));
    }
}
























