package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.CategoryRequest;
import zhanuzak.dto.response.CategoryResponse;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.SubCategoryResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Category;
import zhanuzak.models.MenuItem;
import zhanuzak.repository.CategoryRepository;
import zhanuzak.repository.MenuItemRepository;
import zhanuzak.service.CategoryService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final MenuItemRepository menuItemRepository;

    //
//    @Override
//    public List<CategoryResponse> getAll() {
//        List<Object[]> all = categoryRepository.getAll();
//    }
    @Override
    public List<CategoryResponse> getAll() {
        List<Object[]> all = categoryRepository.getAll();
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        for (Object[] row : all) {
            Long categoryId = (Long) row[0];
            String categoryName = (String) row[1];
            Long subCategoryId = (Long) row[2];
            String subCategoryName = (String) row[3];
            Long menuItemId = (Long) row[4];
            String menuItemName = (String) row[5];
            String menuitemImage = (String) row[6];
            BigDecimal menuItemPrice = (BigDecimal) row[7];
            String menuItemDescription = (String) row[8];
            boolean menuItemIsVegetarian = (boolean) row[9];
            CategoryResponse categoryResponse = categoryResponses.stream()
                    .filter(cr -> cr.getId().equals(categoryId))
                    .findFirst()
                    .orElse(null);
            if (categoryResponse == null) {
                categoryResponse = new CategoryResponse(categoryId, categoryName, new ArrayList<>());
                categoryResponses.add(categoryResponse);
            }
            SubCategoryResponse subCategoryResponse = new SubCategoryResponse(subCategoryId, subCategoryName);
            categoryResponse.getSubCategoryResponses().add(subCategoryResponse);
            MenuItemResponse menuItemResponse = new MenuItemResponse(menuItemId,menuItemName,menuitemImage,
                    menuItemPrice,menuItemDescription,menuItemIsVegetarian);
            List<MenuItemResponse> menuItemResponses = subCategoryResponse.getMenuItemResponses();
            if (menuItemResponses == null) {
                menuItemResponses = new ArrayList<>();
                subCategoryResponse.setMenuItemResponses(menuItemResponses);
            }
            menuItemResponses.add(menuItemResponse);
        }
        return categoryResponses;
    }


    @Override
    public SimpleResponse save(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.name());
        Category save = categoryRepository.save(category);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with name:" + save.getName() + " successfully saved☺")
                .build();
    }

    @Override
    public CategoryResponse getByIdWithSubCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("" +
                "Category with id:" + id + " not found !!!"));
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setSubCategoryResponses(categoryRepository.findSubCategoriesById(id));
        return categoryResponse;
    }

    @Override
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("" +
                "Category with id:" + id + " not found !!!"));
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        categoryResponse.setSubCategoryResponses(categoryRepository.findSubCategoriesById(id));
        return categoryResponse;
    }

    @Override
    public SimpleResponse update(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("" +
                "Category with id:" + id + " not found !!!"));
        category.setName(categoryRequest.name());
        categoryRepository.save(category);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated Category with id:" + id)
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        categoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with id:" + id + " successfully deleted ☺")
                .build();
    }
}
