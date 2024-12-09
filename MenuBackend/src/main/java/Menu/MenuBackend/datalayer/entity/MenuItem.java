package Menu.MenuBackend.datalayer.entity;

import Menu.MenuBackend.datalayer.enums.MenuItemType;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "MENU_ITEM")
@Data
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Menu_ID", nullable = false)
    private Menu menu;

    @Column(name = "MENU_ITEM_TYPE")
    @Enumerated(EnumType.STRING)
    private MenuItemType menuItemType;

    @Column(name = "SERVINGS", nullable = false)
    private Integer servings;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Recipe_ID")
    private Recipe recipe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Product_ID")
    private Product product;
}
