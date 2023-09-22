package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.ChequeRequest;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.dto.response.MenuItemResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.BadRequest;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Cheque;
import zhanuzak.models.MenuItem;
import zhanuzak.models.Restaurant;
import zhanuzak.models.User;
import zhanuzak.repository.ChequeRepository;
import zhanuzak.repository.MenuItemRepository;
import zhanuzak.repository.RestaurantRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;


    @Override
    public List<ChequeResponse> getAll() {
        return null;
    }

    @Override
    public SimpleResponse save(ChequeRequest chequeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with emil:" + " not found !!!"));
        if (user.getRole().name().equalsIgnoreCase("ADMIN") ||
                user.getRole().name().equalsIgnoreCase("WAITER")) {
            Cheque cheque = new Cheque();
            cheque.setCreatedAt(LocalDate.now());
            List<MenuItem> menuItems = new ArrayList<>();
            for (String menuItemName : chequeRequest.getMenuItemNames()) {
                if (menuItemRepository.existsByName(menuItemName)) {
                    menuItems.add(menuItemRepository.findMenuItemByName(menuItemName));
                } else throw new NotFoundException("MenuItem with name :" + menuItemName + " not found !!!");
            }
            BigDecimal bigDecimal = BigDecimal.ZERO;
            for (MenuItem menuItem : menuItems) {
                bigDecimal = bigDecimal.add(menuItem.getPrice());
            }
            cheque.setPriceAverage(bigDecimal);
            cheque.setUser(user);
            user.setCheques(List.of(cheque));
            Cheque save = chequeRepository.save(cheque);
            for (MenuItem menuItem : menuItems) {
                 menuItem.getCheques().add(save);
                 menuItemRepository.save(menuItem);
            }
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("You (+" + user.getRole() + ") Cheque with id:" + save.getId() + " successfully saved☺")
                    .build();
        } else {
            throw new BadRequest("Sorry, you can't create a check.");
        }
    }

    @Override
    public ChequeResponse getById(Long id) {
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cheque with id:" + id + " not found !!!"));
        ChequeResponse chequeResponse = new ChequeResponse();
        chequeResponse.setId(cheque.getId());
        chequeResponse.setFullName(chequeRepository.findUserFullNameByChequeId(id).orElseThrow(() ->
                new NotFoundException("There is no person who created this check with id:" + id)));
        List<MenuItemResponse> menuItemsByChequeId = chequeRepository.findMenuItemsByChequeId(id);
        System.err.println("MenuItems:"+menuItemsByChequeId);
        System.out.println("menuItems:" + menuItemsByChequeId);
        chequeResponse.setMenuItemResponses(menuItemsByChequeId);
        chequeResponse.setPriceAverage(cheque.getPriceAverage());
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() ->
                new NotFoundException("Restaurant with id:" + "1L" + " not found !!!"));
        chequeResponse.setCreatedAt(cheque.getCreatedAt());
        chequeResponse.setService(restaurant.getService());
        BigDecimal service = restaurant.getService();
        BigDecimal priceAverage = cheque.getPriceAverage();
        BigDecimal percentAge = priceAverage.multiply(service.divide(new BigDecimal("100")));
        BigDecimal grantTotal = priceAverage.add(percentAge);
        chequeResponse.setGrandTotal(grantTotal);
        return chequeResponse;
    }
}
