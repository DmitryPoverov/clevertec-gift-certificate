package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.GiftCertificate;

import java.util.List;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
        PagingAndSortingRepository<GiftCertificate, Long> {

    boolean existsByName(String name);

    @Query(value = "FROM GiftCertificate gc " +
                   "JOIN gc.tags t " +
                   "WHERE t.name =:tagName")
    List<GiftCertificate> findCertificatesByTagName(@Param("tagName")String tagName);

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
}
