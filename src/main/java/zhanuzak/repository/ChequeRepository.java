package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.models.Cheque;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {


    @Query("select new zhanuzak.dto.response.ChequeResponse(c.id,c.createdAt,c.priceAverage)from Cheque c")
    List<ChequeResponse> getAll();

    @Query("select concat(u.firstName,' ',u.lastName) from User u join Cheque c on c.user.id=u.id where c.id=?1")
    Optional<String> findUserFullNameByChequeId(Long id);
//        private Long id;
//    private String name;
//    private String image;
//    private BigDecimal price;
//    private String description;
//    private boolean isVegetarian;
//@Query("select new zhanuzak.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from Cheque c join c.menuItems  m where c.id=?1")

//@Query(nativeQuery = true,value = "SELECT m.id, m.name, m.image, m.price, m.description, m.is_vegetarian\n" +
//        "FROM menu_items_cheques AS cm\n" +
//        "JOIN menu_items AS m ON cm.menu_items_id = m.id\n" +
//        "WHERE cm.cheques_id = ?;\n")
//    List<MenuItemResponse> findMenuItemsByChequeId(Long id);

    @Query(nativeQuery = true, value = "SELECT m.id, m.name, m.image, m.price, m.description, m.is_vegetarian " +
            "FROM menu_items_cheques AS cm " +
            "JOIN menu_items AS m ON cm.menu_items_id = m.id " +
            "WHERE cm.cheques_id = ?")
    List<Object[]> findMenuItemsByChequeIdQuery(Long id);
    default List<MenuItemResponse> findMenuItemsByChequeId(Long id) {
        List<Object[]> result = findMenuItemsByChequeIdQuery(id);
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        for (Object[] row : result) {
            menuItemResponses.add(new MenuItemResponse(
                    ((Long) row[0]), // id
                    (String) row[1], // name
                    (String) row[2], // image
                    (BigDecimal) row[3], // price
                    (String) row[4], // description
                    (Boolean) row[5] // isVegetarian
            ));
        }
        return menuItemResponses;
    }

//    List<MenuItem> getMenuItemsByUserId(Long id);
}
