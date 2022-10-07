package ru.clevertec.ecl.service.implementations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

//    private static final TagMapper mapper = Mappers.getMapper(TagMapper.class);
    private TagMapper mapper;
    private TagServiceImpl service;
    private TagRepository repository;

    private static final long ID_ONE = 1;

    private static final Tag TAG = Tag.builder().name("test1").build();
    private static final Tag TAG_ID_1 = Tag.builder().id(1L).name("test1").build();
    private static final Tag TAG_ID_1_UPDATED = Tag.builder().id(1L).name("test1_UPDATED").build();

    private static final TagDto DTO = TagDto.builder().name("test1").build();
    private static final TagDto DTO_ID_1 = TagDto.builder().id(1L).name("test1").build();
    private static final TagDto DTO_ID_1_UPDATED = TagDto.builder().id(1L).name("test1_UPDATED").build();

    private static final List<Tag> TAG_LIST = Collections.singletonList(TAG_ID_1);
    private static final List<TagDto> DTO_LIST = Collections.singletonList(DTO_ID_1);

    private static final Page<Tag> TAG_PAGE = new PageImpl<>(TAG_LIST);
    private static final Pageable pageable = Pageable.ofSize(2);

    @BeforeEach
    void beforeEach() {
        mapper = Mockito.mock(TagMapper.class);
        repository = Mockito.mock(TagRepository.class);
        service = new TagServiceImpl(repository, mapper);
    }

    @Test
    @DisplayName("1 Unit test: findAllTags() from Tag-service")
    void testShouldReturnListWithOneTag() {
        given(repository.findAll(pageable))
                .willReturn(TAG_PAGE);
        given(mapper.tagToDto(TAG_ID_1))
                .willReturn(DTO_ID_1);

        List<TagDto> allTags = service.findAllTags(pageable);
        Assertions.assertEquals(DTO_LIST, allTags);
    }

    @Test
    @DisplayName("2 Unit test: getTagById() from Tag-service")
    void testShouldFindTagDtoById() {
        given(repository.findById(1L))
                .willReturn(Optional.of(TAG_ID_1));
        given(mapper.tagToDto(TAG_ID_1))
                .willReturn(DTO_ID_1);

        TagDto tagById = service.findTagById(ID_ONE);
        Assertions.assertEquals(DTO_ID_1, tagById);
    }

    @Test
    @DisplayName("3 Unit test: saveTag() from Tag-service")
    void testShouldSaveTag() {
        given(repository.save(TAG))
                .willReturn(TAG_ID_1);
        given(repository.existsByName(TAG.getName()))
                .willReturn(false);
        given(mapper.dtoToTag(DTO))
                .willReturn(TAG);
        given(mapper.tagToDto(TAG_ID_1))
                .willReturn(DTO_ID_1);

        TagDto saveTag = service.saveTag(DTO);
        Assertions.assertEquals(DTO_ID_1, saveTag);
    }

    @Test
    @DisplayName("4 Unit test: updateTag() from Tag-service")
    void testShouldUpdateTag() {
        given(repository.findById(ID_ONE))
                .willReturn(Optional.of(TAG_ID_1));
        given(repository.existsByName(any()))
                .willReturn(false);
        given(repository.save(any()))
                .willReturn(TAG_ID_1_UPDATED);

        doNothing().when(mapper)
                .updateFromDto(TAG_ID_1, DTO_ID_1_UPDATED);
        given(mapper.tagToDto(any()))
                .willReturn(DTO_ID_1_UPDATED);

        TagDto updateTag = service.updateTag(DTO_ID_1_UPDATED);
        Assertions.assertEquals(DTO_ID_1_UPDATED, updateTag);
    }

    @Test
    @DisplayName("5 Unit test: deleteTag() from Tag-service")
    void testShouldDeleteTagById() {
        given(repository.findById(ID_ONE))
                .willReturn(Optional.of(TAG_ID_1));
        service.deleteTag(ID_ONE);
        verify(repository, times(1)).delete(TAG_ID_1);
    }

    @Test
    @DisplayName("6 Unit test: findByNameOrSave() from Tag-service")
    void testShouldFindByName() {
        given(repository.findByName(DTO_ID_1.getName()))
                .willReturn(Optional.of(TAG_ID_1));
        Tag byNameOrSave = service.findByNameOrSave(DTO_ID_1);
        Assertions.assertEquals(TAG_ID_1, byNameOrSave);
    }
}