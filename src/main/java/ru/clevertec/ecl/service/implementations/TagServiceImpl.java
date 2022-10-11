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

    private final TagRepository repository;
    private final TagMapper mapper;

    @Override
    public List<TagDto> findAllTags(Pageable pageable) {
        return repository.findAll(pageable)
                .map(mapper::tagToDto).getContent();
    }

    @Override
    public TagDto findTagById(Long id) {
        return repository.findById(id)
                .map(mapper::tagToDto)
                .orElseThrow(() -> new NotFountException("tag", "id", id));
    }

    @Transactional
    @Override
    public TagDto saveTag(TagDto dto) {
        if (repository.existsByName(dto.getName())) {
            throw new DuplicateException("tag", "name", dto.getName());
        }
        Tag tagToSave = mapper.dtoToTag(dto);
        Tag savedTag = repository.save(tagToSave);
        return mapper.tagToDto(savedTag);
    }

    @Transactional
    @Override
    public TagDto updateTag(long id, TagDto dto) {

        Tag tagFromDB = repository.findById(id)
                .orElseThrow(() -> new NotFountException("tag", "id", id));

        if (repository.existsByName(dto.getName())) {                    // тимофей ориентировался на стандартные
            throw new DuplicateException("tag", "name", dto.getName());
        }

        mapper.updateFromDto(tagFromDB, dto);

        Tag savedTad = repository.save(tagFromDB);
        return mapper.tagToDto(savedTad);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        Tag tagFromDB = repository.findById(id)
                .orElseThrow(() -> new NotFountException("tag", "id", id));
        repository.delete(tagFromDB);
    }

    @Override
    @Transactional
    public Tag findByNameOrSave(TagDto dto) {
        return repository.findByName(dto.getName())
                .orElseGet(() -> repository.save(Tag.builder()
                                                    .name(dto.getName())
                                                    .build()));
    }
}
