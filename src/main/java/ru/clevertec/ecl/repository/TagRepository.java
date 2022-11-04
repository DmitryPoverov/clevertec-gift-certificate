package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>,
                                       PagingAndSortingRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    boolean existsByName(String string);

    @Query(value = "SELECT t.id, " +
           "               t.name " +
           "FROM orders o " +
           "         JOIN gift_certificate gc ON o.certificate_id = gc.id " +
           "         JOIN gift_certificate_tag gct ON gc.id = gct.gift_certificate_id " +
           "         JOIN tag t ON gct.tag_id = t.id " +
           "         JOIN users u ON o.user_id = u.id " +
           "GROUP BY t.id, t.name, u.user_name " +
           "ORDER BY count(*) DESC " +
           "LIMIT 1;", nativeQuery = true)
    Optional<Tag> findMostWidelyUsedTagWithHighestCost();

}
