package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.service.interfaces.GiftCertificateService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/gift-certificates")
public class GiftCertificateController {

    private final GiftCertificateService service;

    @GetMapping
    public ResponseEntity<List<GiftCertificateDto>> findAllCertificates(@PageableDefault Pageable pageable) {
        List<GiftCertificateDto> allCertificates = service.findAllCertificates(pageable);
        return ResponseEntity.ok(allCertificates);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateDto> findCertificateById(@PathVariable long id) {
        GiftCertificateDto dtoById = service.findCertificateById(id);
        return ResponseEntity.ok(dtoById);
    }

    @GetMapping("/tag-name/{name}")
    public ResponseEntity<List<GiftCertificateDto>> findGiftCertificatesByOneTagName(@PathVariable String name) {
        List<GiftCertificateDto> certificateByTagName = service.findGiftCertificatesByOneTagName(name);
        return ResponseEntity.ok(certificateByTagName);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateDto> addNewCertificate(@RequestBody GiftCertificateDto dto) {
        GiftCertificateDto certificateDto = service.saveCertificate(dto);
        return ResponseEntity.ok(certificateDto);
    }

    @PatchMapping
    public ResponseEntity<GiftCertificateDto> updateCertificate(@RequestBody GiftCertificateDto dto) {
        GiftCertificateDto certificateDto = service.updateCertificate(dto);
        return ResponseEntity.ok(certificateDto);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteCertificate(@RequestBody long id) {
        service.deleteCertificate(id);
        return ResponseEntity.ok().build();
    }
}
