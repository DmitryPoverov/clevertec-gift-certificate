package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.GiftCertificateDto;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDto> findAllCertificates(Pageable pageable);

    GiftCertificateDto findCertificateById(Long id);

    List<GiftCertificateDto> findGiftCertificatesByOneTagName(String name);

    GiftCertificateDto saveCertificate(GiftCertificateDto dto);

    GiftCertificateDto updateCertificate(GiftCertificateDto dto);

    void deleteCertificate(Long id);
}
