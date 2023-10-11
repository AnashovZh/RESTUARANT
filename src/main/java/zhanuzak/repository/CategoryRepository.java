package zhanuzak.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.dto.response.SubCategoryResponse;
import zhanuzak.models.Category;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    //    @Query("select new zhanuzak.dto.response.CategoryResponse(c.id,c.name,s.id,s.name)from Category c join SubCategory  s on s.category.id=c.id")
//    List<CategoryResponse> getAll();
//    private Long id;
//    private String name;
//    private String image;
//    private BigDecimal price;
//    private String description;
//    private boolean isVegetarian;
    @Query("select c.id, c.name, s.id, s.name ,m.id,m.name,m.image,m.price,m.description," +
            "m.isVegetarian from Category c join c.subCategories s join MenuItem m on" +
            " m.subCategory.id=s.id")
    List<Object[]> getAll();

    @Query("select new zhanuzak.dto.response.SubCategoryResponse(sc.id,sc.name)from Category c" +
            " join c.subCategories sc where c.id=?1 ")
    List<SubCategoryResponse> findSubCategoriesById(Long id);
}
