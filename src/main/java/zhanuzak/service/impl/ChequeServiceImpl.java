package zhanuzak.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import zhanuzak.dto.request.ChequeRequest;
import zhanuzak.dto.response.ChequeResponse;
import zhanuzak.dto.response.SimpleResponse;
import zhanuzak.exceptions.exception.BadRequest;
import zhanuzak.exceptions.exception.NotFoundException;
import zhanuzak.models.Cheque;
import zhanuzak.models.MenuItem;
import zhanuzak.models.User;
import zhanuzak.repository.ChequeRepository;
import zhanuzak.repository.MenuItemRepository;
import zhanuzak.repository.UserRepository;
import zhanuzak.service.ChequeService;

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

    @Override
    public List<ChequeResponse> getAll() {
        return null;
    }

    @Override
    public SimpleResponse save(List<String> menuItemNames, ChequeRequest chequeRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.getUserByEmail(email).orElseThrow(() ->
                new NotFoundException("User with emil:" + " not found !!!"));
        if (user.getRole().name().equalsIgnoreCase("ADMIN") ||
                user.getRole().name().equalsIgnoreCase("WAITER")) {

            Cheque cheque = new Cheque();
            cheque.setCreatedAt(LocalDate.now());
            List<MenuItem> menuItems = new ArrayList<>();
            for (String menuItemName : menuItemNames) {
                menuItems.add(menuItemRepository.findMenuItemByName(menuItemName));
            }
            cheque.setMenuItems(menuItems);
            cheque.setUser(user);
            user.setCheques(List.of(cheque));
            cheque.setPriceAverage(chequeRequest.getPriceAverage());
            Cheque save = chequeRepository.save(cheque);
            return SimpleResponse.builder()
                    .httpStatus(HttpStatus.OK)
                    .message("Cheque with id:"+save.getId()+" successfully saved☺")
                    .build();
        }else {
            throw new BadRequest("Sorry, you can't create a check.");
        }
    }
}
