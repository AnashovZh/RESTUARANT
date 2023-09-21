package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.CategoryResponse;
import zhanuzak.models.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query("select new zhanuzak.dto.response.CategoryResponse(c)from Category c")
    List<CategoryResponse> getAll();
}
