package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Time;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Vector;

public class userInfo extends JFrame {

    Container contentPane = this.getContentPane();
    private String path = "vendingMachine\\src\\pictures";
    private String username = null;
    private ResultSet rs;
    private JLabel user;
    private JLabel exit;
    Vector<Vector<Object>> contentList = new Vector<>();
    Vector<Object> goodData = new Vector<>();

    Font font = new Font("微軟正黑體", Font.BOLD, 14);
    public userInfo(String username, ResultSet rs) throws Exception {
        this.username = username;
        this.rs = rs;

        initJFrame();
        initButton();
        initTable();

        initImage();

        this.setVisible(true);
    }

    private void initJFrame() throws Exception {
        // 更改圖標
        ImageIcon FrameIcon = new ImageIcon(path + "\\Icon.png");
        this.setIconImage(FrameIcon.getImage());

        this.setSize(420, 550);
        // set Title
        this.setTitle("購買紀錄");
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
        ImageIcon bg = new ImageIcon(path + "\\RegisterBackground.png");
        ImageIcon usr = new ImageIcon(path + "\\buttonIcon\\user.png");
        JLabel background = new JLabel(bg);

        JLabel username = new JLabel(this.username);
        JLabel text = new JLabel("顯示最近20筆購買紀錄");
        text.setFont(font);
        text.setBounds(220, 10, 250, 30);
        contentPane.add(text);

        username.setFont(font);
        username.setBounds(65, 10, this.username.length() * 14, 30);
        contentPane.add(username);
        user = new JLabel(usr);

        background.setBounds(0, 0, 420, 550);

        user.setBounds(30, 10, 32, 32);

        contentPane.add(user);
        contentPane.add(background);
    }

    private void initButton() {
        exit = new JLabel("退出系統");
        exit.setFont(font);
        exit.setBounds(170, 390, 60, 30);
        exit.setHorizontalAlignment(SwingConstants.CENTER);
        exit.setOpaque(true);
        exit.setBackground(Color.LIGHT_GRAY);
        exit.setHorizontalAlignment(SwingConstants.CENTER);

        contentPane.add(exit);

        exit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                userInfo.this.dispose();
            }
        });
    }

    private void initTable() throws Exception {
        getData();

        JTable table = new JTable(contentList, goodData);
        JScrollPane jScrollPane=  new JScrollPane(table);
        contentPane.add(jScrollPane);

        table.setFont(font);
        table.setBounds(25, 50, 360, 380);

        table.setVisible(true);
        contentPane.add(table);
    }

    /**
     * 購買的詳細資料
     * @throws Exception
     */
    private void getData() throws Exception {
        goodData.add("商品名稱");
        goodData.add("商品價格");
        goodData.add("購買日期");
        goodData.add("購買時間");
        contentList.add(goodData);
        int i = 0;
        if (rs != null) {
            while (rs.next()) {
                i++;
                String good = rs.getString("good");
                int price = rs.getInt("price");
                Date date = rs.getDate("date");
                Time time = rs.getTime("date");
                Vector<Object> goodDataList = new Vector<>();
                if (i < 10) goodDataList.add(i + ".   " + good);
                else goodDataList.add(i + ". " + good);
                goodDataList.add(price + "元");
                goodDataList.add(date);
                goodDataList.add(time);
                this.contentList.add(goodDataList);
            }
        }

    }
}
