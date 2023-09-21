package zhanuzak.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.CascadeType.PERSIST;

@Getter
@Setter
@Entity
@Table(name = "sub_categories")
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sub_category_seq")
    @SequenceGenerator(name = "sub_category_seq")
    private Long id;
    private String name;
    @OneToMany(mappedBy = "subCategory",cascade = {MERGE,REFRESH,DETACH,PERSIST})
    private List<MenuItem>menuItems;
    @ManyToOne(cascade = {MERGE,REFRESH,DETACH,PERSIST})
    private Category category;

}