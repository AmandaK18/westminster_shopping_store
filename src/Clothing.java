public class Clothing extends Product {
    private String colour;
    private String size;
    public Clothing(String productID,String productName,double price,int numAvailPro,String colour,String size){
        super(productID,productName,price,numAvailPro);
        this.colour=colour;
        this.size=size;
    }


    public String getColour(){
        return colour;
    }
    public void setColour(String colour){
        this.colour=colour;
    }
    public String getSize(){
        return size;
    }
    public void setSize(String size){
        this.size=size;
    }


}
