package Model;



public class Product_model {

    String product_name;
    long ID ;
    String UID;
    String message ;
    long pending ;
    long status  ;
    float price; // TODO : Fetch this value from server
    String Gametype;



    String end_date;
    String end_time;

    String product_image;

    @Override
    public String toString() {
        return product_name ;
    }
// String product_id;
    // String category_id;
    //  String product_description;
    //  String deal_price;
    //  String start_date;
    //  String start_time;
    // String status;
    //  String in_stock;
    //  String unit_value;
    //   String unit;
    //   String increament;
    //   String rewards;
    //  String stock;
    //  String title;

    public Product_model(){}

    public Product_model(String product_name,String Gametype ,long ID, String UID, String message, long pending, long status, float price) {
        this.product_name = product_name;
        this.ID = ID;
        this.UID = UID;
        this.Gametype = Gametype;
        this.message = message;
        this.pending = pending;
        this.status = status;
        this.price = price;
        this.end_date = "01/01/0000";
        this.end_time = "00:00";

    }

    public Product_model(String product_name, String end_date, String end_time, String product_image, float price)
    {
        this.product_image=product_image;
        this.end_date=end_date;
        this.end_time=end_time;
        this.price=price;
        this.product_name=product_name;
    }


    public String getGametype() {
        return Gametype;
    }

    public void setGametype(String gametype) {
        Gametype = gametype;
    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getPending() {
        return pending;
    }

    public void setPending(long pending) {
        this.pending = pending;
    }

    public long getStatus() {
        return status;
    }

    public void setStatus(long status) {
        this.status = status;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public float getPrice() {
        return price;
    }
    public String getPriceStr() {
        return String.valueOf(price);
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }


}