package ru.clevertec.ecl.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exception.DuplicateException;
import ru.clevertec.ecl.exception.NotFountException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.service.interfaces.TagService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    @Override
    public List<TagDto> findAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable)
                .map(tagMapper::tagToDto).getContent();
    }

    @Override
    public TagDto findTagById(Long id) {
        return tagRepository.findById(id)
                .map(tagMapper::tagToDto)
                .orElseThrow(() -> new NotFountException("tag", "id", id));
    }

    @Transactional
    @Override
    public TagDto saveTag(TagDto dto) {
        if (tagRepository.existsByName(dto.getName())) {
            throw new DuplicateException("tag", "name", dto.getName());
        }
        Tag tagToSave = tagMapper.dtoToTag(dto);
        Tag savedTag = tagRepository.save(tagToSave);
        return tagMapper.tagToDto(savedTag);
    }

    @Transactional
    @Override
    public TagDto updateTag(long id, TagDto dto) {

        Tag tagFromDB = tagRepository.findById(id)
                .orElseThrow(() -> new NotFountException("tag", "id", id));

        if (tagRepository.existsByName(dto.getName())) {                    // тимофей ориентировался на стандартные
            throw new DuplicateException("tag", "name", dto.getName());
        }

        tagMapper.updateFromDto(tagFromDB, dto);
        tagFromDB.setId(id);
        Tag savedTad = tagRepository.save(tagFromDB);
        return tagMapper.tagToDto(savedTad);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        Tag tagFromDB = tagRepository.findById(id)
                .orElseThrow(() -> new NotFountException("tag", "id", id));
        tagRepository.delete(tagFromDB);
    }

    @Override
    @Transactional
    public Tag findByNameOrSave(TagDto dto) {
        return tagRepository.findByName(dto.getName())
                .orElseGet(() -> tagRepository.save(Tag.builder()
                                                    .name(dto.getName())
                                                    .build()));
    }

    @Override
    public TagDto findMostPopularAndExpensiveTag() {
        return tagRepository.findMostWidelyUsedTagWithHighestCost()
                .map(tagMapper::tagToDto)
                .orElseThrow(() -> new NotFountException("tag", "most popular tag", null));
    }
}
