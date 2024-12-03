package Menu.MenuBackend.servicelayer.impl;

import Menu.MenuBackend.common.exception.IngredientNotFoundException;
import Menu.MenuBackend.datalayer.DAO.IngredientDAO;
import Menu.MenuBackend.datalayer.entity.Ingredient;
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
    private IngredientDAO ingredientDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<IngredientDTO> getAllIngredients() {
        return ingredientDAO.findAll()
                .stream()
                .map(menuItem -> modelMapper.map(menuItem, IngredientDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public IngredientDTO getIngredientById(Integer id) {
        Ingredient ingredient = ingredientDAO.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));
        return modelMapper.map(ingredient, IngredientDTO.class);
    }

    @Override
    @Transactional
    public IngredientDTO createIngredient(IngredientDTO ingredientDTO) {
        Ingredient ingredient = modelMapper.map(ingredientDTO, Ingredient.class);
        Ingredient savedIngredient = ingredientDAO.save(ingredient);
        return modelMapper.map(savedIngredient, IngredientDTO.class);
    }

    @Override
    @Transactional
    public IngredientDTO updateIngredient(Integer id, IngredientDTO ingredientDTO) {
        Ingredient existingIngredient = ingredientDAO.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));

        // Map DTO to existing entity while preserving the ID
        modelMapper.map(ingredientDTO, existingIngredient);
        existingIngredient.setId(id);

        Ingredient updatedIngredient = ingredientDAO.save(existingIngredient);
        return modelMapper.map(updatedIngredient, IngredientDTO.class);
    }

    @Override
    @Transactional
    public void deleteIngredient(Integer id) {
        Ingredient ingredient = ingredientDAO.findById(id)
                .orElseThrow(() -> new IngredientNotFoundException("Ingredient not found with id: " + id));
        ingredientDAO.delete(ingredient);
    }
}