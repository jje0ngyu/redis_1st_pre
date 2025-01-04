package thread;

public class LatestOrderDatabase {
    private String userName;
    private String itemName;
    private int orderAmount;

    public LatestOrderDatabase(String userName, String itemName, int orderAmount) {
        this.userName = userName;
        this.itemName = itemName;
        this.orderAmount = orderAmount;
    }

    public String getUserName() {
        return userName;
    }

    public String getItemName() {
        return itemName;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public String getLatestOrderInfo() {
        return "User : " + this.userName + ", Item : " + this.itemName + ", Order Amount : " + this.orderAmount;
    }
}
