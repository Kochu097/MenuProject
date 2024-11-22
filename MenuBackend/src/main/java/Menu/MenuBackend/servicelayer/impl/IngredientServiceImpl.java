package Menu.MenuBackend.servicelayer.impl;

import Menu.MenuBackend.common.exception.IngredientNotFoundException;
import Menu.MenuBackend.datalayer.DAO.MenuItemDAO;
import Menu.MenuBackend.datalayer.entity.MenuItem;
import Menu.MenuBackend.servicelayer.IngredientService;
import Menu.MenuBackend.servicelayer.dto.IngredientDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private MenuItemDAO menuItemDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> getAllIngredients() {
        return menuItemDAO.findAll()
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, IngredientDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientDTO getIngredientById(Integer id) {
        MenuItem menuItem = menuItemDAO.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));
        return modelMapper.map(menuItem, IngredientDTO.class);
    }

    @Override
    @Transactional
    public IngredientDTO createIngredient(IngredientDTO ingredientDTO) {
        MenuItem menuItem = modelMapper.map(ingredientDTO, MenuItem.class);
        MenuItem savedMenuItem = menuItemDAO.save(menuItem);
        return modelMapper.map(savedMenuItem, IngredientDTO.class);
    }

    @Override
    @Transactional
    public IngredientDTO updateIngredient(Integer id, IngredientDTO ingredientDTO) {
        MenuItem existingMenuItem = menuItemDAO.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));

        // Map DTO to existing entity while preserving the ID
        modelMapper.map(ingredientDTO, existingMenuItem);
        existingMenuItem.setId(id);

        MenuItem updatedMenuItem = menuItemDAO.save(existingMenuItem);
        return modelMapper.map(updatedMenuItem, IngredientDTO.class);
    }

    @Override
    @Transactional
    public void deleteIngredient(Integer id) {
        MenuItem menuItem = menuItemDAO.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));
        menuItemDAO.delete(menuItem);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> getIngredientsByMenuId(Integer menuId) {
        return menuItemDAO.findByMenuId(menuId)
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, IngredientDTO.class))
                .toList();
    }
}