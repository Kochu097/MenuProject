package Menu.MenuBackend.datalayer.entity;

import lombok.Data;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "MENU")
@Data
@NamedQueries({
        @NamedQuery(name = Menu.GET_BY_PERIOD,
        query = "SELECT m FROM Menu m where (m.day >= :startDate AND m.day <= :endDate) AND m.user = :user"),
})
public class Menu {

    public static final String GET_BY_PERIOD = "Menu.GET_BY_PERIOD";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "Menu_Day", nullable = false)
    private LocalDate day;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "User_ID", nullable = false)
    private User user;
}
