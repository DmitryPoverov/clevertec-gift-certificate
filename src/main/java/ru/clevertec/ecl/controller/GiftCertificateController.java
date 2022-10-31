package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.service.interfaces.GiftCertificateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService service;

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAllCertificates(@PageableDefault Pageable pageable) {
        List<GiftCertificateDto> allCertificates = service.findCertificates(pageable);
        return ResponseEntity.ok(allCertificates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findCertificateById(@PathVariable long id) {
        GiftCertificateDto dtoById = service.findCertificateById(id);
        return ResponseEntity.ok(dtoById);
    }

    @GetMapping("/parameters")
    public ResponseEntity<List<GiftCertificateDto>> findCertificatesWithParameters(
                                        @PageableDefault Pageable pageable,
                                        @RequestParam(required = false) String tagName,
                                        @RequestParam(required = false) String partOfName,
                                        @RequestParam(required = false) String partOfDescription,
                                        @RequestParam(required = false, defaultValue = "false") boolean sortByName,
                                        @RequestParam(required = false, defaultValue = "true") boolean sortAscending) {
        SearchingDto searchingDto = SearchingDto.builder()
                .tagName(tagName)
                .partOfName(partOfName)
                .partOfDescription(partOfDescription)
                .sortByName(sortByName)
                .sortAscending(sortAscending).build();
        List<GiftCertificateDto> certificates = service.findCertificatesWithParameters(searchingDto, pageable);
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/tags/name/{tagName}")
    public ResponseEntity<List<GiftCertificateDto>> findGiftCertificatesByOneTagName(@PathVariable String tagName) {
        List<GiftCertificateDto> certificateByTagName = service.findGiftCertificatesByOneTagName(tagName);
        return ResponseEntity.ok(certificateByTagName);
    }

    @GetMapping("/tags/names/{tagNames}")
    public ResponseEntity<List<GiftCertificateDto>> getCertificateByTagsName(@PathVariable List<String> tagNames,
                                                                             @PageableDefault Pageable pageable) {
        List<GiftCertificateDto> certificatesByTagNames = service.findAllGiftCertificatesByTagNames(tagNames, pageable);
        return ResponseEntity.ok(certificatesByTagNames);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> addNewCertificate(@RequestBody GiftCertificateDto dto) {
        GiftCertificateDto certificateDto = service.saveCertificate(dto);
        return ResponseEntity.ok(certificateDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateCertificate(@PathVariable long id,
                                                                @RequestBody GiftCertificateDto dto) {
        GiftCertificateDto certificateDto = service.updateCertificate(id, dto);
        return ResponseEntity.ok(certificateDto);
    }

    @PatchMapping("/names/{id}")
    public ResponseEntity<GiftCertificateDto> updateCertificateName(@PathVariable long id,
                                                                    @RequestBody GiftCertificateNameDto dto) {
        GiftCertificateDto certificateDto = service.updateCertificateName(id, dto);
        return ResponseEntity.ok(certificateDto);
    }

    @PatchMapping("/descriptions/{id}")
    public ResponseEntity<GiftCertificateDto> updateCertificateDescription(@PathVariable long id,
                                                                           @RequestBody GiftCertificateDescriptionDto dto) {
        GiftCertificateDto certificateDto = service.updateCertificateDescription(id, dto);
        return ResponseEntity.ok(certificateDto);
    }

    @PatchMapping("/prices/{id}")
    public ResponseEntity<GiftCertificateDto> updateCertificatePrice(@PathVariable long id,
                                                                     @RequestBody GiftCertificatePriceDto dto) {
        GiftCertificateDto certificateDto = service.updateCertificatePrice(id, dto);
        return ResponseEntity.ok(certificateDto);
    }

    @PatchMapping("/durations/{id}")
    public ResponseEntity<GiftCertificateDto> updateCertificateDuration(@PathVariable long id,
                                                                        @RequestBody GiftCertificateDurationDto dto) {
        GiftCertificateDto certificateDto = service.updateCertificateDuration(id, dto);
        return ResponseEntity.ok(certificateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCertificate(@PathVariable long id) {
        service.deleteCertificate(id);
        return ResponseEntity.noContent().build();
    }
}
