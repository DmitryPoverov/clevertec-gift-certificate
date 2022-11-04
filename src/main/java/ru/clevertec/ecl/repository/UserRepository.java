package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>,
                                        PagingAndSortingRepository<User, Long> {

    Optional<User> findUserByNickName(String nickName);
}
