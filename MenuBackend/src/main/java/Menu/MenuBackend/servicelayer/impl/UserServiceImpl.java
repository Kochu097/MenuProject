package menu.menubackend.servicelayer.impl;

import menu.menubackend.common.exception.DuplicateAuthTokenException;
import menu.menubackend.common.exception.UserNotFoundException;
import menu.menubackend.datalayer.dao.UserDAO;
import menu.menubackend.datalayer.entity.User;
import menu.menubackend.servicelayer.UserService;
import menu.menubackend.servicelayer.dto.UserDTO;
import org.springframework.transaction.annotation.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private static final String TOKEN_EXISTS_ERROR_MESSAGE = "Authentication token already exists";
    private static final String USER_NOT_FOUND_ERROR_MESSAGE = "User not found with id: ";

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private ModelMapper modelMapper;

    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userDAO.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .toList();
    }

    @Transactional(readOnly = true)
    public UserDTO getUserById(Integer id) {
        User user = userDAO.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + id));
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        if (userDAO.existsByAuthenticationToken(userDTO.getAuthenticationToken())) {
            throw new DuplicateAuthTokenException(TOKEN_EXISTS_ERROR_MESSAGE);
        }
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userDAO.save(user);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    @Transactional
    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User existingUser = userDAO.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + id));

        if (!existingUser.getAuthenticationToken().equals(userDTO.getAuthenticationToken()) &&
                userDAO.existsByAuthenticationToken(userDTO.getAuthenticationToken())) {
            throw new DuplicateAuthTokenException(TOKEN_EXISTS_ERROR_MESSAGE);
        }

        existingUser.setAuthenticationToken(userDTO.getAuthenticationToken());
        User updatedUser = userDAO.save(existingUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Transactional
    public void deleteUser(Integer id) {
        User user = userDAO.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND_ERROR_MESSAGE + id));
        userDAO.delete(user);
    }
}
