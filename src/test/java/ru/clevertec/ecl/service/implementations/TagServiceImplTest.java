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

    private TagMapper tagMapper;
    private TagServiceImpl tagService;
    private TagRepository tagRepository;

    private static final int SIZE_2 = 2;
    private static final long ID_1L = 1L;
    private static final String TEST_1_NAME = "test1";
    private static final String TEST_1_NAME_UPD = "test1_UPDATED";

    private static final Tag TAG_WITHOUT_ID = Tag.builder()
            .name(TEST_1_NAME)
            .build();
    private static final Tag TAG_ID_1 = Tag.builder()
            .id(ID_1L)
            .name(TEST_1_NAME)
            .build();
    private static final Tag TAG_ID_1_UPDATED = Tag.builder()
            .id(ID_1L)
            .name(TEST_1_NAME_UPD)
            .build();

    private static final TagDto DTO_WITHOUT_ID = TagDto.builder()
            .name(TEST_1_NAME)
            .build();
    private static final TagDto DTO_ID_1 = TagDto.builder()
            .id(ID_1L)
            .name(TEST_1_NAME)
            .build();
    private static final TagDto DTO_ID_1_UPDATED = TagDto.builder()
            .id(ID_1L)
            .name(TEST_1_NAME_UPD)
            .build();

    private static final List<Tag> TAG_LIST = Collections.singletonList(TAG_ID_1);
    private static final List<TagDto> DTO_LIST = Collections.singletonList(DTO_ID_1);

    private static final Page<Tag> PAGE_OF_TAGS = new PageImpl<>(TAG_LIST);
    private static final Pageable PAGEABLE_SIZE_2 = Pageable.ofSize(SIZE_2);

    @BeforeEach
    void beforeEach() {
        tagMapper = Mockito.mock(TagMapper.class);
        tagRepository = Mockito.mock(TagRepository.class);
        tagService = new TagServiceImpl(tagRepository, tagMapper);
    }

    @Test
    @DisplayName("1 Unit test: findAllTags() from Tag-service")
    void testShouldReturnListWithOneTag() {
        given(tagRepository.findAll(PAGEABLE_SIZE_2))
                .willReturn(PAGE_OF_TAGS);
        given(tagMapper.tagToDto(TAG_ID_1))
                .willReturn(DTO_ID_1);

        List<TagDto> allTags = tagService.findAllTags(PAGEABLE_SIZE_2);
        Assertions.assertEquals(DTO_LIST, allTags);
    }

    @Test
    @DisplayName("2 Unit test: getTagById() from Tag-service")
    void testShouldFindTagDtoById() {
        given(tagRepository.findById(ID_1L))
                .willReturn(Optional.of(TAG_ID_1));
        given(tagMapper.tagToDto(TAG_ID_1))
                .willReturn(DTO_ID_1);

        TagDto tagById = tagService.findTagById(ID_1L);
        Assertions.assertEquals(DTO_ID_1, tagById);
    }

    @Test
    @DisplayName("3 Unit test: saveTag() from Tag-service")
    void testShouldSaveTag() {
        given(tagRepository.save(TAG_WITHOUT_ID))
                .willReturn(TAG_ID_1);
        given(tagRepository.existsByName(TAG_WITHOUT_ID.getName()))
                .willReturn(false);
        given(tagMapper.dtoToTag(DTO_WITHOUT_ID))
                .willReturn(TAG_WITHOUT_ID);
        given(tagMapper.tagToDto(TAG_ID_1))
                .willReturn(DTO_ID_1);

        TagDto saveTag = tagService.saveTag(DTO_WITHOUT_ID);
        Assertions.assertEquals(DTO_ID_1, saveTag);
    }

    @Test
    @DisplayName("4 Unit test: updateTag() from Tag-service")
    void testShouldUpdateTag() {
        given(tagRepository.findById(ID_1L))
                .willReturn(Optional.of(TAG_ID_1));
        given(tagRepository.existsByName(any()))
                .willReturn(false);
        given(tagRepository.save(any()))
                .willReturn(TAG_ID_1_UPDATED);

        doNothing().when(tagMapper)
                .updateFromDto(TAG_ID_1, DTO_ID_1_UPDATED);
        given(tagMapper.tagToDto(any()))
                .willReturn(DTO_ID_1_UPDATED);

        TagDto updateTag = tagService.updateTag(ID_1L, DTO_ID_1_UPDATED);
        Assertions.assertEquals(DTO_ID_1_UPDATED, updateTag);
    }

    @Test
    @DisplayName("5 Unit test: deleteTag() from Tag-service")
    void testShouldDeleteTagById() {
        given(tagRepository.findById(ID_1L))
                .willReturn(Optional.of(TAG_ID_1));
        tagService.deleteTag(ID_1L);
        verify(tagRepository, times(1)).delete(TAG_ID_1);
    }

    @Test
    @DisplayName("6 Unit test: findByNameOrSave() from Tag-service")
    void testShouldFindByName() {
        given(tagRepository.findByName(DTO_ID_1.getName()))
                .willReturn(Optional.of(TAG_ID_1));
        Tag byNameOrSave = tagService.findByNameOrSave(DTO_ID_1);
        Assertions.assertEquals(TAG_ID_1, byNameOrSave);
    }
}