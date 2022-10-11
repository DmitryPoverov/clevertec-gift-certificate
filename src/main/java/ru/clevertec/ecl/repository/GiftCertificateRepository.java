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

    @Query(value = "FROM GiftCertificate gc " +
                   "JOIN gc.tags t " +
                   "WHERE (gc.name LIKE CONCAT('%', :namePart, '%') OR :namePart IS null) " +
                   "AND (gc.description LIKE CONCAT('%', :description, '%') or :description IS null)")
    List<GiftCertificate> findCertificatesByPartOfNameOrDescription(@Param("namePart") String namePart,
                                                                    @Param("description") String description);

/*    @Query(value = "SELECT gc.* " +
                   "FROM gift_certificate gc " +
                   "JOIN gift_certificate_tag gct on gc.id = gct.gift_certificate_id " +
                   "JOIN tag t on gct.tag_id = t.id " +
                   "WHERE " +
                   "(gc.name LIKE concat('%', ?1, '%') OR ?1 IS NULL) " +
                   "AND " +
                   "(gc.description LIKE concat('%', ?2, '%') OR ?2 IS NULL)", nativeQuery = true)
    List<GiftCertificate> findCertificatesByPartOfNameOrDescription(String namePart, String description);*/

}
