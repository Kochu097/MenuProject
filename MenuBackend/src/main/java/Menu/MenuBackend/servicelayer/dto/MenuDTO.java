package Menu.MenuBackend.servicelayer.dto;

import Menu.MenuBackend.datalayer.entity.MenuItem;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class MenuDTO {
    private Integer id;
    private LocalDate day;
    private List<MenuItemDTO> menuItems;
    private UserDTO user;
}
