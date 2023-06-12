package UI;

import classes.FocusListenerHint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class LoginFrame extends JFrame implements MouseListener {

    private Container contentPane = this.getContentPane();

    private static loginManager manager;

    private JTextField nameInput;
    private JPasswordField passwordInput;
    private JTextField randomCodeInput;
    private String userHint = "用戶名  長度為 6 ~ 16 位英文數字";
    private String passwordHint = "密碼     長度為 6 ~ 16 位英文數字";
    private String randomCodeHint = "驗證碼   可更換";
    private Font font = new Font("微軟正黑體", Font.BOLD, 36);
    private JLabel loginButton;
    private JLabel registerButton;
    private JLabel bar;
    private JLabel user;
    private JLabel password;
    private JLabel randomCode;
    private JLabel verify;
    private JLabel findPassword;
    private String rCode;

    private String path = "vendingMachine\\src\\pictures";

    public LoginFrame() throws Exception {
        initJFrame();
        initButton();
        initTextField();
        initImage();

        this.setVisible(true);
    }

    private void initJFrame() throws Exception {
        // 更改圖標
        ImageIcon FrameIcon = new ImageIcon(path + "\\Icon.png");
        this.setIconImage(FrameIcon.getImage());

        manager = new loginManager();

        this.setSize(650, 450);
        // set Title
        this.setTitle("登陸介面");
        // set UI on Top
        this.setAlwaysOnTop(true);
        // set UI in center
        this.setLocationRelativeTo(null);
        // set closingMode
        this.setDefaultCloseOperation(3); // 0:無法關閉 1:默認 2:全部關閉才終止虛擬機 3:關閉一個就終止虛擬機
        // cancel picture placing default
        this.setLayout(null);
        // setResizable
        this.setResizable(false);

    }

    private void initImage() {
        // contentPane.removeAll();

        JLabel title = new JLabel("自動販賣機登入系統");
        title.setFont(font);
        ImageIcon bg = new ImageIcon(path + "\\RegisterBackground.png");
        ImageIcon usr = new ImageIcon(path + "\\buttonIcon\\user.png");
        ImageIcon pswd = new ImageIcon(path + "\\buttonIcon\\password.png");
        ImageIcon verification = new ImageIcon(path + "\\buttonIcon\\verification.png");


        JLabel background = new JLabel(bg);

        user = new JLabel(usr);
        password = new JLabel(pswd);
        randomCode = new JLabel(verification);

        title.setBounds(0, 50, 650, 50);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        background.setBounds(0, 0, 650, 450);
        user.setBounds(190, 150, 32, 32);
        password.setBounds(190, 200, 32, 32);
        randomCode.setBounds(190, 250, 32, 32);

        contentPane.add(title);
        contentPane.add(user);
        contentPane.add(password);
        contentPane.add(randomCode);
        contentPane.add(verify);
        contentPane.add(findPassword);
        contentPane.add(background);
    }

    private void initTextField() {
        nameInput = new JTextField();
        passwordInput = new JPasswordField();
        randomCodeInput = new JTextField();

        nameInput.setBounds(245, 150, 250, 30);
        passwordInput.setBounds(245, 200, 250, 30);
        randomCodeInput.setBounds(245, 250, 110, 30);

        nameInput.addFocusListener(new FocusListenerHint(nameInput, userHint));
        passwordInput.addFocusListener(new FocusListenerHint(passwordInput, passwordHint));
        randomCodeInput.addFocusListener(new FocusListenerHint(randomCodeInput, randomCodeHint));

        contentPane.add(nameInput);
        contentPane.add(passwordInput);
        contentPane.add(randomCodeInput);

    }

    private void initButton() {
        Font font = new Font("微軟正黑體", Font.BOLD, 14);
        // code
        {
            rCode = code();
            verify = new JLabel(rCode);
            verify.setFont(font);
            verify.setForeground(Color.RED);
            verify.setOpaque(true);
            verify.setBackground(Color.white);
            verify.setHorizontalAlignment(SwingConstants.CENTER);
            verify.setBounds(365, 250, 55, 30);
        }

        // findPassword
        {
            findPassword = new JLabel("忘記密碼");
            findPassword.setFont(font);
            findPassword.setOpaque(true);
            findPassword.setBackground(Color.white);
            findPassword.setHorizontalAlignment(SwingConstants.CENTER);
            findPassword.setBounds(435, 250, 60, 30);
        }


        ImageIcon registerBt = new ImageIcon(path + "\\buttonIcon\\register.png");
        registerButton = new JLabel(registerBt);

        ImageIcon loginBt = new ImageIcon(path + "\\buttonIcon\\login.png");
        loginButton = new JLabel(loginBt);

        ImageIcon barIcon = new ImageIcon(path + "\\buttonIcon\\bar.png");
        bar = new JLabel(barIcon);

        registerButton.setBounds(350, 315, 35, 35);
        loginButton.setBounds(260, 315, 35, 35);
        bar.setBounds(225, 292, 200, 75);

        // add event
        {
            registerButton.addMouseListener(this);
            loginButton.addMouseListener(this);
            verify.addMouseListener(this);
            findPassword.addMouseListener(this);
        }

        contentPane.add(loginButton);
        contentPane.add(registerButton);
        contentPane.add(bar);
    }


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Object source = e.getSource();
        // 切換驗證碼
        if (source == verify) {
            rCode = code();
            verify.setText(rCode);
            contentPane.repaint();
        }
        // 忘記密碼
        if (source == findPassword) {
            try {
                new findPasswordFrame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

        if (source == loginButton) {
            // 開始登陸
            String name = nameInput.getText();
            String password = passwordInput.getText();
            String check = randomCodeInput.getText();
            int condition = isValidInput(name, password, check);
            if (condition == 1) showJDialog("帳號密碼不符合規範", "請重新輸入", true);
            else if (condition == 3) {
                showJDialog("驗證碼錯誤", "請重新輸入", true);
                rCode = code();
                verify.setText(rCode);
                randomCodeInput.setText("");
                contentPane.repaint();
            }
            else if (condition == 2) {
                try {
                    int reaction = manager.isValidLogin(name, password);
                    // 根據reaction操作
                    if (reaction == 3) { // 用戶不存在
                        clearTextField(nameInput, passwordInput, randomCodeInput);
                        showJDialog("用戶不存在", "請重新輸入或註冊", true);
                    } else if (reaction == 2) { // 密碼錯誤
                        clearTextField(passwordInput, randomCodeInput);
                        showJDialog("密碼錯誤", "請重新輸入", true);
                    } else if (reaction == 1) { // 成功登陸
                        clearTextField(nameInput, passwordInput, randomCodeInput);
                        showJDialog("登入成功", "即將跳轉至主系統", true);
                        new VendingMachine(name);
                        this.dispose();
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (source == registerButton) {
            this.dispose();
            try {
                new RegisterFrame();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    /**
     * show JDialog
     * @param text1
     * @param text2
     * @param order
     */
    private void showJDialog(String text1, String text2, boolean order) {

        Font font = new Font("微軟正黑體", Font.BOLD, 14);

        JLabel jLabel1 = new JLabel(text1);
        JLabel jLabel2 = new JLabel(text2);

        jLabel1.setFont(font);
        jLabel2.setFont(font);

        JDialog jDialog = new JDialog();
        jDialog.getContentPane().add(jLabel1);
        jDialog.getContentPane().add(jLabel2);

        jDialog.setTitle("系統提示");

        jLabel1.setBounds(0, 20, 180, 30);
        jLabel2.setSize(150, 30);

        jLabel1.setVerticalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel2.setVerticalAlignment(SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        jDialog.setSize(200, 150);
        jDialog.setAlwaysOnTop(true);
        jDialog.setModal(true);
        if (order) jDialog.setLocationRelativeTo(null);
        else {
            jLabel1.setBounds(0, 0, 200, 30);
            jLabel2.setBounds(0, 20, 150, 30);
            jDialog.setUndecorated(true);
            jDialog.setModal(false);
            jDialog.setLocation((int) this.getLocation().getX() + 175, 85);
            jDialog.setSize(200, 30);
        }

        jDialog.setVisible(true);
    }

    /**
     * 判斷輸入是否合法
     * @param name
     * @param password
     * @param code
     * @return
     */
    private int isValidInput(String name, String password, String code) {
        String regex = "[a-zA-Z0-9]{6,16}";
        if (name.matches(regex) && password.matches(regex)) {
            if (code.equals(rCode)) return 2; // 合格
            else return 3; // 驗證碼錯誤
        } else {
            return 1; // 不符合規範
        }
    }

    /**
     * 生成驗證碼
     * @return
     */
    public static String code() {
        // 生成驗證碼 4位英文 1位數字 數字位置隨機
        char[] arr = new char[52];
        for (int i = 0; i < arr.length; i++) {
            if (i < arr.length / 2) {
                char c = (char) ('a' + i);
                arr[i] = c;
            } else {
                char c = (char) ('A' + i - arr.length / 2);
                arr[i] = c;
            }
        }
        Random r = new Random();
        int number = r.nextInt(10);
        int index = r.nextInt(5);
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i == index) {
                code.append(number);
            } else {
                int indexEN = r.nextInt(52);
                code.append(arr[indexEN]);
            }
        }
        return code.toString();
    }

    /**
     * 清空輸入
     * @param textFields
     */
    private void clearTextField(JTextField... textFields) {
        for (JTextField textField : textFields) {
            textField.setText("");
        }
    }

    public static loginManager getManager() {
        return manager;
    }
}
