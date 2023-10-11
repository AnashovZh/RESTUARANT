package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.models.MenuItem;

import java.util.List;
import java.util.Optional;

@Repository

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
    Optional<MenuItem> findMenuItemByName(String menuItemName);

    boolean existsByName(String menuItemName);

    @Query("select new zhanuzak.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem m order by m.price asc ")
    List<MenuItemResponse> getAllAscAboutPrice();

    @Query("select new zhanuzak.dto.response.MenuItemResponse(m.id,m.name,m.image,m.price,m.description,m.isVegetarian)from MenuItem m order by m.price desc ")
    List<MenuItemResponse> getAllDescAboutPrice();

    @Query("select new zhanuzak.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description," +
            " m.isVegetarian, r.name, s.name) " +
            "from MenuItem m " +
            "join SubCategory s  on s.id = m.subCategory.id " +
            "join Restaurant r on r.id = m.restaurant.id " +
            "where m.name ilike concat(:search, '%') " +
            "   or m.name ilike concat('%', :search, '%') " +
            "   or s.name ilike concat('%', :search, '%') ")
    List<MenuItemResponse> searchMenuItemByName(@Param("search") String search);

    @Query("select new zhanuzak.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from MenuItem m where m.isVegetarian=true order by m.name asc ")
    List<MenuItemResponse> getAllByIsVegetarians(String vegetarian);

    @Query("select new zhanuzak.dto.response.MenuItemResponse(m.id, m.name, m.image, m.price, m.description, m.isVegetarian) from MenuItem m where m.isVegetarian=false order by m.name asc ")
    List<MenuItemResponse> getAllByNotIsVegetarians(String vegetarian);
}
