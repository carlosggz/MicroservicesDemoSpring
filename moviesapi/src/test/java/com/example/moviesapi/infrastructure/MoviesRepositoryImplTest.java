package com.example.moviesapi.infrastructure;

import com.example.moviesapi.application.LikeCommand;
import com.example.moviesapi.domain.Movie;
import com.example.moviesapi.domain.MovieDto;
import com.example.moviesapi.infrastructure.jpa.MovieEntity;
import com.example.moviesapi.infrastructure.jpa.MoviesCrudRepository;
import com.example.moviesapi.objectmothers.MoviesObjectMother;
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
        crudRepository.deleteAll();
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
                MoviesObjectMother.getRandomEntity(),
                MoviesObjectMother.getRandomEntity(),
                MoviesObjectMother.getRandomEntity());

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

        val emptyItem = repo.getById("");
        assertTrue(emptyItem.isEmpty());

        assertThrows(Exception.class, () -> {
            repo.getById(null);
        });
    }

    @Test
    public void getByIdExistingItemReturnsIt(){

        val entity = MoviesObjectMother.getRandomEntity();
        crudRepository.save(entity);

        val found = repo.getById(entity.getId());

        assertTrue(found.isPresent());
        assertEquals(found.get(), MovieMapper.INSTANCE.toDomain(entity));
    }

    @Test
    public void saveAddEntity() {

        val domainEntity = MoviesObjectMother.getRandomDomainEntity();

        repo.save(domainEntity);

        val entity = crudRepository.getOne(domainEntity.getId());

        assertNotNull(entity);
        assertEquals(entity, MovieMapper.INSTANCE.toEntity(domainEntity));
    }

    @Test
    public void getInListWithEmptyListReturnsAnEmptyList() {

        val result = repo.getInList(new ArrayList<>());

        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    public void getInListReturnsSearchedItems() {

        val items = List.of(
                MoviesObjectMother.getRandomEntity(),
                MoviesObjectMother.getRandomEntity(),
                MoviesObjectMother.getRandomEntity());
        val first = items.get(0).getId();
        val third = items.get(2).getId();

        items.forEach(x -> crudRepository.save(x));

        val result = repo.getInList(List.of(first, third));

        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(x -> x.getId().equals(first)));
        assertTrue(result.stream().anyMatch(x -> x.getId().equals(third)));
    }
}