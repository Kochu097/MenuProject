package Menu.MenuBackend.datalayer.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "PRODUCT")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "Name", nullable = false)
    private String name;
}
