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

    private static final int ZERO = 0;
    private static final int THREE = 3;
    private static final long ONE_L = 1L;
    private static final long TWO_L = 2L;
    private static final long THREE_L = 3L;
    private static final String STRING_ID = "id";
    private static final String STRING_RELAX = "relax_test";
    private static final String STRING_HEALTH = "health_test";
    private static final String STRING_EXTREME = "extreme_test";
    private static final String STRING_TEST_TAG = "test tag to save";

    private final Tag tag1 = Tag.builder()
            .id(ONE_L)
            .name(STRING_RELAX)
            .build();

    private final Tag tag2 = Tag.builder()
            .id(TWO_L)
            .name(STRING_HEALTH)
            .build();

    private final Tag tag3 = Tag.builder()
            .id(THREE_L)
            .name(STRING_EXTREME)
            .build();

    private final Tag tag6 = Tag.builder()
            .name(STRING_TEST_TAG)
            .build();

    private final List<Tag> tagList = Arrays.asList(tag1, tag2, tag3);

    @Test
    @DisplayName("1: findAll() from Tag-repository")
    public void testShouldFindFirstThreeTagsAndReturnThem() {
        Pageable pageable = PageRequest.of(ZERO, THREE, Sort.by(STRING_ID).ascending());
        List<Tag> actual = repository.findAll(pageable).getContent();
        assertNotNull(actual);
        assertEquals(tagList, actual);
    }

    @Test
    @DisplayName("2: findByName() from Tag-repository")
    void testShouldFindByName() {
        Tag byName = repository.findByName(STRING_RELAX)
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
        Tag tag6Saved = Tag.builder().id(id).name(STRING_TEST_TAG).build();
        assertEquals(tag6Saved, save);
    }

    @Test
    @DisplayName("4: findById() from Tag-repository")
    void testShouldFindTagById() {
        Tag save = repository.findById(ONE_L)
                .orElseThrow(RuntimeException::new);
        assertEquals(tag1, save);
    }

    @Test
    @DisplayName("5: existsByName() from Tag-repository")
    void testShouldFindOutDoesTagExistByName() {
        boolean exists = repository.existsByName(STRING_RELAX);
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