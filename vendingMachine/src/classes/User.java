package classes;

public class User {

    private int ID;
    private String name;
    private String password;
    private String email;

    public User() {
    }

    public User(int ID, String name, String password, String email) {
        this.ID = ID;
        this.name = name;
        this.password = password;
        this.email = email;
    }

    /**
     * 获取
     * @return ID
     */
    public int getID() {
        return ID;
    }

    /**
     * 设置
     * @param ID
     */
    public void setID(int ID) {
        this.ID = ID;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        return "User{ID = " + ID + ", name = " + name + ", password = " + password + ", email = " + email + "}";
    }
}
