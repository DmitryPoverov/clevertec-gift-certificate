package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.SearchingDto;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDto> findCertificates(Pageable pageable);

    GiftCertificateDto findCertificateById(long id);

    List<GiftCertificateDto> findGiftCertificatesByOneTagName(String tagName);

    List<GiftCertificateDto> findCertificatesWithParameters(SearchingDto dto, Pageable pageable);

    GiftCertificateDto saveCertificate(GiftCertificateDto dto);

    GiftCertificateDto updateCertificate(long id, GiftCertificateDto dto);

    void deleteCertificate(Long id);
}
