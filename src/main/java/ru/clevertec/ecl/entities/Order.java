package ru.clevertec.ecl.entities;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"user", "certificate"})
@EqualsAndHashCode(exclude = {"user", "certificate"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal price;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @Column(name ="create_date")
    private LocalDateTime createDate;

    @ManyToOne
    private User user;

    @ManyToOne
    private GiftCertificate certificate;
}
