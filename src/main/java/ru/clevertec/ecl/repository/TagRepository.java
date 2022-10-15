package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>,
                                       PagingAndSortingRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    boolean existsByName(String string);

}
