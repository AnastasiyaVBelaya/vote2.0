package by.it_academy.jd2.storage.db.utils;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.IOException;
import java.io.InputStream;

public class DBUtils {

    private static DataSource dataSource;
    public static long time = 0;

    static {
        Properties props = new Properties();
        try (InputStream input = DBUtils.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("Не удалось найти конфигурационный файл db.properties");
            }
            props.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Ошибка при загрузке конфигурационного файла", ex);
        }

        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass(props.getProperty("db.driver"));
            cpds.setJdbcUrl(props.getProperty("db.url"));
            cpds.setUser(props.getProperty("db.user"));
            cpds.setPassword(props.getProperty("db.password"));
            cpds.setMaxPoolSize(Integer.parseInt(props.getProperty("db.maxPoolSize")));
        } catch (Exception e) {
            throw new RuntimeException("Ошибка при настройке пула соединений", e);
        }

        dataSource = cpds;
    }

    public static Connection getConnect() {
        long start = System.nanoTime();
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Ошибка подключения", e);
        } finally {
            long stop = System.nanoTime();
            time += (stop - start);
        }
    }
}