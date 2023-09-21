package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.SubCategoryResponse;
import zhanuzak.models.SubCategory;

import java.util.List;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory,Long> {
    @Query("select new zhanuzak.dto.response.SubCategoryResponse(s.id,s.name)from SubCategory s")
    List<SubCategoryResponse>getAll();
}
