package menu.menubackend.servicelayer.impl;

import menu.menubackend.common.exception.MenuNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import menu.menubackend.datalayer.dao.MenuDAO;
import menu.menubackend.datalayer.entity.Menu;
import menu.menubackend.datalayer.entity.User;
import menu.menubackend.servicelayer.MenuService;
import menu.menubackend.servicelayer.dto.MenuDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private static final String MENU_NOT_FOUND_ERROR_MESSAGE = "Menu not found with id: ";

    @Autowired
    private MenuDAO menuDAO;

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
}
