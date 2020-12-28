package com.example.moviesapi.infrastructure;

import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.infrastructure.jpa.MovieEntity;
import com.example.moviesapi.infrastructure.jpa.MoviesCrudRepository;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class MoviesRepositoryImplTest {

    @Autowired
    private MoviesCrudRepository crudRepository;

    MoviesRepositoryImpl repo;

    @BeforeEach
    public void setup(){
        repo = new MoviesRepositoryImpl(crudRepository);
    }

    @AfterEach
    public  void cleanUp(){
        repo = null;
    }


    @Test
    public void getAllReturnsNotNull() {

        val result = repo.getAll();

        assertNotNull(result);
    }

    @Test
    public void getAllReturnsAllItems(){

        val items = List.of(
                new MovieEntity("1", "First", 0),
                new MovieEntity("2", "Second", 0),
                new MovieEntity("3", "Third", 0)
        );

        items.forEach(x -> crudRepository.save(x));

        val result = repo.getAll();

        assertEquals(items.size(), result.size());

        result.forEach(x -> {
            val found = items
                    .stream()
                    .filter(i -> i.getId().equals(x.getId()))
                    .findFirst();

            assertTrue(found.isPresent());
            assertEquals(x.getId(), found.get().getId());
        });

    }

    @Test
    public void getByIdNonExistItemReturnsEmpty() {

        val invalid  = repo.getById("123");
        assertTrue(invalid.isEmpty());

        val nullItem = repo.getById(null);
        assertTrue(nullItem.isEmpty());

        val emptyItem = repo.getById("");
        assertTrue(emptyItem.isEmpty());
    }

    @Test
    public void getByIdExistingItemReturnsIt(){

        val entity = new MovieEntity("1", "First", 0);
        crudRepository.save(entity);

        val found = repo.getById(entity.getId());

        assertTrue(found.isPresent());
        assertEquals(found.get().getId(), entity.getId());
        assertEquals(found.get().getTitle(), entity.getTitle());
        assertEquals(found.get().getLikes(), entity.getLikes());
    }

    @Test
    public void likeNonExistingItemThrowsException() {

        assertThrows(Exception.class, () -> {
            repo.like(null);
        });

        assertThrows(Exception.class, () -> {
            repo.like("");
        });

        assertThrows(Exception.class, () -> {
            repo.like("123");
        });
    }

    @Test
    public void likeExistingItemIncrementByOne() throws Exception {

        val entity = new MovieEntity("1", "First", 5);
        crudRepository.save(entity);

        repo.like(entity.getId());

        val changedEntity = crudRepository.getOne(entity.getId());

        assertEquals(entity.getLikes()+1, changedEntity.getLikes());
    }
}