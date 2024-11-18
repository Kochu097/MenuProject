package Menu.MenuBackend.datalayer.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class BasicDAO {

    @PersistenceContext
    public EntityManager entityManager;
}
