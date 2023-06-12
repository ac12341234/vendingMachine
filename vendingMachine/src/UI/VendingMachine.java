package UI;

import classes.Good;
import classes.GoodsInfo;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.w3c.dom.Text;

import javax.sql.DataSource;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

public class VendingMachine extends JFrame implements MouseListener, MouseMotionListener {

    // simple path
    String path = "vendingMachine\\src\\pictures";

    int count = 0;
    int systemInfoOrder = 0;
    JLabel good1, good2, good3, good4, good5, good6, good7, good8, good9, good10, good11, good12, temp, coinEntry;
    private String user;

    String newDataInsert_SQL = "insert into purchasingHistory (username, good, price, date) values (?, ?, ?, current_time)";
    JDialog goodsData, systemInfo;

    // 進入時間
    long enterTime = 0;
    // 離開時間
    long stayTime = 0;

    // 設置圖標
    ImageIcon FrameIcon = new ImageIcon(path + "\\Icon.png");

    Container contentPane = this.getContentPane();

    Connection conn;

    ArrayList<Good> goodList = new ArrayList<>();

    VendingMachine(String user) throws Exception { // initialize
        this.user = user;
        connectToMySql();
        initJFrame();
        initJMenuBar();
        initImage(0);
        this.setVisible(true);

    }

    private void initJFrame() {
        // 更改圖標
        this.setIconImage(FrameIcon.getImage());
        // set Size
        this.setSize(550, 820);
        // set Title
        this.setTitle("自動販賣機 V1.0");
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

    private void initImage(int number) throws Exception {
        // clear all
        contentPane.removeAll();

        // background color
        // contentPane.setBackground(Color.RED);

        // background
        ImageIcon bg = new ImageIcon(path + "\\background.png");
        JLabel background = new JLabel(bg);
        background.setBounds(10, 0, 510, 760);

        // sent out goods
        addGood(number);

        // add goods
        {
            ImageIcon gd1 = new ImageIcon(path + "\\goods\\good1.png");
            ImageIcon gd2 = new ImageIcon(path + "\\goods\\good2.png");
            ImageIcon gd3 = new ImageIcon(path + "\\goods\\good3.png");
            ImageIcon gd4 = new ImageIcon(path + "\\goods\\good4.png");
            ImageIcon gd5 = new ImageIcon(path + "\\goods\\good5.png");
            ImageIcon gd6 = new ImageIcon(path + "\\goods\\good6.png");
            ImageIcon ce = new ImageIcon(path + "\\coinEntry.png");

            good1 = new JLabel(gd1);
            good2 = new JLabel(gd2);
            good3 = new JLabel(gd3);
            good4 = new JLabel(gd4);
            good5 = new JLabel(gd5);
            good6 = new JLabel(gd6);
            good7 = new JLabel(gd1);
            good8 = new JLabel(gd2);
            good9 = new JLabel(gd3);
            good10 = new JLabel(gd4);
            good11 = new JLabel(gd5);
            good12 = new JLabel(gd6);
            coinEntry = new JLabel(ce);

            /*temp = new JLabel(gd1);
            temp.setVisible(false);*/
        }

        // set location
        {
            good1.setBounds(70, 75, 50, 90);
            good2.setBounds(200, 75, 50, 90);
            good3.setBounds(330, 75, 50, 90);
            good4.setBounds(70, 205, 50, 90);
            good5.setBounds(200, 205, 50, 90);
            good6.setBounds(330, 205, 50, 90);
            good7.setBounds(135, 75, 50, 90);
            good8.setBounds(265, 75, 50, 90);
            good9.setBounds(395, 75, 50, 90);
            good10.setBounds(135, 205, 50, 90);
            good11.setBounds(265, 205, 50, 90);
            good12.setBounds(395, 205, 50, 90);
            coinEntry.setBounds(323, 425, 57, 55);
        }

        // add price menu from data of mysql

        selectMySql(conn);
        Font font = new Font("微軟正黑體", Font.BOLD, 18);
        JLabel price1 = addPriceBar(70, 170, "元", font, 1);
        JLabel price2 = addPriceBar(135, 170, "元", font, 1);
        JLabel price3 = addPriceBar(200, 170, "元", font, 2);
        JLabel price4 = addPriceBar(265, 170, "元", font, 2);
        JLabel price5 = addPriceBar(330, 170, "元", font, 3);
        JLabel price6 = addPriceBar(395, 170, "元", font, 3);
        JLabel price7 = addPriceBar(70, 300, "元", font, 4);
        JLabel price8 = addPriceBar(135, 300, "元", font, 4);
        JLabel price9 = addPriceBar(200, 300, "元", font, 5);
        JLabel price10 = addPriceBar(265, 300, "元", font, 5);
        JLabel price11 = addPriceBar(330, 300, "元", font, 6);
        JLabel price12 = addPriceBar(395, 300, "元", font, 6);

        // total money

        JLabel totalMoney = new JLabel();
        JLabel text = new JLabel("已投幣");

        text.setBounds(85, 430, 100, 30);
        text.setFont(font);

        totalMoney.setText("" + coinSystem.money + "元");
        totalMoney.setFont(font);

        totalMoney.setBounds(85, 440, 100, 50);

        // add events
        {
            good1.addMouseListener(this);
            good2.addMouseListener(this);
            good3.addMouseListener(this);
            good4.addMouseListener(this);
            good5.addMouseListener(this);
            good6.addMouseListener(this);
            good7.addMouseListener(this);
            good8.addMouseListener(this);
            good9.addMouseListener(this);
            good10.addMouseListener(this);
            good11.addMouseListener(this);
            good12.addMouseListener(this);
            // mouseMotionEvent
            good1.addMouseMotionListener(this);
            good2.addMouseMotionListener(this);
            good3.addMouseMotionListener(this);
            good4.addMouseMotionListener(this);
            good5.addMouseMotionListener(this);
            good6.addMouseMotionListener(this);
            good7.addMouseMotionListener(this);
            good8.addMouseMotionListener(this);
            good9.addMouseMotionListener(this);
            good10.addMouseMotionListener(this);
            good11.addMouseMotionListener(this);
            good12.addMouseMotionListener(this);

            coinEntry.addMouseListener(this);
        }

        // add goods to contentPane
        {
            contentPane.add(good1);
            contentPane.add(good2);
            contentPane.add(good3);
            contentPane.add(good4);
            contentPane.add(good5);
            contentPane.add(good6);
            contentPane.add(good7);
            contentPane.add(good8);
            contentPane.add(good9);
            contentPane.add(good10);
            contentPane.add(good11);
            contentPane.add(good12);
            contentPane.add(coinEntry);

            contentPane.add(price1);
            contentPane.add(price2);
            contentPane.add(price3);
            contentPane.add(price4);
            contentPane.add(price5);
            contentPane.add(price6);
            contentPane.add(price7);
            contentPane.add(price8);
            contentPane.add(price9);
            contentPane.add(price10);
            contentPane.add(price11);
            contentPane.add(price12);

            contentPane.add(totalMoney);
            contentPane.add(text);

            // insert background finally
            contentPane.add(background);

            // flush
            contentPane.repaint();
        }

    }

    private void initJMenuBar() {
        JMenuBar jmb = new JMenuBar();

        Font font1 = new Font("微軟正黑體", Font.BOLD, 14);
        Font font2 = new Font("微軟正黑體", Font.BOLD, 12);
        JMenu function = new JMenu("基本功能");
        JMenu aboutUs = new JMenu("關於我們");
        function.setFont(font1);
        aboutUs.setFont(font1);

        JMenuItem account = new JMenuItem("帳戶資訊");
        JMenuItem reLogin = new JMenuItem("重新登入");
        JMenuItem close = new JMenuItem("退出系統");
        account.setFont(font2);
        reLogin.setFont(font2);
        close.setFont(font2);

        // 添加事件
        close.addActionListener(e -> {
                showJDialog("感謝使用本系統", "期待與你再次相遇", true);
                System.exit(0);
            });

        reLogin.addActionListener(e -> {
                VendingMachine.this.dispose();
                try {
                    new LoginFrame();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

        account.addActionListener(e -> {
                try {
                    new userInfo(user, getHistory());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            });

        aboutUs.addMenuListener(new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                ImageIcon icon = new ImageIcon(path + "\\buttonIcon\\aboutUs.png");
                JLabel jLabel = new JLabel(icon);

                JDialog jd = new JDialog();
                jd.setTitle("關於我們");
                jLabel.setBounds(0, 0, 250, 270);

                jd.add(jLabel);
                jd.setSize(258, 270);
                jd.setLocationRelativeTo(null);
                jd.setAlwaysOnTop(true);
                jd.setModal(true);
                jd.setVisible(true);
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        function.add(account);
        function.add(reLogin);
        function.add(close);

        jmb.add(function);
        jmb.add(aboutUs);



        // add menu
        this.setJMenuBar(jmb);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        if (e.getButton() == MouseEvent.BUTTON3) {
            if ((source == good1 || source == good7) && count == 0) {
                showGoodsData(1);
            } else if (source == good2 || source == good8) {
                showGoodsData(2);
            } else if (source == good3 || source == good9) {
                showGoodsData(3);
            } else if (source == good4 || source == good10) {
                showGoodsData(4);
            } else if (source == good5 || source == good11) {
                showGoodsData(5);
            } else if (source == good6 || source == good12) {
                showGoodsData(6);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // determine target
        Object source = e.getSource();

        if (e.getButton() == MouseEvent.BUTTON1) {
            try {
                if (temp != null && temp.isVisible() && source != temp) {
                    showJDialog("不好意思", "請先取走商品再進行操作", true);
                } else {
                    if (source == good1 || source == good7) affordable(0);
                    else if (source == good2 || source == good8) affordable(1);
                    else if (source == good3 || source == good9) affordable(2);
                    else if (source == good4 || source == good10) affordable(3);
                    else if (source == good5 || source == good11) affordable(4);
                    else if (source == good6 || source == good12) affordable(5);
                    else if (source == temp) initImage(0);
                    else if (coinSystem.coinSystemOpen == 0 && source == coinEntry) {
                        coinSystem cs = new coinSystem();
                        cs.addWindowListener(new WindowAdapter() {
                            @Override
                            public void windowClosed(WindowEvent e) {
                                try {
                                    initImage(0);
                                } catch (Exception ex) {
                                    throw new RuntimeException(ex);
                                }
                            }
                        });
                    }
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getButton() == MouseEvent.BUTTON3) {
            try {
                mouseExit();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        Object source = e.getSource();
        if (source == good1 || source == good7) {
            enterTime = System.currentTimeMillis();
            // System.out.println(enterTime);
        } else if (source == good2 || source == good8) {
            enterTime = System.currentTimeMillis();
        } else if (source == good3 || source == good9) {
            enterTime = System.currentTimeMillis();
        } else if (source == good4 || source == good10) {
            enterTime = System.currentTimeMillis();
        } else if (source == good5 || source == good11) {
            enterTime = System.currentTimeMillis();
        } else if (source == good6 || source == good12) {
            enterTime = System.currentTimeMillis();
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        Object source = e.getSource();

        try {
            if (source == good1 || source == good7) {
                mouseExitEvent();
            } else if (source == good2 || source == good8) {
                mouseExitEvent();
            } else if (source == good3 || source == good9) {
                mouseExitEvent();
            } else if (source == good4 || source == good10) {
                mouseExitEvent();
            } else if (source == good5 || source == good11) {
                mouseExitEvent();
            } else if (source == good6 || source == good12) {
                mouseExitEvent();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * 落下商品
     * @param num
     */
    private void addGood(int num) {
        if (num == 0) {
            if (temp != null) temp.setVisible(false);
        } else {
            if (num == 1) temp = good1;
            else if (num == 2) temp = good2;
            else if (num == 3) temp = good3;
            else if (num == 4) temp = good4;
            else if (num == 5) temp = good5;
            else if (num == 6) temp = good6;
            // 隨機位置
            Random r = new Random();
            int x = r.nextInt(300) + 100;
            temp.setBounds(x, 540, 50, 90);
            temp.setVisible(true);
            contentPane.add(temp);
        }
    }

    private JLabel addPriceBar(int x, int y, String text, Font font, int index) {
        JLabel temp = new JLabel(getPrice(goodList, index - 1) + text);
        temp.setOpaque(true);
        temp.setForeground(Color.black);
        temp.setFont(font);
        temp.setBackground(Color.white);
        temp.setHorizontalAlignment(SwingConstants.CENTER);
        temp.setBounds(x, y, 50, 26);
        return temp;
    }

    /**
     * connect to mysql
     */
    private void connectToMySql() throws Exception {
        // 1. import jar

        // 2. set properties

        // 3. create dataBasePool
        Properties prop = new Properties();
        prop.load(new FileInputStream("vendingMachine\\src\\druid.properties"));

        DataSource dataSource = DruidDataSourceFactory.createDataSource(prop);

        conn = dataSource.getConnection();
        createTable_SQL(conn);

    }

    /**
     * select mysql
     * @param conn
     * @throws Exception
     */
    private void selectMySql(Connection conn) throws Exception {
        // sql sentence
        // delete table first
        String delTable = "drop table if exists goods;";

        // create a new table
        String addTable = "create table if not exists goods(" + "    id int primary key auto_increment," + "    name varchar(10)," + "    price int unsigned" + ") comment'商品價目表';";

        // insert data
        String insert = "insert into goods (name, price) values ('葡萄汁', 30), ('草莓牛奶', 35), ('橘子汁', 30), ('果菜汁', 25), ('檸檬汁', 30), ('蜂蜜檸檬', 40);";

        // select data
        String sql = "select * from goods";

        // prepareStatement object
        PreparedStatement deTable = conn.prepareStatement(delTable);
        PreparedStatement newTable = conn.prepareStatement(addTable);
        PreparedStatement insertData = conn.prepareStatement(insert);
        PreparedStatement select = conn.prepareStatement(sql);

        // execute sql
        deTable.executeUpdate();
        newTable.executeUpdate();
        insertData.executeUpdate();
        ResultSet rs = select.executeQuery();

        // dealing with result
        while (rs.next()) {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");

            // new good object, adding it into goodList
            Good good = new Good(id, name, price);
            goodList.add(good);
        }
    }

    private void createTable_SQL(Connection conn) throws Exception {
        String createTable_SQL = "create table if not exists purchasingHistory (" +
                "    ID int primary key auto_increment," +
                "    username varchar(16)," +
                "    good varchar(10)," +
                "    price int unsigned," +
                "    date datetime" +
                ")";
        PreparedStatement pst = conn.prepareStatement(createTable_SQL);
        pst.executeUpdate();
        pst.close();
    }

    private int getPrice(ArrayList<Good> list, int index) {
        return list.get(index).getPrice();
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

        systemInfo = new JDialog();
        systemInfo.getContentPane().add(jLabel1);
        systemInfo.getContentPane().add(jLabel2);

        systemInfo.setTitle("系統提示");

        jLabel1.setBounds(0, 20, 180, 30);
        jLabel2.setSize(150, 30);

        jLabel1.setVerticalAlignment(SwingConstants.CENTER);
        jLabel1.setHorizontalAlignment(SwingConstants.CENTER);

        jLabel2.setVerticalAlignment(SwingConstants.CENTER);
        jLabel2.setHorizontalAlignment(SwingConstants.CENTER);

        systemInfo.setSize(200, 150);
        systemInfo.setAlwaysOnTop(true);
        systemInfo.setModal(true);
        if (order) systemInfo.setLocationRelativeTo(null);
        else {
            jLabel1.setBounds(0, 0, 200, 30);
            jLabel2.setBounds(0, 20, 150, 30);
            systemInfo.setUndecorated(true);
            systemInfo.setModal(false);
            systemInfo.setLocation((int) this.getLocation().getX() + 175, 85);
            systemInfo.setSize(200, 30);
        }

        systemInfo.setVisible(true);
    }

    /**
     * show good data
     * @param index
     */
    private void showGoodsData(int index) {
        // 更改計數器
        count = 1;

        Font font = new Font("微軟正黑體", Font.BOLD, 12);
        Border border = BorderFactory.createEmptyBorder(10, 10, 10, 10);

        JTextArea textArea = new JTextArea();
        textArea.setText(GoodsInfo.goodsInfo[index - 1]);
        textArea.setFont(font);

        // 获取JTextArea的文本宽度和高度
        FontMetrics fontMetrics = textArea.getFontMetrics(textArea.getFont());

        int textHeight = fontMetrics.stringWidth(textArea.getText()) / 5;
        int textWidth = 200;

        textArea.setSize(textWidth, textHeight);

        textArea.setLineWrap(true);
        textArea.setEditable(false);

        goodsData = new JDialog();

        JPanel panel = new JPanel();
        panel.setBorder(border);
        panel.setBackground(Color.LIGHT_GRAY);
        panel.add(textArea);

        goodsData.setContentPane(panel);

        goodsData.setSize(250, textArea.getHeight());

        goodsData.setTitle("商品簡介");
        goodsData.setAlwaysOnTop(true);

        ArrayList<Double> list = dataLocation(index);
        int x = list.get(0).intValue();
        int y = list.get(1).intValue();
        goodsData.setLocation(x, y);
        goodsData.setVisible(true);

        if (systemInfo != null) systemInfo.setVisible(false);
    }

    /**
     * remove good data
     */
    private void removeGoodsData() {
        if (goodsData != null) {
            count = 0;
            goodsData.dispose();
            systemInfoOrder = 0;
        }
    }

    /**
     * 判斷是否有足夠的金錢購買商品
     */
    private void affordable(int index) throws Exception {
        int price = goodList.get(index).getPrice();
        if (coinSystem.money >= price) {
            coinSystem.money -= price;
            initImage(index + 1);
            addRecord(goodList.get(index));
        } else {
            int need = price - coinSystem.money;
            showJDialog("所需金額不足", "請繼續投幣 (還需要" + need + "元)", true);
        }
    }

    /**
     * 調整data的位置
     * @param index
     * @return
     */
    private ArrayList<Double> dataLocation(int index) {
        double x = 0, y = 0;
        if (index == 1) {
            x = good5.getX() + 490;
            y = good5.getY() + 80;
        } else if (index == 2) {
            x = good6.getX() + 290;
            y = good6.getY() + 80;
        } else if (index == 3) {
            x = good4.getX() + 490;
            y = good4.getY() + 80;
        } else if (index == 4) {
            x = good2.getX() + 490;
            y = good2.getY() + 30;
        } else if (index == 5) {
            x = good3.getX() + 390;
            y = good3.getY() + 30;
        } else if (index == 6) {
            x = good1.getX() + 490;
            y = good1.getY() + 30;
        }
        ArrayList<Double> list = new ArrayList<>();
        list.add(x);
        list.add(y);
        return list;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Object source = e.getSource();
        if (source == good1 || source == good7) {
            showSystemInfo();
        } else if (source == good2 || source == good8) {
            showSystemInfo();
        } else if (source == good3 || source == good9) {
            showSystemInfo();
        } else if (source == good4 || source == good10) {
            showSystemInfo();
        } else if (source == good5 || source == good11) {
            showSystemInfo();
        } else if (source == good6 || source == good12) {
            showSystemInfo();
        }

    }

    /**
     * show system hint
     */
    private void showSystemInfo() {
        stayTime = System.currentTimeMillis();
        if (stayTime - enterTime >= 750 && systemInfoOrder == 0) {
            showJDialog("點擊右鍵查看商品信息", "", false);
            systemInfoOrder = 1;
        }
    }

    private void mouseExitEvent() throws Exception {
        mouseExit();
    }

    /**
     * 添加用戶歷史紀錄
     */
    private void addRecord(Good good) throws SQLException {
        PreparedStatement pst = conn.prepareStatement(newDataInsert_SQL);
        pst.setString(1, user);
        pst.setString(2, good.toString());
        pst.setInt(3, good.getPrice());
        pst.executeUpdate();
        pst.close();
    }

    /**
     * 獲得用戶歷史紀錄
     * @return
     * @throws Exception
     */
    private ResultSet getHistory() throws Exception {
        String getHistory_SQL = "select ID, good, price, date from purchasingHistory where username = ? order by date desc limit 0, 20";
        PreparedStatement pst = conn.prepareStatement(getHistory_SQL);
        pst.setString(1, user);
        ResultSet rs = pst.executeQuery();
        return rs;
    }

    private void mouseExit() throws Exception {
        removeGoodsData();
        enterTime = System.currentTimeMillis();
        stayTime = 0;
        systemInfoOrder = 0;
        if (systemInfo != null) systemInfo.dispose();
        Thread.sleep(100);
    }
}
