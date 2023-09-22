package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zhanuzak.models.MenuItem;

@Repository

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
//    @Query("select m from MenuItem m where m.name=?")
    MenuItem findMenuItemByName(String menuItemName);
//    @Query("select m from MenuItem m where m.name=?")
    boolean existsByName(String menuItemName);
}
