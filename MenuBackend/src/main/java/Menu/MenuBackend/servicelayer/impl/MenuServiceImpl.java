package Menu.MenuBackend.servicelayer.impl;

import Menu.MenuBackend.common.exception.MenuNotFoundException;
import Menu.MenuBackend.common.exception.UserNotFoundException;
import Menu.MenuBackend.datalayer.DAO.UserDAO;
import Menu.MenuBackend.servicelayer.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;
import Menu.MenuBackend.datalayer.DAO.MenuDAO;
import Menu.MenuBackend.datalayer.entity.Menu;
import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.servicelayer.MenuService;
import Menu.MenuBackend.servicelayer.dto.MenuDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MenuServiceImpl implements MenuService {

    private static final String MENU_NOT_FOUND_ERROR_MESSAGE = "Menu not found with id: ";

    @Autowired
    private MenuDAO menuDAO;

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<MenuDTO> getAllMenus() {
        return menuDAO.findAll()
                .stream()
                .map(menu -> modelMapper.map(menu, MenuDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public MenuDTO getMenuById(Integer id) {
        Menu menu = menuDAO.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_ERROR_MESSAGE + id));
        return modelMapper.map(menu, MenuDTO.class);
    }

    @Override
    @Transactional
    public MenuDTO createMenu(MenuDTO menuDTO) {
        Menu menu = modelMapper.map(menuDTO, Menu.class);
        User user = menuDTO.getUser() != null ? modelMapper.map(menuDTO.getUser(), User.class) : null;
        menu.setUser(user);
        Menu savedMenu = menuDAO.save(menu);
        return modelMapper.map(savedMenu, MenuDTO.class);
    }

    @Override
    @Transactional
    public MenuDTO updateMenu(Integer id, MenuDTO menuDTO) {
        Menu existingMenu = menuDAO.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_ERROR_MESSAGE + id));

        existingMenu.setDay(menuDTO.getDay());
        User user = menuDTO.getUser() != null ? modelMapper.map(menuDTO.getUser(), User.class) : null;
        existingMenu.setUser(user);

        Menu updatedMenu = menuDAO.save(existingMenu);
        return modelMapper.map(updatedMenu, MenuDTO.class);
    }

    @Override
    @Transactional
    public void deleteMenu(Integer id) {
        Menu menu = menuDAO.findById(id)
                .orElseThrow(() -> new MenuNotFoundException(MENU_NOT_FOUND_ERROR_MESSAGE + id));
        menuDAO.delete(menu);
    }

    @Override
    public List<MenuDTO> getMenuForPeriod(LocalDate startDate, LocalDate endDate, UserDTO user) throws MenuNotFoundException {
        Optional<User> userEntity = userDAO.findById(1);
        if(userEntity.isEmpty()) throw new UserNotFoundException("User Not found");

        List<Menu> menu = menuDAO.getByPeriod(startDate, endDate, userEntity.get());
        return menu.stream().map( m -> modelMapper.map(m, MenuDTO.class)).toList();
    }
}
