package Menu.MenuBackend.servicelayer.impl;

import Menu.MenuBackend.common.exception.RecipeNotFoundException;
import Menu.MenuBackend.datalayer.dao.RecipeDAO;
import Menu.MenuBackend.datalayer.entity.Recipe;
import Menu.MenuBackend.datalayer.entity.User;
import Menu.MenuBackend.servicelayer.RecipeService;
import Menu.MenuBackend.servicelayer.dto.RecipeDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RecipeServiceImpl implements RecipeService {

    @Autowired
    private RecipeDAO recipeDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional(readOnly = true)
    public List<RecipeDTO> getAllRecipes() {
        return recipeDAO.findAll()
                .stream()
                .map(recipe -> modelMapper.map(recipe, RecipeDTO.class))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public RecipeDTO getRecipeById(Integer id) {
        Recipe recipe = recipeDAO.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
        return modelMapper.map(recipe, RecipeDTO.class);
    }

    @Override
    @Transactional
    public RecipeDTO createRecipe(RecipeDTO recipeDTO) {
        Recipe recipe = modelMapper.map(recipeDTO, Recipe.class);
        User user = recipeDTO.getUser() != null ? modelMapper.map(recipeDTO.getUser(), User.class) : null;
        recipe.setUser(user);
        Recipe savedRecipe = recipeDAO.save(recipe);
        return modelMapper.map(savedRecipe, RecipeDTO.class);
    }

    @Override
    @Transactional
    public RecipeDTO updateRecipe(Integer id, RecipeDTO recipeDTO) {
        Recipe existingRecipe = recipeDAO.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));

        existingRecipe.setName(recipeDTO.getName());
        existingRecipe.setDescription(recipeDTO.getDescription());
        existingRecipe.setImageUrl(recipeDTO.getImageUrl());
        existingRecipe.setPreparationTime(recipeDTO.getPreparationTime());
        existingRecipe.setServings(recipeDTO.getServings());
        existingRecipe.setDifficulty(recipeDTO.getDifficulty());
        User user = recipeDTO.getUser() != null ? modelMapper.map(recipeDTO.getUser(), User.class) : null;
        existingRecipe.setUser(user);

        Recipe updatedRecipe = recipeDAO.save(existingRecipe);
        return modelMapper.map(updatedRecipe, RecipeDTO.class);
    }

    @Override
    @Transactional
    public void deleteRecipe(Integer id) {
        Recipe recipe = recipeDAO.findById(id)
                .orElseThrow(() -> new RecipeNotFoundException("Recipe not found with id: " + id));
        recipeDAO.delete(recipe);
    }
}
