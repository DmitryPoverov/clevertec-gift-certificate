package ru.clevertec.ecl.service.interfaces;

import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.entities.GiftCertificate;

import java.util.List;

public interface GiftCertificateService {

    List<GiftCertificateDto> findCertificates(Pageable pageable);

    GiftCertificateDto findCertificateById(long giftCertificateId);

    List<GiftCertificateDto> findGiftCertificatesByOneTagName(String tagName);

    List<GiftCertificateDto> findCertificatesWithParameters(SearchingDto searchingDto, Pageable pageable);

    GiftCertificateDto saveCertificate(GiftCertificateDto giftCertificateDto);

    GiftCertificateDto updateCertificate(long giftCertificateId, GiftCertificateDto giftCertificateDto);

    GiftCertificateDto updateCertificateName(long giftCertificateId, GiftCertificateNameDto giftCertificateNameDto);

    GiftCertificateDto updateCertificateDescription(long giftCertificateId,
                                                    GiftCertificateDescriptionDto giftCertificateDescriptionDto);

    GiftCertificateDto updateCertificatePrice(long giftCertificateId, GiftCertificatePriceDto giftCertificatePriceDto);

    GiftCertificateDto updateCertificateDuration(long giftCertificateId,
                                                 GiftCertificateDurationDto giftCertificateDurationDto);

    void deleteCertificate(Long giftCertificateId);

    GiftCertificate findByNameOrSave(GiftCertificateDto giftCertificateDto);

    List<GiftCertificateDto> findAllGiftCertificatesByTagNames(List<String> tagNames, Pageable pageable);
}
