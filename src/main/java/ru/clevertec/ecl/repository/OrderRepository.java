package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.entities.Order;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "FROM Order o " +
                   "WHERE o.id = :orderId " +
                   "AND o.user.id = :userId")
    Optional<Order> findOrderByUserIdAndByOrderId(@Param(value = "userId") long userId,
                                                  @Param(value = "orderId") long orderId);

    @Query(value = "FROM Order o " +
                   "WHERE o.user.nickName = :nickName")
    Page<Order> findAllOrdersByUserNickName(String nickName, Pageable pageable);

/*    @Query("FROM Order o " +
           "JOIN User u ON o.user.id = u.id " +
           "WHERE u.id = :userId " +
           "ORDER BY o.id")*/
    List<Order> findAllByUserId(long id, Pageable pageable);
}
