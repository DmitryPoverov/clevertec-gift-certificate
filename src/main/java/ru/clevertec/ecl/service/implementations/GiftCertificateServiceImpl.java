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
    public GiftCertificateDto findCertificateById(long id) {
        return certificateRepository.findById(id)
                .map(certificateMapper::giftCertificateToDto)
                .orElseThrow(() -> new NotFountException("gift certificate", "id", id));
    }

    @Override
    public List<GiftCertificateDto> findGiftCertificatesByOneTagName(String tagName) {
        return certificateRepository.findCertificatesByTagName(tagName)
                .stream()
                .map(certificateMapper::giftCertificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<GiftCertificateDto> findCertificatesWithParameters(SearchingDto dto, Pageable pageable) {
        pageable = handlePageable(dto, pageable);

        return certificateRepository.findCertificatesWithParameters(dto.getTagName(), dto.getPartOfName(),
                                                         dto.getPartOfDescription(), pageable)
                .getContent()
                .stream()
                .map(certificateMapper::giftCertificateToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto saveCertificate(GiftCertificateDto dto) {
        findCertificateNameOrThrow(dto.getName());

        List<TagDto> uncheckedTags = dto.getTags();

        List<Tag> collect = uncheckedTags.stream()
                .map(tagService::findByNameOrSave)
                .collect(Collectors.toList());

        GiftCertificate certificate = certificateMapper.dtoToGiftCertificate(dto);
        certificate.setTags(collect);

        GiftCertificate savedCertificate = certificateRepository.save(certificate);
        return certificateMapper.giftCertificateToDto(savedCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(long id, GiftCertificateDto dto) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(id);
        findCertificateNameOrThrow(dto.getName());

        certificateMapper.updateFromDto(certificateFromDB, dto);
        GiftCertificate savedCertificate = certificateRepository.save(certificateFromDB);
        return certificateMapper.giftCertificateToDto(savedCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificateName(long id, GiftCertificateNameDto dto) {
        GiftCertificate certificate = findAndReturnCertificateByIdOrThrow(id);
        findCertificateNameOrThrow(dto.getName());

        certificate.setName(dto.getName());
        certificateRepository.updateGiftCertificateName(dto.getName(), id);
        return certificateMapper.giftCertificateToDto(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificateDescription(long id, GiftCertificateDescriptionDto dto) {
        GiftCertificate certificate = findAndReturnCertificateByIdOrThrow(id);

        certificate.setDescription(dto.getDescription());

        certificateRepository.updateGiftCertificateDescription(dto.getDescription(), id);
        return certificateMapper.giftCertificateToDto(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificatePrice(long id, GiftCertificatePriceDto dto) {
        GiftCertificate certificate = findAndReturnCertificateByIdOrThrow(id);

        certificate.setPrice(dto.getPrice());
        certificateRepository.updateGiftCertificatePrice(dto.getPrice(), id);
        return certificateMapper.giftCertificateToDto(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificateDuration(long id, GiftCertificateDurationDto dto) {
        GiftCertificate certificate = findAndReturnCertificateByIdOrThrow(id);

        certificate.setDuration(dto.getDuration());
        certificateRepository.updateGiftCertificateDuration(dto.getDuration(), id);
        return certificateMapper.giftCertificateToDto(certificate);
    }

    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        GiftCertificate certificateFromDB = findAndReturnCertificateByIdOrThrow(id);

        certificateRepository.delete(certificateFromDB);
    }

    @Override
    @Transactional
    public GiftCertificate findByNameOrSave(GiftCertificateDto dto) {
        return certificateRepository.findByName(dto.getName())
                .orElseGet(() -> certificateRepository.save(GiftCertificate.builder()
                                .name(dto.getName())
                                .description(dto.getDescription())
                                .price(dto.getPrice())
                                .duration(dto.getDuration())
                                .tags(dto.getTags().stream().map(tagMapper::dtoToTag).collect(Collectors.toList()))
                        .build()));
    }

    @Override
    public List<GiftCertificateDto> findAllGiftCertificatesByTagNames(List<String> names, Pageable pageable) {
        return certificateRepository.findGiftCertificatesByTagNames(names, pageable)
                .stream()
                .map(certificateMapper::giftCertificateToDto)
                .collect(Collectors.toList());
    }

    private GiftCertificate findAndReturnCertificateByIdOrThrow(long id) {
        return certificateRepository.findById(id)
                .orElseThrow(() -> new NotFountException("gift certificate", "id", id));
    }

    private void findCertificateNameOrThrow(String name) {
        if (certificateRepository.existsByName(name)) {
            throw new DuplicateException("gift certificate", "name", name);
        }
    }

    private static Pageable handlePageable(SearchingDto dto, Pageable pageable) {
        if (dto.isSortByName()) {
            if (dto.isSortAscending()) {
                pageable = PageRequest
                        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").ascending());
            } else {
                pageable = PageRequest
                        .of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by("name").descending());
            }
        } else {
            if (dto.isSortAscending()) {
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
