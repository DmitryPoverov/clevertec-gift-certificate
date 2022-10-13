package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.SearchingDto;
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
    public ResponseEntity<List<GiftCertificateDto>> findCertificatesWithParameters
                                       (@PageableDefault Pageable pageable,
                                        @RequestParam(required = false) String tagName,
                                        @RequestParam(required = false) String partOfName,
                                        @RequestParam(required = false) String partOfDescription,
                                        @RequestParam(required = false, defaultValue = "false") boolean sortByName,
                                        @RequestParam(required = false, defaultValue = "true") boolean ascending) {
        List<GiftCertificateDto> certificates = service.findCertificatesWithParameters(
                SearchingDto.builder()
                .tagName(tagName)
                .partOfName(partOfName)
                .partOfDescription(partOfDescription)
                .sortByName(sortByName)
                .sortAscending(ascending).build(), pageable);
        return ResponseEntity.ok(certificates);
    }

    @GetMapping("/tag-names/{tagName}")
    public ResponseEntity<List<GiftCertificateDto>> findGiftCertificatesByOneTagName(@PathVariable String tagName) {
        List<GiftCertificateDto> certificateByTagName = service.findGiftCertificatesByOneTagName(tagName);
        return ResponseEntity.ok(certificateByTagName);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> addNewCertificate(@RequestBody GiftCertificateDto dto) {
        GiftCertificateDto certificateDto = service.saveCertificate(dto);
        return ResponseEntity.ok(certificateDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> updateCertificate(@PathVariable long id, @RequestBody GiftCertificateDto dto) {
        GiftCertificateDto certificateDto = service.updateCertificate(id, dto);
        return ResponseEntity.ok(certificateDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCertificate(@RequestBody long id) {
        service.deleteCertificate(id);
        return ResponseEntity.ok().build();
    }
}
