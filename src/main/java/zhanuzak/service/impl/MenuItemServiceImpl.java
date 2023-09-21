package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.MenuItemRequest;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.MenuItem;
import zhanuzak.models.Restaurant;
import zhanuzak.models.SubCategory;
import zhanuzak.models.User;
import zhanuzak.repository.MenuItemRepository;
import zhanuzak.repository.SubCategoryRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.repository.dao.MenuItemJdbcTemplate;
import zhanuzak.service.MenuItemService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MenuItemServiceImpl implements MenuItemService {
    private final MenuItemRepository menuItemRepository;
    private final MenuItemJdbcTemplate menuItemJdbcTemplate;
    private final UserRepository userRepository;
    private final SubCategoryRepository subCategoryRepository;

    @Override
    public List<MenuItemResponse> getAll() {
        return menuItemJdbcTemplate.getAll();
    }

    @Override
    public MenuItemResponse getById(Long id) {
        return menuItemJdbcTemplate.getById(id).orElseThrow(()->
                new NotFoundException("MenuItemResponse with id:"+id+" not found !!!"));
    }

    @Override
    public SimpleResponse save(MenuItemRequest menuItemRequest, Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with emil:" + " not found !!!"));
        SubCategory subCategory = subCategoryRepository.findById(id).orElseThrow(() ->
                new NotFoundException("SubCategory with id:" + id + " not found!!!"));
        Restaurant restaurant = user.getRestaurant();
        MenuItem menuItem = MenuItem.buildStatic(menuItemRequest);
        restaurant.setMenuItems(List.of(menuItem));
        menuItem.setRestaurant(restaurant);
        subCategory.setMenuItems(List.of(menuItem));
        menuItem.setSubCategory(subCategory);
        menuItemRepository.save(menuItem);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem with name:"+menuItem.getName()+" successfully saved ")
                .build();
    }

    @Override
    public SimpleResponse update(Long id, MenuItemRequest menuItemRequest) {
        MenuItem menuItem = menuItemRepository.findById(id).orElseThrow(() ->
                new NotFoundException("MenuItem with id:" + id + " not found !!!"));
        MenuItem menuItemUpdate = menuItem.build(menuItemRequest);
        MenuItem save = menuItemRepository.save(menuItemUpdate);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem with name:"+save.getName()+" successfully updated ☺ ")
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        menuItemRepository.deleteById(id);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("MenuItem with name:"+id+" successfully deleted ☺ ")
                .build();
    }
}
