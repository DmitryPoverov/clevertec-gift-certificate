package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.GiftCertificate;

@Repository
public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Long>,
                                                   PagingAndSortingRepository<GiftCertificate, Long> {

    boolean existsByName(String name);
}
