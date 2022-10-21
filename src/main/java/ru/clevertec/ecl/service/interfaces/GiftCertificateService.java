package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.entities.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDto> findCertificates(Pageable pageable);

    GiftCertificateDto findCertificateById(long id);

    List<GiftCertificateDto> findGiftCertificatesByOneTagName(String tagName);

    List<GiftCertificateDto> findCertificatesWithParameters(SearchingDto dto, Pageable pageable);

    GiftCertificateDto saveCertificate(GiftCertificateDto dto);

    GiftCertificateDto updateCertificate(long id, GiftCertificateDto dto);

    GiftCertificateDto updateCertificateName(long id, GiftCertificateNameDto dto);

    GiftCertificateDto updateCertificateDescription(long id, GiftCertificateDescriptionDto dto);

    GiftCertificateDto updateCertificatePrice(long id, GiftCertificatePriceDto dto);

    GiftCertificateDto updateCertificateDuration(long id, GiftCertificateDurationDto dto);

    void deleteCertificate(Long id);

    GiftCertificate findByNameOrSave(GiftCertificateDto dto);

    List<GiftCertificateDto> findAllGiftCertificatesByTagNames(List<String> names, Pageable pageable);
}
