package Menu.MenuBackend.datalayer.DAO.container;

import org.testcontainers.containers.MySQLContainer;

public class BaseMySQLContainer extends MySQLContainer<BaseMySQLContainer> {
    private static final String IMAGE_VERSION = "mysql:8.0.30";
    private static BaseMySQLContainer container;

    private BaseMySQLContainer() {
        super(IMAGE_VERSION);
    }

    public static BaseMySQLContainer getInstance() {
        if(container == null) {
            container = new BaseMySQLContainer()
                    .withDatabaseName("menu_database")
                    .withUsername("root")
                    .withPassword("zaq1@WSX");
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("spring.datasource.url", container.getJdbcUrl());
        System.setProperty("spring.datasource.username", container.getUsername());
        System.setProperty("spring.datasource.password", container.getPassword());
    }

    @Override
    public void stop() {
    }
}
