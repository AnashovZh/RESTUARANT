package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.models.Cheque;
import zhanuzak.models.MenuItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChequeRepository extends JpaRepository<Cheque, Long> {


    @Query(nativeQuery = true, value = "SELECT m.id, m.name, m.image, m.price, m.description, m.is_vegetarian " +
            "FROM menu_items_cheques AS cm " +
            "JOIN menu_items AS m ON cm.menu_items_id = m.id ")
    List<ChequeResponse> getAll();

    @Query("select concat(u.firstName,' ',u.lastName) from User u join Cheque c on c.user.id=u.id where c.id=?1")
    Optional<String> findUserFullNameByChequeId(Long id);

    @Query(nativeQuery = true, value = "SELECT m.id, m.name, m.image, m.price, m.description, m.is_vegetarian " +
            "FROM menu_items_cheques AS cm " +
            "JOIN menu_items AS m ON cm.menu_items_id = m.id " +
            "WHERE cm.cheques_id = ?")
    List<Object[]> findMenuItemsByChequeIdQuery(Long id);

    default List<MenuItemResponse> findMenuItemResponsesByChequeId(Long id) {
        List<Object[]> result = findMenuItemsByChequeIdQuery(id);
        List<MenuItemResponse> menuItemResponses = new ArrayList<>();
        for (Object[] row : result) {
            menuItemResponses.add(new MenuItemResponse(
                    ((Long) row[0]),
                    (String) row[1],
                    (String) row[2],
                    (BigDecimal) row[3],
                    (String) row[4],
                    (Boolean) row[5]
            ));
        }
        return menuItemResponses;
    }

    default List<MenuItem> findMenuItemsByChequeId(Long id) {
        List<Object[]> result = findMenuItemsByChequeIdQuery(id);
        List<MenuItem> menuItems = new ArrayList<>();
        for (Object[] objects : result) {
            menuItems.add(new MenuItem(
                    (Long) objects[0],
                    (String) objects[1],
                    (String) objects[2],
                    (BigDecimal) objects[3],
                    (String) objects[4],
                    (Boolean) objects[5]));
        }
        return menuItems;
    }

    @Modifying
    @Query(value = "delete from menu_items_cheques where cheques_id = ?1", nativeQuery = true)
    void deleteCheque123(Long checkId);

//    @Query(nativeQuery = true, value = "SELECT m.id, m.name, m.image, m.price, m.description, m.is_vegetarian " +
//            "FROM menu_items_cheques AS cm " +
//            "JOIN menu_items AS m ON cm.menu_items_id = m.id join cheques ch join users u on ch.user_id=u.id where u.email=?1 ")
//
//        this.id = id;
//        this.fullName = fullName;
//        this.menuItemResponses = menuItemResponses;
//        this.createdAt = createdAt;
//        this.priceAverage = priceAverage;


//    this.id = id;
//        this.name = name;
//        this.image = image;
//        this.price = price;
//        this.description = description;
//        this.isVegetarian = isVegetarian;

// @Query("select new zhanuzak.dto.response.ChequeResponse(c.id,concat(u.firstName,u.lastName)" +
//      ",m.id,m.name,m.price,m.description,m.isVegetarian,c.createdAt,c.priceAverage )from " +
//       "Cheque c join User u on c.user.id=u.id join c.menuItems m on c.menuItems= m where u.email=?1 ")
//    @Query(nativeQuery = true,value = "SELECT m.id, m.name, m.image, m.price, m.description, m.is_vegetarian\n" +
//            "FROM menu_items_cheques AS cm\n" +
//            "JOIN menu_items AS m ON cm.menu_items_id = m.id\n" +
//            "JOIN cheques AS ch ON cm.cheque_id = ch.id\n" +
//            "JOIN users AS u ON ch.user_id = u.id\n" +
//            "WHERE u.email = ?1\n")

    //    public class ChequeResponse {
//        private Long id;
//        private String fullName;
//        private List<MenuItemResponse> menuItemResponses;
//        private LocalDate createdAt;
//        private BigDecimal priceAverage;
//        private BigDecimal service;
    @Query("select  (c.id,concat(u.firstName,' ',u.lastName) ," +
            "m.id,m.name,m.image,m.price,m.description,m.isVegetarian,c.createdAt,c.priceAverage)" +
            "from Cheque c join c.menuItems m join" +
            " User u on c.user.id=u.id where u.email=?1")
    List<Object[]> getAllChequeByUserEmail(String email);

    @Query("select sum(c.priceAverage) from Cheque  c where c.user.email=?1")
    BigDecimal grandTotal(String email);

//    @Query("select ")
//    List<Object[]> getAllChequesByUserEmail2(String email);

//        private Long id;
//    private String fullName;
//    private List<MenuItemResponse> menuItemResponses;
//    private LocalDate createdAt;
//    private BigDecimal priceAverage;
//    private BigDecimal service;
//    private BigDecimal grandTotal;
//    @Query("select new zhanuzak.dto.response.ChequeResponse(c.id,concat(u.firstName,' ',u.lastName),c.createdAt,c.priceAverage) from " +
//            "Cheque c join c.menuItems cm  User u on c.user.id=u.id where u.email=?1")
//    List<ChequeResponse> getAllCheques(String email);
}