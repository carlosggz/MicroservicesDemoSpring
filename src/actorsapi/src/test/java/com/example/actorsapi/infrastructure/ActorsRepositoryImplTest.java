package com.example.actorsapi.infrastructure;

import com.example.actorsapi.infrastructure.jpa.ActorsCrudRepository;
import com.example.actorsapi.objectmothers.ActorsObjectMother;
import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ActorsRepositoryImplTest {

    @Autowired
    ActorsCrudRepository crudRepository;

    ActorsRepositoryImpl repo;

    @BeforeEach
    public void setup(){
        crudRepository.deleteAll();
        repo = new ActorsRepositoryImpl(crudRepository);
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
                ActorsObjectMother.getRandomEntity(),
                ActorsObjectMother.getRandomEntity(),
                ActorsObjectMother.getRandomEntity()
        );

        crudRepository.saveAll(items);

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

        val entity = ActorsObjectMother.getRandomEntity();
        crudRepository.save(entity);

        val found = repo.getById(entity.getId());

        assertTrue(found.isPresent());
        assertEquals(ActorMapper.INSTANCE.toDomain(entity), found.get());
    }

    @Test
    public void getByMovieReturnMatching(){

        val movieId = "xxx";
        addActorWithMovie(movieId);
        addActorWithMovie(movieId+"x");
        addActorWithMovie(movieId);

        val actors = repo.getByMovie(movieId);

        assertEquals(2, actors.size());
        actors.forEach(x -> assertTrue(x.getMovies().stream().anyMatch(movieId::equals)));
    }

    @Test
    public void getByMovieReturnEmptyListOnNonMatching(){

        val movieId = "xxx";
        addActorWithMovie(movieId);
        addActorWithMovie(movieId+"x");
        addActorWithMovie(movieId);

        val actors = repo.getByMovie(movieId + "123");

        assertEquals(0, actors.size());
    }

    @Test
    public void saveAddChanges() {

        val originalActor = ActorsObjectMother.getRandomEntity();
        crudRepository.save(originalActor);
        originalActor.setFirstName("fn");
        originalActor.setLastName("ln");
        originalActor.setLikes(123);

        repo.save(List.of(ActorMapper.INSTANCE.toDomain(originalActor)));

        val savedActor = crudRepository.getOne(originalActor.getId());
        assertNotNull(savedActor);
        assertEquals(savedActor.getFirstName(), originalActor.getFirstName());
        assertEquals(savedActor.getLastName(), originalActor.getLastName());
        assertEquals(savedActor.getLikes(), originalActor.getLikes());

    }

    private void addActorWithMovie(String movieId){
        val entity = ActorsObjectMother.getRandomEntity(movieId);
        crudRepository.save(entity);
    }
}