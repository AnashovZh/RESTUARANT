package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import zhanuzak.repository.dao.ChequeJdbcTemplate;
import zhanuzak.service.ChequeService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ChequeServiceImpl implements ChequeService {
    private final ChequeRepository chequeRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;
    private final ChequeJdbcTemplate chequeJdbcTemplate;


    @Override
    public List<ChequeResponse> getAll() {
        return chequeRepository.getAll();
    }

    @Override
    public List<ChequeResponse> getAllChequesByUserEmail2(String email) {
//        log.info("start");
//        List<ChequeResponse> allCheques = chequeRepository.getAllCheques(email);
//        log.info("finish");
//        return allCheques;
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        List<Cheque> cheques = user.getCheques();
        String fullName = user.getFirstName()+" " +user.getLastName();
        for (Cheque cheque : cheques) {
            ChequeResponse chequeResponse = new ChequeResponse();
            chequeResponse.setId(cheque.getId());
            chequeResponse.setFullName(fullName);
            chequeResponse.setCreatedAt(cheque.getCreatedAt());
            chequeResponse.setPriceAverage(cheque.getPriceAverage());
            List<MenuItem> menuItems = cheque.getMenuItems();
            List<MenuItemResponse> menuItemResponses = new ArrayList<>();
            for (MenuItem menuItem : menuItems) {
                MenuItemResponse menuItemResponse = new MenuItemResponse();
                menuItemResponse.setId(menuItem.getId());
                menuItemResponse.setName(menuItem.getName());
                menuItemResponse.setImage(menuItem.getImage());
                menuItemResponse.setPrice(menuItem.getPrice());
                menuItemResponse.setDescription(menuItem.getDescription());
                menuItemResponse.setVegetarian(menuItem.isVegetarian());
                menuItemResponses.add(menuItemResponse);
            }
            chequeResponse.setMenuItemResponses(menuItemResponses);
            chequeResponse.setService(user.getRestaurant().getService());
            chequeResponse.setGrandTotal(chequeRepository.grandTotal(email));
            chequeResponses.add(chequeResponse);
        }


        return chequeResponses;
    }

    @Override
    public List<ChequeResponse> getAllChequeByUserEmail(String email) {
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with email:" + email + " not found !!!"));
        List<ChequeResponse> chequeResponses = new ArrayList<>();
        List<Object[]> all = chequeRepository.getAllChequeByUserEmail(email);
        System.err.println(all);
        ChequeResponse chequeResponse = new ChequeResponse();
        for (Object[] row : all) {
            Long id = (Long) row[0];
            String fullName = (String) row[1];
            Long menuItemId = (Long) row[2];
            String menuItemName = (String) row[3];
            String menuItemImage = (String) row[4];
            BigDecimal price = (BigDecimal) row[5];
            String description = (String) row[6];
            boolean isVegetarian = (boolean) row[7];
            LocalDate createdDate = (LocalDate) row[8];
            BigDecimal priceAverage = (BigDecimal) row[9];
            chequeResponse.setId(id);
            chequeResponse.setFullName(fullName);
            chequeResponse.setMenuItemResponses(List.of(MenuItemResponse.builder()
                    .id(menuItemId)
                    .name(menuItemName)
                    .image(menuItemImage)
                    .price(price)
                    .description(description)
                    .isVegetarian(isVegetarian)
                    .build()));
            chequeResponse.setCreatedAt(createdDate);
            chequeResponse.setPriceAverage(priceAverage);
        }
        chequeResponses.add(chequeResponse);
        return chequeResponses;
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
                    menuItems.add(menuItemRepository.findMenuItemByName(menuItemName).orElseThrow(() ->
                            new NotFoundException("MenuItem with name:" + menuItemName + " not found !!!")));
                } else throw new NotFoundException("MenuItem with name :" + menuItemName + " not found !!!");
            }
            for (MenuItem menuItem : menuItems) {
                if (menuItem.getStopList() == null) {
                    for (MenuItem menuItem1 : menuItems) {
                        if (menuItem1.getCheques() == null) {
                            menuItem1.setCheques(Arrays.asList(cheque));
                        } else {
                            menuItem1.getCheques().add(cheque);
                        }
                    }
                } else {
                    throw new BadRequest("It is MenuItem in StopList menuItemName:" + menuItem.getName());
                }
            }
            System.err.println(menuItems);
            BigDecimal bigDecimal = BigDecimal.ZERO;
            for (MenuItem menuItem : menuItems) {
                bigDecimal = bigDecimal.add(menuItem.getPrice());
            }
            System.err.println(bigDecimal);
            cheque.setPriceAverage(bigDecimal);
            cheque.setUser(user);
            user.setCheques(List.of(cheque));
            cheque.setMenuItems(menuItems);
            chequeRepository.save(cheque);

            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("You (+" + user.getRole() + ") Cheque with id:" +
                            cheque.getId() + " successfully saved☺")
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
        List<MenuItemResponse> menuItemsByChequeId = chequeRepository.findMenuItemResponsesByChequeId(id);
        chequeResponse.setMenuItemResponses(menuItemsByChequeId);
        chequeResponse.setPriceAverage(cheque.getPriceAverage());
        Restaurant restaurant = restaurantRepository.findById(1L).orElseThrow(() ->
                new NotFoundException("Restaurant with id:" + "1L" + " not found !!!"));
        chequeResponse.setCreatedAt(cheque.getCreatedAt());
        chequeResponse.setService(restaurant.getService());
        BigDecimal service = restaurant.getService();
        if (service != null) {
            BigDecimal priceAverage = cheque.getPriceAverage();
            BigDecimal percentAge = priceAverage.multiply(service.divide(new BigDecimal("100")));
            BigDecimal grantTotal = priceAverage.add(percentAge);
            chequeResponse.setGrandTotal(grantTotal);
        } else throw new NotFoundException("Service not found !!");
        return chequeResponse;
    }

    @Override
    public SimpleResponse update(Long id, ChequeRequest chequeRequest) {
        chequeRepository.deleteCheque123(id);
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cheque with id:" + id + " not found !!!"));

        List<MenuItem> menuItems = new ArrayList<>();
        for (String menuItemName : chequeRequest.getMenuItemNames()) {
            if (menuItemRepository.existsByName(menuItemName)) {
                menuItems.add(menuItemRepository.findMenuItemByName(menuItemName).orElseThrow(() ->
                        new NotFoundException("MenuItem with name:" + menuItemName + " not found !!!")));
            } else throw new NotFoundException("MenuItem with name :" + menuItemName + " not found !!!");
        }

        BigDecimal bigDecimal = BigDecimal.ZERO;
        for (MenuItem menuItem : menuItems) {
            bigDecimal = bigDecimal.add(menuItem.getPrice());
        }
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getCheques() == null) {
                menuItem.setCheques(Arrays.asList(cheque));
            } else {
                menuItem.getCheques().add(cheque);
            }
        }
        cheque.setMenuItems(menuItems);
        cheque.setPriceAverage(bigDecimal);
        Cheque save = chequeRepository.save(cheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully updated cheque with id:" + save.getId())
                .build();
    }

    @Override
    public SimpleResponse delete(Long id) {
        System.out.println(id);
        Cheque cheque = chequeRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Cheque with id:" + id + " not found !!!"));
        chequeRepository.deleteCheque123(cheque.getId());
        chequeRepository.delete(cheque);
        return SimpleResponse.builder()
                .httpStatus(HttpStatus.OK)
                .message("Successfully deleted cheque with id:" + id)
                .build();
    }
}
