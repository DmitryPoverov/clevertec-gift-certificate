package ru.clevertec.ecl.service.implementations;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.GiftCertificateDto;
import ru.clevertec.ecl.dto.TagDto;
import ru.clevertec.ecl.entities.GiftCertificate;
import ru.clevertec.ecl.entities.Tag;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.service.interfaces.GiftCertificateService;
import ru.clevertec.ecl.service.interfaces.TagService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    private final TagMapper tagMapper = Mappers.getMapper(TagMapper.class);
    private final GiftCertificateMapper certificateMapper = Mappers.getMapper(GiftCertificateMapper.class);
    private GiftCertificateRepository repository;
    private TagService tagService;
    private GiftCertificateService service;

    private static final long ID_1 = 1L;
    private static final long DURATION_11 = 11L;
    private static final String TAG_1_NAME = "test1";
    private static final String CERTIFICATE_1_NAME_OR_DESCRIPTION = "certificate1";
    private static final String CERTIFICATE_1_NAME_OR_DESCRIPTION_UPD = "certificate1_UPD";
    private static final BigDecimal PRICE_11_11 = BigDecimal.valueOf(11.11);

    private static final Tag TAG_ID_1 = Tag.builder()
            .id(ID_1)
            .name(TAG_1_NAME)
            .build();

    private static final TagDto TAG_DTO_ID_1 = TagDto.builder()
            .id(ID_1)
            .name(TAG_1_NAME)
            .build();

    private static final List<Tag> TAG_LIST = Collections.singletonList(TAG_ID_1);
    private static final List<TagDto> TAG_DTO_LIST = Collections.singletonList(TAG_DTO_ID_1);

    private final GiftCertificate certificate = GiftCertificate.builder()
            .name(CERTIFICATE_1_NAME_OR_DESCRIPTION)
            .description(CERTIFICATE_1_NAME_OR_DESCRIPTION)
            .price(PRICE_11_11)
            .duration(DURATION_11)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now())
            .tags(TAG_LIST)
            .build();

    private final GiftCertificate certificateId1 = GiftCertificate.builder()
            .id(1L)
            .name(CERTIFICATE_1_NAME_OR_DESCRIPTION)
            .description(CERTIFICATE_1_NAME_OR_DESCRIPTION)
            .price(PRICE_11_11)
            .duration(DURATION_11)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now())
            .tags(TAG_LIST)
            .build();

    private final GiftCertificate certificateId1Upd = GiftCertificate.builder()
            .id(1L)
            .name(CERTIFICATE_1_NAME_OR_DESCRIPTION_UPD)
            .description(CERTIFICATE_1_NAME_OR_DESCRIPTION_UPD)
            .price(PRICE_11_11)
            .duration(DURATION_11)
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now())
            .tags(TAG_LIST)
            .build();

    private final GiftCertificateDto certificateId1Dto = GiftCertificateDto.builder()
            .id(1L)
            .name(CERTIFICATE_1_NAME_OR_DESCRIPTION)
            .description(CERTIFICATE_1_NAME_OR_DESCRIPTION)
            .price(PRICE_11_11)
            .duration(DURATION_11)
            .createDate(certificateId1.getCreateDate())
            .lastUpdateDate(certificateId1.getLastUpdateDate())
            .tags(TAG_DTO_LIST)
            .build();

    private final GiftCertificateDto certificateId1DtoUpd = GiftCertificateDto.builder()
            .id(ID_1)
            .name(CERTIFICATE_1_NAME_OR_DESCRIPTION_UPD)
            .description(CERTIFICATE_1_NAME_OR_DESCRIPTION_UPD)
            .build();

    private final GiftCertificateDto certificateId1DtoUpdated = GiftCertificateDto.builder()
            .id(ID_1)
            .name(CERTIFICATE_1_NAME_OR_DESCRIPTION_UPD)
            .description(CERTIFICATE_1_NAME_OR_DESCRIPTION_UPD)
            .price(PRICE_11_11)
            .duration(DURATION_11)
            .createDate(certificateId1.getCreateDate())
            .lastUpdateDate(certificateId1.getLastUpdateDate())
            .tags(TAG_DTO_LIST)
            .build();

    private final List<GiftCertificate> CERTIFICATE_LIST = Collections.singletonList(certificateId1);
    private final List<GiftCertificateDto> CERTIFICATE_DTO_LIST = Collections.singletonList(certificateId1Dto);

    private final Page<GiftCertificate> CERTIFICATE_PAGE = new PageImpl<>(CERTIFICATE_LIST);
    private final Pageable pageable = Pageable.ofSize(2);

    @BeforeEach
    void beforeEach() {
        repository = Mockito.mock(GiftCertificateRepository.class);
        tagService = Mockito.mock(TagServiceImpl.class);
        service = new GiftCertificateServiceImpl(repository, certificateMapper, tagService, tagMapper);
    }

    @Test
    @DisplayName("1: findAllCertificates() from GiftCertificate-service")
    void findAllCertificates() {
        given(repository.findAll(pageable))
                .willReturn(CERTIFICATE_PAGE);

        List<GiftCertificateDto> list = service.findCertificates(pageable);
        Assertions.assertEquals(CERTIFICATE_DTO_LIST, list);
    }

    @Test
    @DisplayName("2: findCertificateById() from GiftCertificate-service")
    void findCertificateById() {
        given(repository.findById(ID_1))
                .willReturn(Optional.of(certificateId1));

        GiftCertificateDto byId = service.findCertificateById(ID_1);
        Assertions.assertEquals(certificateId1Dto, byId);
    }

    @Test
    @DisplayName("3: saveCertificate() from GiftCertificate-service")
    void saveCertificate() {
        given(repository.existsByName(CERTIFICATE_1_NAME_OR_DESCRIPTION))
                .willReturn(false);
        given(tagService.findByNameOrSave(TAG_DTO_ID_1))
                .willReturn(TAG_ID_1);
        given(repository.save(certificate))
                .willReturn(certificateId1);

        GiftCertificateDto dto = service.saveCertificate(certificateId1Dto);
        Assertions.assertEquals(certificateId1Dto, dto);
    }

    @Test
    @DisplayName("4: updateCertificate() from GiftCertificate-service")
    void updateCertificate() {
        given(repository.findById(ID_1))
                .willReturn(Optional.of(certificateId1));
        String NAME_UPD = "certificate1_UPD";
        given(repository.existsByName(NAME_UPD))
                .willReturn(false);
        given(repository.save(certificateId1Upd))
                .willReturn(certificateId1Upd);

        GiftCertificateDto dto = service.updateCertificate(ID_1, certificateId1DtoUpd);
        Assertions.assertEquals(certificateId1DtoUpdated, dto);
    }

    @Test
    @DisplayName("5: deleteCertificate() from GiftCertificate-service")
    void deleteCertificate() {
        given(repository.findById(ID_1))
                .willReturn(Optional.of(certificateId1));

        service.deleteCertificate(ID_1);
        verify(repository, times(1)).delete(certificateId1);
    }
}