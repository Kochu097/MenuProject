package Menu.MenuBackend.datalayer.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MENU")
@Data
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Day", nullable = false)
    private LocalDate day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;
}
