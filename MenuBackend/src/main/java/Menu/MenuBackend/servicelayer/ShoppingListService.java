package Menu.MenuBackend.servicelayer;

import Menu.MenuBackend.servicelayer.dto.ShoppingListDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;

import java.time.LocalDate;

public interface ShoppingListService {

    ShoppingListDTO generateShoppingList(LocalDate startDate, LocalDate endDate, UserDTO userDTO);
}
