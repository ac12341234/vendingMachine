package UI;

import classes.User;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.crypto.interfaces.PBEKey;
import javax.sql.DataSource;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class loginManager {

    private Connection conn;
    private ResultSet rs;
    private PreparedStatement pst;

    // sql
    private String createDB_SQL = "create database if not exists itcast";
    private String select_SQL = "select * from User";
    private String findPassword_SQL = "select password from user where name = ? && email = ?";
    private String createTAB_SQL = "CREATE TABLE if not exists User (" +
            "ID int primary key auto_increment, " +
            "    name    VARCHAR(20) " +
            "  , password  VARCHAR(20))";



    public loginManager() throws Exception {

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

    /**
     * 測試用
     * @throws Exception
     */
    public void login() throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("enter your username");
        String uName = sc.next();
        System.out.println("enter your password");
        String uPassword = sc.next();

        // judge
        int reaction = isValidLogin(uName, uPassword);
        if (reaction == 0) {
            System.out.println("user doesn't exist");
        } else if (reaction == 1) {
            System.out.println("successfully login");
        } else {
            System.out.println("password is wrong");
        }
    }

    /**
     * 判斷是否是合法的登陸
     * @param name
     * @param password
     * @return
     * @throws Exception
     */
    public int isValidLogin(String name, String password) throws Exception {

        // select data
        try {
            pst = conn.prepareStatement(select_SQL);
            rs = pst.executeQuery();

            ArrayList<User> users = new ArrayList<>();
            while (rs.next()) {
                int ID = rs.getInt("ID");
                String uName = rs.getString("name");
                String uPassword = rs.getString("password");
                String uEmail = rs.getString("email");
                users.add(new User(ID, uName, uPassword, uEmail));
            }

            for (User user : users) {
                if (user.getName().equals(name)) {
                    if (user.getPassword().equals(password)) {
                        return 1; // 成功登入
                    } else {
                        return 2; // 用戶名存在, 但密碼錯誤
                    }
                }
            }
            return 3; // 用戶名不存在
        } catch (SQLException e) {
            System.out.println("Selection Exception :" + e.toString());
        } finally {
            close();
        }
        return 0;
    }

    public void createTable() throws Exception {
        try {
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

    /**
     * find password
     * @param username
     * @param email
     * @return
     * @throws Exception
     */
    public String findPassword(String username, String email) throws Exception {
        pst = conn.prepareStatement(findPassword_SQL);
        pst.setString(1, username);
        pst.setString(2, email);
        rs = pst.executeQuery();
        String password = null;
        while (rs.next()) {
            password = rs.getString("password");
        }
        close();
        return password;
    }
}
