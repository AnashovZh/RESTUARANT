package zhanuzak.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "cheques")
@NoArgsConstructor
@AllArgsConstructor
public class Cheque {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cheque_seq")
    @SequenceGenerator(name = "cheque_seq",allocationSize = 1)
    private Long id;
    @Column(name = "price_average")
    private BigDecimal priceAverage;
    @Column(name = "created_at")
    private LocalDate createdAt;
    @ManyToOne
    private User user;
    @Column(name = "menu_items")
    @ManyToMany(mappedBy = "cheques")
    private List<MenuItem> menuItems;



}