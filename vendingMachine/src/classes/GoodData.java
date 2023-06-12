package classes;

public class GoodData {

    private String goodName;
    private int price;
    private String date;

    public GoodData() {
    }

    public GoodData(String goodName, int price, String date) {
        this.goodName = goodName;
        this.price = price;
        this.date = date;
    }

    /**
     * 获取
     * @return goodName
     */
    public String getGoodName() {
        return goodName;
    }

    /**
     * 设置
     * @param goodName
     */
    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    /**
     * 获取
     * @return price
     */
    public int getPrice() {
        return price;
    }

    /**
     * 设置
     * @param price
     */
    public void setPrice(int price) {
        this.price = price;
    }

    /**
     * 获取
     * @return date
     */
    public String getDate() {
        return date;
    }

    /**
     * 设置
     * @param date
     */
    public void setDate(String date) {
        this.date = date;
    }

    public String toString() {
        return "goodData{goodName = " + goodName + ", price = " + price + ", date = " + date + "}";
    }
}
