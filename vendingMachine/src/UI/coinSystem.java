package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class coinSystem extends JFrame implements MouseListener, KeyListener {

    static int money = 0;

    String path = "vendingMachine\\src\\pictures\\coin";
    Container contentPane = this.getContentPane();
    ImageIcon coin50Light = new ImageIcon(path + "\\coin50light.png");
    ImageIcon coin10Light = new ImageIcon(path + "\\coin10light.png");
    ImageIcon coin5Light = new ImageIcon(path + "\\coin5light.png");
    ImageIcon coin1Light = new ImageIcon(path + "\\coin1light.png");

    JLabel coin1;
    JLabel coin1L;

    JLabel coin5;
    JLabel coin5L;

    JLabel coin10;
    JLabel coin10L;

    JLabel coin50;
    JLabel coin50L;

    public static int coinSystemOpen = 0;

    int[] coinSet = new int[4];


    coinSystem() {
        initJFrame();
        initImage();
        addEvent();
        this.setVisible(true);
    }

    private void initJFrame() {
        // 更改圖標
        ImageIcon FrameIcon = new ImageIcon( "vendingMachine\\src\\pictures\\Icon.png");
        this.setIconImage(FrameIcon.getImage());

        // 設置介面的寬高
        this.setSize(515, 300);

        // 設置標題
        this.setTitle("投幣系統 V1.0");

        // 設置介面置頂
        this.setAlwaysOnTop(true);

        // 設置介面居中
        this.setLocationRelativeTo(null);

        // 設置關閉模式
        this.setDefaultCloseOperation(2); // 0:無法關閉 1:默認 2:全部關閉才終止虛擬機 3:關閉一個就終止虛擬機

        // 取消圖片默認居中放置
        this.setLayout(null);

        // 給整個介面添加鍵盤監聽
        this.addKeyListener(this);

        this.setResizable(false);

        // 添加螢幕監聽
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                coinSystem.coinSystemOpen = 1;
            }

            @Override
            public void windowClosing(WindowEvent e) {
                coinSystem.coinSystemOpen = 0;
            }
        });
    }

    private void initButton() {
        // 投幣按鈕
        JButton coinIN = new JButton("投幣");
        Font font = new Font("微軟正黑體", Font.BOLD, 20);
        coinIN.setFont(font);
        coinIN.setBounds(180, 190, 80, 50);
        coinIN.setHorizontalAlignment(SwingConstants.CENTER);

        // 投幣監聽
        coinIN.addActionListener((ActionEvent e) -> {
            money += sum();

            this.dispose();
            coinSystem.coinSystemOpen = 0;
        });

        // 退幣按鈕 三處
        Font font2 = new Font("微軟正黑體", Font.BOLD, 16);
        JButton coin1RT = coinRTJLabel(35, 150, 0, font2);
        JButton coin5RT = coinRTJLabel(155, 150, 1, font2);
        JButton coin10RT = coinRTJLabel(275, 150, 2, font2);
        JButton coin50RT = coinRTJLabel(395, 150, 3, font2);

        contentPane.add(coinIN);
        contentPane.add(coin1RT);
        contentPane.add(coin5RT);
        contentPane.add(coin10RT);
        contentPane.add(coin50RT);
    }

    private void initImage() {

        // 清空屏幕
        contentPane.removeAll();

        ImageIcon bg = new ImageIcon("vendingMachine\\src\\pictures\\RegisterBackground.png");

        JLabel background = new JLabel(bg);
        background.setBounds(0, 0, 515, 300);

        ImageIcon c1 = new ImageIcon(path + "\\coin1.png");
        coin1 = new JLabel(c1);
        coin1.setBounds(10, 30, 120, 84);
        coin1L = new JLabel(coin1Light);
        coin1L.setBounds(10, 30, 120, 84);
        coin1L.setVisible(false); // 初始化時隱藏

        ImageIcon c5 = new ImageIcon(path + "\\coin5.png");
        coin5 = new JLabel(c5);
        coin5.setBounds(130, 30, 120, 84);
        coin5L = new JLabel(coin5Light);
        coin5L.setBounds(130, 30, 120, 84);
        coin5L.setVisible(false); // 初始化時隱藏

        ImageIcon c10 = new ImageIcon(path + "\\coin10.png");
        coin10 = new JLabel(c10);
        coin10.setBounds(250, 30, 120, 84);
        coin10L = new JLabel(coin10Light);
        coin10L.setBounds(250, 30, 120, 84);
        coin10L.setVisible(false); // 初始化時隱藏

        ImageIcon c50 = new ImageIcon(path + "\\coin50.png");
        coin50 = new JLabel(c50);
        coin50.setBounds(370, 30, 120, 84);
        coin50L = new JLabel(coin50Light);
        coin50L.setBounds(370, 30, 120, 84);
        coin50L.setVisible(false); // 初始化時隱藏

        // 投幣次數紀錄
        Font font = new Font("微軟正黑體", Font.BOLD, 18);
        JLabel c1Count = coinCountJLabel(40, 120, font, coinSet[0] + "枚", 60, 25);
        JLabel c5Count = coinCountJLabel(160, 120, font, coinSet[1] + "枚",60, 25);
        JLabel c10Count = coinCountJLabel(280, 120, font, coinSet[2] + "枚", 60, 25);
        JLabel c50Count = coinCountJLabel(400, 120, font, coinSet[3] + "枚", 60, 25);

        // 記錄投幣總金額
        JLabel total = coinCountJLabel(270, 200, font,"共" + sum() + "元" , 80, 30);
        if (sum() >= 1000) {
            total.setSize(100, 30);
        } else if (sum() >= 10000) {
            total.setSize(120, 30);
        }

        addEvent();

        pictureLight();

        contentPane.add(coin1);
        contentPane.add(coin1L);
        contentPane.add(coin5);
        contentPane.add(coin5L);
        contentPane.add(coin10);
        contentPane.add(coin10L);
        contentPane.add(coin50);
        contentPane.add(coin50L);

        contentPane.add(c1Count);
        contentPane.add(c5Count);
        contentPane.add(c10Count);
        contentPane.add(c50Count);

        contentPane.add(total);
        initButton();

        contentPane.add(background);
        contentPane.repaint();
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
        if (source != null) {
            if (source == coin1 || source == coin1L) {
                coinSet[0] = coinSet[0] + 1;
            } else if (source == coin5 || source == coin5L) {
                coinSet[1] = coinSet[1] + 1;
            } else if (source == coin10 || source == coin10L) {
                coinSet[2] = coinSet[2] + 1;
            } else if (source == coin50 || source == coin50L) {
                coinSet[3] = coinSet[3] + 1;
            }
            initImage();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * 調整硬幣高亮
     */
    private void pictureLight() {
        if (coinSet[0] > 0) {
            // 讓coin1高亮
            coin1.setVisible(false);
            coin1L.setVisible(true);
        } else {
            // 讓coin1變暗
            coin1.setVisible(true);
            coin1L.setVisible(false);
        }
        if (coinSet[1] > 0) {
            // 讓coin5高亮
            coin5.setVisible(false);
            coin5L.setVisible(true);
        } else {
            // 讓coin5變暗
            coin5.setVisible(true);
            coin5L.setVisible(false);
        }
        if (coinSet[2] > 0) {
            // 讓coin10高亮
            coin10.setVisible(false);
            coin10L.setVisible(true);
        } else {
            // 讓coin10變暗
            coin10.setVisible(true);
            coin10L.setVisible(false);
        }
        if (coinSet[3] > 0) {
            // 讓coin50高亮
            coin50.setVisible(false);
            coin50L.setVisible(true);
        } else {
            // 讓coin50變暗
            coin50.setVisible(true);
            coin50L.setVisible(false);
        }

    }

    private void addEvent() {
        // 添加事件
        coin1.addMouseListener(this);
        coin1L.addMouseListener(this);
        coin5.addMouseListener(this);
        coin5L.addMouseListener(this);
        coin10.addMouseListener(this);
        coin10L.addMouseListener(this);
        coin50.addMouseListener(this);
        coin50L.addMouseListener(this);
    }

    /**
     * 控制投幣的數量
     * @param x
     * @param y
     * @param font
     * @param text
     * @param width
     * @param height
     * @return
     */
    private JLabel coinCountJLabel(int x, int y, Font font, String text, int width, int height) {
        JLabel temp = new JLabel(text);
        temp.setForeground(Color.black);
        temp.setOpaque(true);
        temp.setBackground(Color.white);
        temp.setFont(font);
        temp.setBounds(x, y, width, height);
        temp.setHorizontalAlignment(SwingConstants.CENTER);
        return temp;
    }

    /**
     * 退幣
     * @param x
     * @param y
     * @param index
     * @param font
     * @return
     */
    private JButton coinRTJLabel(int x, int y, int index, Font font) {
        JButton temp = new JButton("退幣");
        temp.setFont(font);
        temp.setBounds(x, y, 70, 25);
        temp.setHorizontalAlignment(SwingConstants.CENTER);

        temp.addActionListener((ActionEvent e) -> {
            coinSet[index] = (coinSet[index] > 0 ? coinSet[index] - 1 : coinSet[index]);
            initImage();
        });
        return temp;
    }

    /**
     * 總金額
     * @return
     */
    private int sum() {
        return coinSet[0] * 1 + coinSet[1] * 5 + coinSet[2] * 10 + coinSet[3] * 50;
    }
}
