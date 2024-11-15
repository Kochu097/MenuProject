package menu.menubackend.servicelayer.dto;

import menu.menubackend.datalayer.enums.MealType;

import java.time.LocalDate;
import java.util.List;

public class MenuDTO {

    private Long id;
    private LocalDate day;
    private MealType mealType;
    private List<MenuItemDTO> items;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDay() {
        return day;
    }

    public void setDay(LocalDate day) {
        this.day = day;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }
}
