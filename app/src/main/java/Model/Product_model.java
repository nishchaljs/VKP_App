package Model;



public class Product_model {

    String product_name;
    long ID ;
    String message ;
    int pending ;
    int status  ;



    String end_date;
    String end_time;
    String price;
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

    public Product_model(String product_name, String end_date, String end_time,String product_image, String price)
    {
        this.product_image=product_image;
        this.end_date=end_date;
        this.end_time=end_time;
        this.price=price;
        this.product_name=product_name;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }


}
