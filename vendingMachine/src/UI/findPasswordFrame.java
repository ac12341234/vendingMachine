package UI;

import classes.FocusListenerHint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class findPasswordFrame extends JFrame implements MouseListener {

    private Container contentPane = this.getContentPane();
    private Font font = new Font("微軟正黑體", Font.BOLD, 14);
    private String userHint = "用戶名  長度為 6 ~ 16 位英文數字";
    private String emailHint = "電子信箱 (Gmail Only)";
    private String passwordHint = "找回密碼";

    private String path = "vendingMachine\\src\\pictures";

    private JLabel user;
    private JTextField userInput;
    private JLabel email;
    private JTextField emailInput;
    private JLabel password;
    private JTextField passwordOutput;
    private JLabel find;
    private JLabel exit;

    public findPasswordFrame() throws Exception {
        initJFrame();
        initTextField();
        initButton();
        initImage();
        this.setVisible(true);
    }

    private void initJFrame() throws Exception {
        // 更改圖標
        ImageIcon FrameIcon = new ImageIcon(path + "\\Icon.png");
        this.setIconImage(FrameIcon.getImage());

        this.setSize(400, 280);
        // set Title
        this.setTitle("找回密碼");
        // set UI on Top
        this.setAlwaysOnTop(true);
        // set UI in center
        this.setLocationRelativeTo(null);
        // set closingMode
        this.setDefaultCloseOperation(2); // 0:無法關閉 1:默認 2:全部關閉才終止虛擬機 3:關閉一個就終止虛擬機
        // cancel picture placing default
        this.setLayout(null);
        // setResizable
        this.setResizable(false);

    }

    private void initImage() {
        // contentPane.removeAll();

        ImageIcon bg = new ImageIcon(path + "\\RegisterBackground.png");
        ImageIcon usr = new ImageIcon(path + "\\buttonIcon\\user.png");
        ImageIcon mail = new ImageIcon(path + "\\buttonIcon\\email.png");
        ImageIcon pswd = new ImageIcon(path + "\\buttonIcon\\password.png");

        JLabel background = new JLabel(bg);

        user = new JLabel(usr);
        email = new JLabel(mail);
        password = new JLabel(pswd);

        background.setBounds(0, 0, 650, 450);
        user.setBounds(50, 40, 32, 32);
        email.setBounds(50, 90, 32, 32);
        password.setBounds(50, 90, 32, 32);

        contentPane.add(user);
        contentPane.add(email);
        contentPane.add(password);
        contentPane.add(background);
    }

    private void initTextField() {
        userInput = new JTextField();
        emailInput = new JTextField();
        passwordOutput = new JTextField();

        userInput.setBounds(95, 40, 250, 30);
        emailInput.setBounds(95, 90, 250, 30);
        passwordOutput.setBounds(95, 140, 250, 30);

        userInput.addFocusListener(new FocusListenerHint(userInput, userHint));
        emailInput.addFocusListener(new FocusListenerHint(emailInput, emailHint));
        passwordOutput.addFocusListener(new FocusListenerHint(passwordOutput, passwordHint));

        contentPane.add(userInput);
        contentPane.add(emailInput);
        contentPane.add(passwordOutput);
    }

    private void initButton() {
        find = new JLabel("找回密碼");
        find.setFont(font);
        find.setBounds(130, 190, 60, 35);
        find.setOpaque(true);
        find.setHorizontalAlignment(SwingConstants.CENTER);
        find.setBackground(Color.white);
        find.addMouseListener(this);

        exit = new JLabel("退出系統");
        exit.setFont(font);
        exit.setBounds(210, 190, 60, 35);
        exit.setOpaque(true);
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        exit.setBackground(Color.white);
        exit.addMouseListener(this);

        contentPane.add(find);
        contentPane.add(exit);
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
        if (source == find) {
            try {
                returnPassword(userInput.getText(), emailInput.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (source == exit) {
            this.dispose();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public void returnPassword(String username, String email) throws Exception {
        String password = LoginFrame.getManager().findPassword(username, email);
        if (password != null) {
            passwordOutput.setText(password);
            contentPane.repaint();
        } else {
            showJDialog("用戶名不存在或信箱錯誤", "請重新輸入", true);
            userInput.setText("");
            emailInput.setText("");
        }
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
}
