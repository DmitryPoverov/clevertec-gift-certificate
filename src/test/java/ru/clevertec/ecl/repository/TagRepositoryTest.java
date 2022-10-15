package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.entities.Tag;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class TagRepositoryTest {

    @Autowired
    private TagRepository repository;

    private final String name = "relax_test";

    private final Tag tag1 = Tag.builder().id(1L).name("relax_test").build();
    private final Tag tag2 = Tag.builder().id(2L).name("health_test").build();
    private final Tag tag3 = Tag.builder().id(3L).name("extreme_test").build();

    private final Tag tag6 = Tag.builder().name("test tag to save").build();

    private final List<Tag> tagList = Arrays.asList(tag1, tag2, tag3);

    @Test
    @DisplayName("1: findAll() from Tag-repository")
    public void testShouldFindFirstThreeTagsAndReturnThem() {
        Pageable pageable = PageRequest.of(0, 3, Sort.by("id").ascending());
        List<Tag> actual = repository.findAll(pageable).getContent();
        assertNotNull(actual);
        assertEquals(tagList, actual);
    }

    @Test
    @DisplayName("2: findByName() from Tag-repository")
    void testShouldFindByName() {
        Tag byName = repository.findByName(name)
                .orElseThrow(RuntimeException::new);
        assertEquals(tag1, byName);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("3: save() from Tag-repository")
    void testShouldSaveTagAndReturnItWithId() {
        Tag save = repository.save(tag6);
        long id = save.getId();

        Tag tag6Saved = Tag.builder().id(id).name("test tag to save").build();

        assertEquals(tag6Saved, save);
    }

    @Test
    @DisplayName("4: findById() from Tag-repository")
    void testShouldFindTagById() {
        long id1 = 1L;
        Tag save = repository.findById(id1)
                .orElseThrow(RuntimeException::new);
        assertEquals(tag1, save);
    }

    @Test
    @DisplayName("5: existsByName() from Tag-repository")
    void testShouldFindOutDoesTagExistByName() {
        boolean exists = repository.existsByName(name);
        boolean existTrue = true;
        assertEquals(existTrue, exists);
    }

    @Test
    @Transactional
    @Rollback
    @DisplayName("6: delete() from Tag-repository")
    void testShouldDeleteTagById() {
        Tag save = repository.save(tag6);
        long id = save.getId();

        Tag tagBeforeDelete = repository.findById(id)
                .orElseThrow(RuntimeException::new);
        assertNotNull(tagBeforeDelete);

        repository.delete(save);

        boolean tagAfterDelete = repository.existsById(id);

        assertFalse(tagAfterDelete);
    }
}