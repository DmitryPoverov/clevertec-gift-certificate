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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GiftCertificateRepositoryTest {

    @Autowired
    private GiftCertificateRepository repository;

    private final GiftCertificate certificate1 = GiftCertificate.builder().id(1L).name("massage White lotus_test")
            .description("one free massage").price(BigDecimal.valueOf(11.11)).duration(11)
            .createDate(LocalDateTime.parse("2022-09-27T11:11"))
            .lastUpdateDate(LocalDateTime.parse("2022-09-27T11:11")).build();

    @Test
    @DisplayName("1: findAll() from GiftCertificate-repository")
    public void testShouldFindFirstTwoCertificatesAndReturnThem() {
        Pageable pageable = PageRequest.of(0, 2, Sort.by("id").ascending());
        List<GiftCertificate> actual = repository.findAll(pageable).getContent();
        assertNotNull(actual);
    }

    @Test
    @DisplayName("2: findById() from GiftCertificate-repository")
    void testShouldFindTagById() {
        GiftCertificate byId = repository.findById(1L).orElseThrow(RuntimeException::new);
        assertEquals(certificate1, byId);
    }

    @Test
    @DisplayName("3: existsByName() from GiftCertificate-repository")
    void testShouldFindOutDoesCertificateExistsByName() {
        String name = "massage White lotus_test";

        assertTrue(repository.existsByName(name));
    }

    @Test
    @DisplayName("4: save() from GiftCertificate-repository")
    @Transactional
    @Rollback
    void testShouldSaveNewCertificate() {
        GiftCertificate certificate = GiftCertificate.builder().name("Certificate_name_test")
                .description("Certificate_description_test").price(BigDecimal.valueOf(88.88)).duration(88)
                .createDate(LocalDateTime.parse("2022-09-27T11:11"))
                .lastUpdateDate(LocalDateTime.parse("2022-09-27T11:11")).build();

        GiftCertificate save = repository.save(certificate);

        assertNotEquals(0 ,save.getId());
    }

    @Test
    @DisplayName("5: findCertificatesByTagName() from GiftCertificate-repository")
    public void testShouldFindCertificateByTagNameAndReturnNotNull() {
        List<GiftCertificate> certificates = repository.findCertificatesByTagName("extreme_test");
        assertEquals(2, certificates.size());
    }

    @Test
    @DisplayName("6: findCertificatesByPartOfNameOrDescription() from GiftCertificate-repository")
    public void testShouldFindCertificatePartOfNameOrDescription() {
        List<GiftCertificate> certificates = repository.findCertificatesByPartOfNameOrDescription("as", "as");
        assertEquals(2, certificates.size());
    }
}