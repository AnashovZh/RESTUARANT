package zhanuzak.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "menu_items")
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "menu_item_seq")
    @SequenceGenerator(name = "menu_item_seq",allocationSize = 1)
    private Long id;
    private String name;
    private String image;
    private BigDecimal price;
    private String description;
    @Column(name = "is_vegetarian")
    private boolean isVegetarian;
    @ManyToOne
    private Restaurant restaurant;
    @ManyToMany
    private List<Cheque> cheques;
    @ManyToOne
    private SubCategory subCategory;
    @OneToOne(mappedBy = "menuItem")
    private StopList stopList;



}