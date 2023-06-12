package UI;

import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.*;
import java.util.Properties;

public class registerManager {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;

    // sql
    // private String deleteTAB_SQL = "drop table if exists User";
    private String createDB_SQL = "create database if not exists itcast";
    private String createTAB_SQL = "CREATE TABLE if not exists User (" +
            "ID int primary key auto_increment, " +
            "    name    VARCHAR(16), " +
            "  password  VARCHAR(16), " +
            " email VARCHAR(30))";
    private String insert_SQL = "insert into User(name,password,email) values(?, ?, ?)";
    private String select_SQL = "select * from User";

    public registerManager() throws Exception {

        Properties prop1 = new Properties();
        prop1.load(new FileInputStream("vendingMachine\\src\\druid2.properties"));

        Connection connection = DruidDataSourceFactory.createDataSource(prop1).getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(createDB_SQL);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

        Properties prop2 = new Properties();
        prop2.load(new FileInputStream("vendingMachine\\src\\druid.properties"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop2);

        conn = dataSource.getConnection();
        conn.setAutoCommit(false);

        createTable();
    }

    public void createTable() throws Exception {
        try {
            // deleteTable();
            pst = conn.prepareStatement(createTAB_SQL);
            pst.executeUpdate();
            conn.commit();
        } catch (Exception e) {
            System.out.println("Create Table Exception :" + e.toString());
            conn.rollback();
        } finally {
            close();
        }
    }

    public int insertTable(String Name, String Password, String email) throws Exception {
        boolean confirm = SelectTable(Name);
        try {
            if (confirm) {
                pst = conn.prepareStatement(insert_SQL);
                pst.setString(1, Name);
                pst.setString(2, Password);
                pst.setString(3, email);
                pst.executeUpdate();
                conn.commit();
                return 1; // 成功註冊
            } else {
                conn.commit();
                return 2; // 用戶名已存在
            }
        } catch (Exception e) {
            System.out.println("Insert Exception :" + e.toString());
            conn.rollback();
        } finally {
            close();
        }
        return 0;
    }

    public boolean SelectTable(String name) {

        try {
            pst = conn.prepareStatement(select_SQL);
            rs = pst.executeQuery();
            while (rs.next()) {
                if (rs.getString("name").equals(name))
                    return false;
            }
        } catch (SQLException e) {
            System.out.println("Selection Exception :" + e.toString());
        } finally {
            close();
        }
        return true;
    }

    private void close() {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pst != null) {
                pst.close();
            }
        } catch (SQLException e) {
            System.out.println("Close Exception :" + e.toString());
        }
    }

}
