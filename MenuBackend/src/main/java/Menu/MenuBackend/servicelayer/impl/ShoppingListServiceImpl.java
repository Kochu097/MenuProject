package Menu.MenuBackend.servicelayer.impl;

import Menu.MenuBackend.servicelayer.MenuService;
import Menu.MenuBackend.servicelayer.ShoppingListService;
import Menu.MenuBackend.servicelayer.dto.MenuDTO;
import Menu.MenuBackend.servicelayer.dto.MenuItemDTO;
import Menu.MenuBackend.servicelayer.dto.ShoppingListDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShoppingListServiceImpl implements ShoppingListService {

    @Autowired
    private MenuService menuService;

    @Override
    public ShoppingListDTO generateShoppingList(LocalDate startDate, LocalDate endDate, UserDTO userDTO) {

        ShoppingListDTO shoppingList = new ShoppingListDTO();
        shoppingList.setStartDate(startDate);
        shoppingList.setEndDate(endDate);

        List<MenuDTO> menuList = menuService.getMenuForPeriod(startDate, endDate, userDTO);

        List<MenuItemDTO> menuItems = menuList.stream()
                .map(menuDTO -> menuDTO.getMenuItems()).flatMap(Collection::stream).toList();

        menuItems.forEach(shoppingList::addToShoppingList);


        return shoppingList;
    }
}
