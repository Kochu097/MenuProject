package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.servicelayer.MenuService;
import Menu.MenuBackend.servicelayer.dto.MenuDTO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuRestController {

    @Autowired
    MenuService menuService;

    @GetMapping("/test")
    public String test() {
        return "test123";
    }

    @GetMapping("/getMenuForPeriod")
    public List<MenuDTO> getMenuForPeriod(@RequestParam
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate startDate,
                                          @RequestParam
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate endDate) {
        UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return menuService.getMenuForPeriod(startDate, endDate, user);
    }

}
