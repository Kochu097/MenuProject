package Menu.MenuBackend.servicelayer.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Integer id;
    private String firebaseUserId;
    private List<MenuDTO> menus;
}