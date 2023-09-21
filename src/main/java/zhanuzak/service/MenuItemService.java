package zhanuzak.service;

import zhanuzak.dto.request.MenuItemRequest;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.dto.response.SimpleResponse;

import java.util.List;

public interface MenuItemService {
    List<MenuItemResponse> getAll();

    MenuItemResponse getById(Long id);

    SimpleResponse save(MenuItemRequest menuItemRequest, Long id);

    SimpleResponse update(Long id, MenuItemRequest menuItemRequest);

    SimpleResponse delete(Long id);

}
