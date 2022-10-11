package ru.clevertec.ecl.repository;

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
                   "WHERE t.name =:name")
    List<GiftCertificate> findCertificatesByTagName(String name);

    @Query(value = "SELECT gc.* " +
                   "FROM gift_certificate gc " +
                   "JOIN gift_certificate_tag gct on gc.id = gct.gift_certificate_id " +
                   "JOIN tag t on gct.tag_id = t.id " +
                   "WHERE " +
                   "(gc.name LIKE concat('%', :namePart, '%') OR :namePart IS NULL) " +
                   "AND " +
                   "(gc.description LIKE concat('%', :description, '%') OR :description IS NULL)", nativeQuery = true)
    List<GiftCertificate> findCertificatesByPartOfNameOrDescription(@Param("namePart") String namePart,
                                                                    @Param("description") String description);

}
