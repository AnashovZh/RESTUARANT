package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.StopListRequest;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.dto.response.StopListResponse;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.MenuItem;
import zhanuzak.models.StopList;
import zhanuzak.repository.MenuItemRepository;
import zhanuzak.repository.StopListRepository;
import zhanuzak.service.StopListService;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@Service
@RequiredArgsConstructor
@Transactional
public class StopListServiceImpl implements StopListService {
    private final StopListRepository stopListRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public List<StopListResponse> getAll() {
        return stopListRepository.getAll();
    }

    @Override
    public SimpleResponse save(StopListRequest stopListRequest) {
        StopList stopList = new StopList();
        stopList.setReason(stopListRequest.reason());
        stopList.setDate(stopListRequest.date());
        String menuItemName = stopListRequest.menuItemName();
        MenuItem menuItem = menuItemRepository.findMenuItemByName(menuItemName).orElseThrow(() ->
                new NotFoundException("MenuItem with name :" + stopListRequest.menuItemName() + " not found !!!"));
        stopList.setMenuItem(menuItem);
        menuItem.setStopList(stopList);
        StopList save = stopListRepository.save(stopList);
        return SimpleResponse.builder()
                .httpStatus(OK)
                .message("StopList with id :" + save.getId() + " successfully saved ☺")
                .build();
    }
}
