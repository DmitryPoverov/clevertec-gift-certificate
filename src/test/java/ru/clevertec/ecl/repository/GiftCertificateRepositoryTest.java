package ru.clevertec.ecl.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.entities.GiftCertificate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GiftCertificateRepositoryTest {

    private static final int ZERO = 0;
    private static final long ONE = 1L;
    private static final int TWO = 2;
    private static final int THREE = 3;
    private static final int TEN = 10;
    private static final long ELEVEN = 11L;
    private static final long EIGHTY_EIGHT = 88L;
    private static final String DESCRIPTION = "one free massage";
    private static final String STRING_ID = "id";
    private static final String STRING_AL = "al";
    private static final String STRING_SPORT = "sport_test";
    private static final String STRING_ANIMAL = "animal_test";
    private static final String STRING_EXTREME = "extreme_test";
    private static final String NAME = "massage White lotus_test";
    private static final String TEST_NAME = "Test_certificate_name";
    private static final String TEST_DESCRIPTION = "Test_certificate_description";
    private static final BigDecimal PRICE_11_11 = BigDecimal.valueOf(11.11);
    private static final LocalDateTime DATE = LocalDateTime.parse("2022-09-27T11:11");

    @Autowired
    private GiftCertificateRepository repository;

    private final GiftCertificate certificate = GiftCertificate.builder()
            .name(TEST_NAME)
            .description(TEST_DESCRIPTION)
            .price(PRICE_11_11)
            .duration(EIGHTY_EIGHT)
            .createDate(DATE)
            .lastUpdateDate(DATE)
            .build();

    private final GiftCertificate certificateId1 = GiftCertificate.builder()
            .id(ONE)
            .name(NAME)
            .description(DESCRIPTION)
            .price(PRICE_11_11)
            .duration(ELEVEN)
            .createDate(DATE)
            .lastUpdateDate(DATE)
            .build();

    @Test
    @DisplayName("1: findAll() from GiftCertificate-repository")
    public void testShouldFindFirstTwoCertificatesAndReturnThem() {
        Pageable pageable = PageRequest.of(ZERO, TWO, Sort.by(STRING_ID).ascending());
        List<GiftCertificate> actual = repository.findAll(pageable).getContent();
        assertNotNull(actual);
    }

    @Test
    @DisplayName("2: findById() from GiftCertificate-repository")
    void testShouldFindTagById() {
        GiftCertificate byId = repository.findById(ONE).orElseThrow(RuntimeException::new);
        assertEquals(certificateId1, byId);
    }

    @Test
    @DisplayName("3: existsByName() from GiftCertificate-repository")
    void testShouldFindOutDoesCertificateExistsByName() {
        assertTrue(repository.existsByName(NAME));
    }

    @Test
    @DisplayName("4: save() from GiftCertificate-repository")
    @Transactional
    @Rollback
    void testShouldSaveNewCertificate() {
        GiftCertificate save = repository.save(certificate);
        assertNotEquals(ZERO ,save.getId());
    }

    @Test
    @DisplayName("5: findCertificatesByTagName() from GiftCertificate-repository")
    public void testShouldFindCertificateByTagNameAndReturnNotNull() {
        List<GiftCertificate> certificates = repository.findCertificatesByTagName(STRING_EXTREME);
        assertEquals(TWO, certificates.size());
    }

    @Test
    @DisplayName("6: findCertificatesByPartOfNameOrDescription() from GiftCertificate-repository")
    public void testShouldFindCertificateByPartOfNameOrDescription() {
        List<GiftCertificate> certificates = repository.findCertificatesWithParameters
                (STRING_SPORT, STRING_AL, null,  Pageable.ofSize(TEN)).getContent();
        assertEquals(TWO, certificates.size());
    }

    @Test
    @DisplayName("7: findCertificatesByAFewTagNames() from GiftCertificate-repository")
    public void testShouldFindCertificateByTagsAndNameIsIn() {
        List<GiftCertificate> certificates = repository.findByTags_NameIsIn(Arrays.asList(STRING_EXTREME, STRING_ANIMAL));
        assertEquals(THREE, certificates.size());
    }
}
