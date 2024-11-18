package Menu.MenuBackend.servicelayer.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MenuDTO {
    private LocalDate day;
    private UserDTO user;
}
