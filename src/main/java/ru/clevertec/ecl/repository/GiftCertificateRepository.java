package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.GiftCertificate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
                                                   PagingAndSortingRepository<GiftCertificate, Long> {

    boolean existsByName(String name);

    Optional<GiftCertificate> findByName(String name);

    @Query(value = "FROM GiftCertificate gc " +
                   "JOIN gc.tags t " +
                   "WHERE t.name =:tagName")
    List<GiftCertificate> findCertificatesByTagName(@Param("tagName") String tagName);

    List<GiftCertificate> findByTags_NameIsIn(List<String> tagNames);

    @Query(value = "SELECT gc.* " +
                   "FROM gift_certificate gc " +
                   "JOIN gift_certificate_tag gct on gc.id = gct.gift_certificate_id " +
                   "JOIN tag t on gct.tag_id = t.id " +
                   "WHERE " +
                   "(t.name = :tagName OR :tagName IS NULL)" +
                   "AND " +
                   "(gc.name ILIKE concat('%', :partOfName, '%') OR :partOfName IS NULL) " +
                   "AND " +
                   "(gc.description ILIKE concat('%', :partOfDescription, '%') OR :partOfDescription IS NULL)",
            nativeQuery = true)
    Page<GiftCertificate> findCertificatesWithParameters(@Param("tagName") String tagName,
                                                         @Param("partOfName") String partOfName,
                                                         @Param("partOfDescription") String partOfDescription,
                                                         Pageable pageable);

    @Modifying
    @Query(value = "UPDATE GiftCertificate SET name = :name WHERE id = :id")
    void updateGiftCertificateName(@Param("name") String name,
                                   @Param("id") long id);

    @Modifying
    @Query(value = "UPDATE GiftCertificate SET description = :description WHERE id = :id")
    void updateGiftCertificateDescription(@Param("description") String description,
                                          @Param("id") long id);

    @Modifying
    @Query(value = "UPDATE GiftCertificate SET price = :price WHERE id = :id")
    void updateGiftCertificatePrice(@Param("price") BigDecimal price,
                                    @Param("id") long id);

    @Modifying
    @Query(value = "UPDATE GiftCertificate SET duration = :duration WHERE id = :id")
    void updateGiftCertificateDuration(@Param("duration") long duration,
                                    @Param("id") long id);
}
