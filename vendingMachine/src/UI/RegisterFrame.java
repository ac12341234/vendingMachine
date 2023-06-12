package UI;

import classes.FocusListenerHint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class RegisterFrame extends JFrame implements MouseListener {

    private Container contentPane = this.getContentPane();

    private registerManager manager;

    private JTextField nameInput;
    private JPasswordField passwordInput;
    private JPasswordField checkInput;
    private JTextField emailInput;
    private String userHint = "用戶名  長度為 6 ~ 16 位英文數字";
    private String passwordHint = "密碼     長度為 6 ~ 16 位英文數字";
    private String checkHint = "確認密碼";
    private String emailHint = "電子信箱 (Gmail Only)";
    private Font font = new Font("微軟正黑體", Font.BOLD, 36);
    private JLabel loginButton;
    private JLabel registerButton;
    private JLabel bar;
    private JLabel user;
    private JLabel password;
    private JLabel check;
    private JLabel email;

    private String path = "vendingMachine\\src\\pictures";

    public RegisterFrame() throws Exception {
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

        manager = new registerManager();

        this.setSize(650, 450);
        // set Title
        this.setTitle("註冊介面");
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
        JLabel title = new JLabel("自動販賣機註冊系統");
        title.setFont(font);
        ImageIcon bg = new ImageIcon(path + "\\RegisterBackground.png");
        ImageIcon usr = new ImageIcon(path + "\\buttonIcon\\user.png");
        ImageIcon pswd = new ImageIcon(path + "\\buttonIcon\\password.png");
        ImageIcon mail = new ImageIcon(path + "\\buttonIcon\\email.png");
        JLabel background = new JLabel(bg);

        user = new JLabel(usr);
        password = new JLabel(pswd);
        check = new JLabel(pswd);
        email = new JLabel(mail);

        title.setBounds(0, 35, 650, 50);
        title.setHorizontalAlignment(SwingConstants.CENTER);

        background.setBounds(0, 0, 650, 450);

        user.setBounds(190, 100, 32, 32);
        password.setBounds(190, 150, 32, 32);
        check.setBounds(190, 200, 32, 32);
        email.setBounds(190, 250, 32, 32);

        contentPane.add(title);
        contentPane.add(user);
        contentPane.add(password);
        contentPane.add(check);
        contentPane.add(email);
        contentPane.add(background);
    }

    private void initTextField() {
        nameInput = new JTextField();
        passwordInput = new JPasswordField();
        checkInput = new JPasswordField();
        emailInput = new JTextField();

        nameInput.setBounds(245, 100, 250, 30);
        passwordInput.setBounds(245, 150, 250, 30);
        checkInput.setBounds(245, 200, 250, 30);
        emailInput.setBounds(245, 250, 250, 30);

        nameInput.addFocusListener(new FocusListenerHint(nameInput, userHint));
        passwordInput.addFocusListener(new FocusListenerHint(passwordInput, passwordHint));
        checkInput.addFocusListener(new FocusListenerHint(checkInput, checkHint));
        emailInput.addFocusListener(new FocusListenerHint(emailInput, emailHint));

        contentPane.add(nameInput);
        contentPane.add(passwordInput);
        contentPane.add(checkInput);
        contentPane.add(emailInput);
    }

    private void initButton() {
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
        registerButton.addMouseListener(this);
        loginButton.addMouseListener(this);

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
        if (source == registerButton) {
            // 開始註冊
            String name = nameInput.getText();
            String password = passwordInput.getText();
            String check = checkInput.getText();
            String email = emailInput.getText();
            int condition = isValidInput(name, password, check, email);
            if (condition == 1) showJDialog("帳號密碼不符合規範", "請重新註冊", true);
            else if (condition == 3) showJDialog("兩次密碼輸入不一致", "請重新註冊", true);
            else if (condition == 2) {
                try {
                    int reaction = manager.insertTable(name, password, email);
                    // 根據reaction操作
                    if (reaction == 1) {
                        showJDialog("恭喜用戶", "帳號註冊成功", true);
                        clearTextField(nameInput, passwordInput, checkInput, emailInput);
                    } else if (reaction == 2) {
                        showJDialog("用戶名已存在", "請重新輸入", true);
                        clearTextField(nameInput);
                    } else if (reaction == 3) {
                        showJDialog("帳號密碼不符合規範", "請重新輸入", true);
                        clearTextField(nameInput, passwordInput, checkInput);
                    } else if (reaction == 4) {
                        showJDialog("電子信箱不符合規範", "請重新輸入", true);
                        clearTextField(emailInput);
                    }
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (source == loginButton) {
            this.dispose();
            try {
                new LoginFrame();
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
     * @param check
     * @param email
     * @return
     */
    private int isValidInput(String name, String password, String check, String email) {
        String regex = "[a-zA-Z0-9]{6,16}";
        if (name.matches(regex) && password.matches(regex) && check.matches(regex)) {
            if (password.equals(check)) {
                if (email.matches("[a-zA-z0-9]+@gmail.com")) return 2; // 合格
                return 4; // email不合格
            }
            else return 3; // 確認密碼失敗
        } else {
            return 1; // 不符合規範
        }
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
}
