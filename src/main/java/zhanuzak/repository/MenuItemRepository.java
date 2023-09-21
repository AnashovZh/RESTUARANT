package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import zhanuzak.models.MenuItem;
@Repository

public interface MenuItemRepository extends JpaRepository<MenuItem,Long> {

    MenuItem findMenuItemByName(String menuItemName);
}
