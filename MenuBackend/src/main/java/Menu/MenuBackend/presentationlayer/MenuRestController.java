package Menu.MenuBackend.presentationlayer;

import Menu.MenuBackend.datalayer.entity.MenuItem;
import Menu.MenuBackend.servicelayer.MenuService;
import Menu.MenuBackend.servicelayer.ProductService;
import Menu.MenuBackend.servicelayer.RecipeService;
import Menu.MenuBackend.servicelayer.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MenuRestController {

    @Autowired
    MenuService menuService;

    @Autowired
    RecipeService recipeService;

    @Autowired
    ProductService productService;

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

    @GetMapping("/getRecipes")
    public List<RecipeDTO> getRecipes() {
        return recipeService.getAllRecipes();
    }

    @GetMapping("/getProducts")
    public List<ProductDTO> getProducts() {
        UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return productService.getAllProductsForUser(user);
    }

    @PostMapping("/addMenuItem")
    public void addMenuItem(@RequestParam
                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                   LocalDate date,
                               @RequestBody
                               MenuItemDTO item) {
        UserDTO user = (UserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        menuService.addMenuItem(date, item, user);
    }

}
