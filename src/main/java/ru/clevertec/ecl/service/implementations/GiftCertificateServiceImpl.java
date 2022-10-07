package ru.clevertec.ecl.service.implementations;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.GiftCertificate;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.exception.DuplicateException;
import ru.clevertec.ecl.exception.NotFountException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.service.interfaces.GiftCertificateService;
import ru.clevertec.ecl.service.interfaces.TagService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateServiceImpl implements GiftCertificateService {

    private final GiftCertificateRepository repository;
    private final GiftCertificateMapper certificateMapper;
    private final TagService tagService;

    @Override
    public List<GiftCertificateDto> findAllCertificates(Pageable pageable) {
        Page<GiftCertificate> all = repository.findAll(pageable);
        Page<GiftCertificateDto> map = all
                .map(certificateMapper::giftCertificateToDto);
        return map.getContent();
    }

    @Override
    public GiftCertificateDto findCertificateById(Long id) {
        return repository.findById(id)
                .map(certificateMapper::giftCertificateToDto)
                .orElseThrow(() -> new NotFountException("gift certificate", "id", id));
    }

    @Override
    @Transactional
    public GiftCertificateDto saveCertificate(GiftCertificateDto dto) {

        if (repository.existsByName(dto.getName())) {
            throw new DuplicateException("gift certificate", "name", dto.getName());
        }

        List<TagDto> uncheckedTags = dto.getTags();

        List<Tag> collect = uncheckedTags.stream()
                .map(tagService::findByNameOrSave)
                .collect(Collectors.toList());

        GiftCertificate certificate = certificateMapper.dtoToGiftCertificate(dto);
        certificate.setTags(collect);

        GiftCertificate savedCertificate = repository.save(certificate);
        return certificateMapper.giftCertificateToDto(savedCertificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(GiftCertificateDto dto) {
        GiftCertificate certificateFromDB = repository.findById(dto.getId())
                .orElseThrow(() -> new NotFountException("gift certificate", "id", dto.getId()));

        if (repository.existsByName(dto.getName())) {
            throw new DuplicateException("gift certificate", "name", dto.getName());
        }

        certificateMapper.updateFromDto(certificateFromDB, dto);

        GiftCertificate savedCertificate = repository.save(certificateFromDB);
        return certificateMapper.giftCertificateToDto(savedCertificate);
    }

    @Override
    @Transactional
    public void deleteCertificate(Long id) {
        GiftCertificate certificateFromDB = repository.findById(id)
                .orElseThrow(() -> new NotFountException("gift certificate", "id", id));
        repository.delete(certificateFromDB);
    }
}
