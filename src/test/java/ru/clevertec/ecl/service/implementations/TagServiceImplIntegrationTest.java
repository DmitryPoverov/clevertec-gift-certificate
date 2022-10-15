package ru.clevertec.ecl.service.implementations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.exception.DuplicateException;
import ru.clevertec.ecl.exception.NotFountException;
import ru.clevertec.ecl.service.interfaces.TagService;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class TagServiceImplIntegrationTest {

    @Autowired
    private TagService service;

    @Test
    @DisplayName("1: findTagById() from Tag-service negative")
    public void testShouldThrowNotFoundException() {
        assertThrows(NotFountException.class, () -> service.findTagById(100L));
    }

    @Test
    @DisplayName("1: existsByName() from Tag-service negative")
    public void testShouldThrowDuplicateException() {
        assertThrows(DuplicateException.class, () -> service.saveTag(TagDto.builder().name("relax_test").build()));
    }
}