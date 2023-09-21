package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.CategoryRequest;
import zhanuzak.dto.response.CategoryResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Category;
import zhanuzak.repository.CategoryRepository;
import zhanuzak.service.CategoryService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryResponse> getAll() {
        return categoryRepository.getAll();
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
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new NotFoundException("" +
                "Category with id:" + id + " not found !!!"));
        CategoryResponse categoryResponse=new CategoryResponse(category);
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
                .message("Successfully updated Category with id:"+id)
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        categoryRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Category with id:"+id+" successfully deleted ☺")
                .build();
    }
}
