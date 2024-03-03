public abstract class Product implements Comparable<Product> {
    private String productID;
    private String productName;
    private double price;
    private int numAvailPro;

    public Product(String productID,String productName,double price,int numAvailPro ){
        this.productID=productID;
        this.productName=productName;
        this.price=price;
        this.numAvailPro=numAvailPro;
    }

    public Product() {
    }

    @Override
    public int compareTo(Product product){
        return this.getProductID().compareTo(product.getProductID());
    }
    public String getProductID(){
        return productID;
    }
    public void setProductID(String productID){
        this.productID=productID;
    }
    public String getProductName(){
        return productName;
    }
    public void setProductName(String productName){
        this.productName=productName;
    }
    public int getNumAvailPro(){
        return numAvailPro;
    }
    public void setNumAvailPro(int numAvailPro){
        this.numAvailPro=numAvailPro;
    }
    public double getPrice(){
        return price;
    }
    public void setPrice(double price){
        this.price=price;
    }
    public void buyAvailableItems() throws RuntimeException {
        if (this.numAvailPro > 0) {
            this.numAvailPro -= 1;
        } else {
            throw new RuntimeException("No  items to buy");
        }
    }

    public String getCartProductDetails(Product product) {

        if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return "<html>"+clothing.getProductID() +"<br>"+
                    clothing.getProductName() +"<br>"+
                    clothing.getSize() +","+
                    clothing.getColour()+"</html>";
        } else if (product instanceof Electronics) {
            Electronics electronics = (Electronics) product;
            return "<html>" + electronics.getProductID() +"<br>"+
                    electronics.getProductName() +"<br>"+
                    electronics.getBrand() +","+
                    electronics.getWarrantyPeriod()+" months </html>";
        } else {
            // Handle other product types if needed
            return "ID: " + product.getProductID() +
                    ", Name: " + product.getProductName();
        }
    }
}
