package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.models.Cheque;
import zhanuzak.models.MenuItem;

import java.util.List;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {

    @Query("select new zhanuzak.dto.response.ChequeResponse(c.id,c.priceAverage,c.createdAt)from Cheque c")
    List<ChequeResponse> getAll();
//  private Long id;
//    private String name;
//    private String image;
//    private BigDecimal price;
//    private String description;
//    @Column(name = "is_vegetarian")
//    private boolean isVegetarian;
    @Query("select ")
    List<MenuItem> getMenuItemsByUserId(Long id);
}
