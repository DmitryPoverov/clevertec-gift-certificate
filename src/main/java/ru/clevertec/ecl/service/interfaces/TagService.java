package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.Tag;

import java.util.List;

public interface TagService {

    List<TagDto> findAllTags(Pageable pageable);

    TagDto findTagById(Long id);

    TagDto saveTag(TagDto dto);

    TagDto updateTag(long id, TagDto dto);

    void deleteTag(Long id);

    Tag findByNameOrSave(TagDto dto);

    TagDto findMostPopularAndExpensiveTag();
}
