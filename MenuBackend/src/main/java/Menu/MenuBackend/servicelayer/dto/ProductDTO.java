package menu.menubackend.servicelayer.dto;

import lombok.Data;
import menu.menubackend.datalayer.enums.WeightUnit;

@Data
public class ProductDTO {
    private Integer id;
    private String name;
    private UserDTO user;
    private String description;
    private String imageUrl;
    private Long weight;
    private WeightUnit weightUnit;
    private Long calories;
}

