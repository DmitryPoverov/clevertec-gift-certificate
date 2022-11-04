package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.service.interfaces.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagDto>> findAllTags(@PageableDefault Pageable pageable) {
        List<TagDto> allDtos = tagService.findAllTags(pageable);
        return ResponseEntity.ok(allDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagDto> findTagById(@PathVariable long id) {
        TagDto dtoById = tagService.findTagById(id);
        return ResponseEntity.ok(dtoById);
    }

    @GetMapping("/highest-cost-tags")
    public ResponseEntity<TagDto> findMostPopularAndExpensiveTag() {
        TagDto mostPopularAndExpensiveTag = tagService.findMostPopularAndExpensiveTag();
        return ResponseEntity.ok(mostPopularAndExpensiveTag);
    }

    @PostMapping
    public ResponseEntity<TagDto> addNewTag(@RequestBody TagDto dto) {
        TagDto tagDto = tagService.saveTag(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(tagDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TagDto> updateTag(@PathVariable long id, @RequestBody TagDto dto) {
        TagDto tagDto = tagService.updateTag(id, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(tagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
