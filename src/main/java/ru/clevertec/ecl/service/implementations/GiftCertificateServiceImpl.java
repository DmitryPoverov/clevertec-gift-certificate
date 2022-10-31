package ru.clevertec.ecl.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.entities.GiftCertificate;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exception.DuplicateException;
import ru.clevertec.ecl.exception.NotFountException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.service.interfaces.GiftCertificateService;
import ru.clevertec.ecl.service.interfaces.TagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private static final String ID = "id";
    private static final String NAME = "gift certificate";
    private static final String GIFT_CERTIFICATE = "gift certificate";

    private final GiftCertificateRepository certificateRepository;
    private final GiftCertificateMapper certificateMapper;
    private final TagService tagService;
    private final TagMapper tagMapper;

    @Override
    public List<GiftCertificateDto> findCertificates(Pageable pageable) {
        return certificateRepository.findAll(pageable)
                .map(certificateMapper::giftCertificateToDto)
                .getContent();
    }

    @Override
    public GiftCertificateDto findCertificateById(long giftCertificateId) {
        return certificateRepository.findById(giftCertificateId)
                .map(certificateMapper::giftCertificateToDto)
                .orElseThrow(() -> new NotFountException("gift certificate", "id", giftCertificateId));
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificatesByOneTagName(String tagName) {
        return certificateRepository.findCertificatesByTagName(tagName).stream()
                .map(certificateMapper::giftCertificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findCertificatesWithParameters(SearchingDto searchingDto, Pageable pageable) {
        pageable = handlePageable(searchingDto, pageable);

        return certificateRepository.findCertificatesWithParameters(searchingDto.getTagName(), searchingDto.getPartOfName(),
                        searchingDto.getPartOfDescription(), pageable)
                .getContent().stream()
                .map(certificateMapper::giftCertificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto saveCertificate(GiftCertificateDto giftCertificateDto) {
        findCertificateNameOrThrow(giftCertificateDto.getName());

        List<TagDto> uncheckedTags = giftCertificateDto.getTags();

        List<Tag> collect = uncheckedTags.stream()
                .map(tagService::findByNameOrSave)
                .collect(Collectors.toList());

        GiftCertificate certificate = certificateMapper.dtoToGiftCertificate(giftCertificateDto);
        certificate.setTags(collect);

        GiftCertificate savedCertificate = certificateRepository.save(certificate);
        return certificateMapper.giftCertificateToDto(savedCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(long giftCertificateId, GiftCertificateDto giftCertificateDto) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(giftCertificateId);
        findCertificateNameOrThrow(giftCertificateDto.getName());

        certificateMapper.updateFromDto(certificateFromDB, giftCertificateDto);
        GiftCertificate savedCertificate = certificateRepository.save(certificateFromDB);
        return certificateMapper.giftCertificateToDto(savedCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificateName(long giftCertificateId, GiftCertificateNameDto giftCertificateNameDto) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(giftCertificateId);
        findCertificateNameOrThrow(giftCertificateNameDto.getName());

        certificateFromDB.setName(giftCertificateNameDto.getName());
        certificateRepository.updateGiftCertificateName(giftCertificateNameDto.getName(), giftCertificateId);
        return certificateMapper.giftCertificateToDto(certificateFromDB);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificateDescription(long giftCertificateId, GiftCertificateDescriptionDto giftCertificateDescriptionDto) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(giftCertificateId);

        certificateFromDB.setDescription(giftCertificateDescriptionDto.getDescription());

        certificateRepository.updateGiftCertificateDescription(giftCertificateDescriptionDto.getDescription(), giftCertificateId);
        return certificateMapper.giftCertificateToDto(certificateFromDB);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificatePrice(long giftCertificateId, GiftCertificatePriceDto giftCertificatePriceDto) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(giftCertificateId);

        certificateFromDB.setPrice(giftCertificatePriceDto.getPrice());
        certificateRepository.updateGiftCertificatePrice(giftCertificatePriceDto.getPrice(), giftCertificateId);
        return certificateMapper.giftCertificateToDto(certificateFromDB);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificateDuration(long giftCertificateId, GiftCertificateDurationDto giftCertificateDurationDto) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(giftCertificateId);

        certificateFromDB.setDuration(giftCertificateDurationDto.getDuration());
        certificateRepository.updateGiftCertificateDuration(giftCertificateDurationDto.getDuration(), giftCertificateId);
        return certificateMapper.giftCertificateToDto(certificateFromDB);
    }

    @Override
    @Transactional
    public void deleteCertificate(Long giftCertificateId) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(giftCertificateId);

        certificateRepository.delete(certificateFromDB);
    }

    @Override
    @Transactional
    public GiftCertificate findByNameOrSave(GiftCertificateDto giftCertificateDto) {
        return certificateRepository.findByName(giftCertificateDto.getName())
                .orElseGet(() -> certificateRepository.save(
                        GiftCertificate.builder()
                                .name(giftCertificateDto.getName())
                                .description(giftCertificateDto.getDescription())
                                .price(giftCertificateDto.getPrice())
                                .duration(giftCertificateDto.getDuration())
                                .tags(giftCertificateDto.getTags().stream()
                                        .map(tagMapper::dtoToTag)
                                        .collect(Collectors.toList()))
                                .build()));
    }

    @Override
    public List<GiftCertificateDto> findAllGiftCertificatesByTagNames(List<String> tagNames, Pageable pageable) {
        return certificateRepository.findGiftCertificatesByTagNames(tagNames, pageable).stream()
                .map(certificateMapper::giftCertificateToDto)
                .collect(Collectors.toList());
    }

    private GiftCertificate findAndReturnCertificateByIdOrThrow(long giftCertificateId) {
        return certificateRepository.findById(giftCertificateId)
                .orElseThrow(() -> new NotFountException(GIFT_CERTIFICATE, ID, giftCertificateId));
    }

    private void findCertificateNameOrThrow(String giftCertificateName) {
        if (certificateRepository.existsByName(giftCertificateName)) {
            throw new DuplicateException(GIFT_CERTIFICATE, NAME, giftCertificateName);
        }
    }

    private static Pageable handlePageable(SearchingDto searchingDto, Pageable pageable) {
        if (searchingDto.isSortByName()) {
            if (searchingDto.isSortAscending()) {
                pageable = PageRequest
                        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
            } else {
                pageable = PageRequest
                        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").descending());
            }
        } else {
            if (searchingDto.isSortAscending()) {
                pageable = PageRequest
                        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").ascending());
            } else {
                pageable = PageRequest
                        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("id").descending());
            }
        }
        return pageable;
    }
}
