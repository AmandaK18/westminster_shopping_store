import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.util.stream.Collectors;

public class WestminsterShoppingManager implements ShoppingManager{
    private ArrayList<Product> productList;

    public ArrayList<Product> getProductList(){
        return productList;
    }



    public WestminsterShoppingManager(){
        this.productList=new ArrayList<>();
        readFromFile("ProductFile.txt");
    }
    @Override
    public void addProduct(){
        while(true){
            if (productList.size()>=50) {
                System.out.println("product limit reached");
                break;
            }
            Scanner sc = new Scanner(System.in);
            System.out.print("Enter the type of the product (E / C): ");
            String type=sc.nextLine().toUpperCase();
            if("E".equals(type)){
                addElectronic();
                break;
            }else if ("C".equals(type)){
                addClothing();
                break;
            }else{
                System.out.println("Invalid product type!!");
            }
        }
    }


    public void addElectronic(){
        Scanner sc=new Scanner(System.in);

        String produtName = Validation.StringOnlyValidator("Enter product Name: ");
        String productID = Validation.charValidation("Enter the product Id (use EL as the prefix): ").toUpperCase();
        double price = Validation.doubleValidator("Enter the product price: ");
        int numAvailPro = Validation.intValidator("Enter the number of available products: ");


        System.out.print("Enter the brand of the electronic product: ");
        String brand=sc.nextLine().trim();
        int warrantyPeriod=Validation.intValidator("Enter the warranty period of the electronic product: MON ");

        Electronics ele=new Electronics(productID,produtName,price,numAvailPro,brand,warrantyPeriod);
        productList.add(ele);
        System.out.println("Product added successfully!!");
    }
    public void addClothing(){
        Scanner sc=new Scanner(System.in);

        String produtName = Validation.StringValidator("Enter the product name: ");
        String productID = Validation.charValidation("Enter the product Id (use CL as the prefix): ").toUpperCase();
        double price = Validation.doubleValidator("Enter the product price: ");
        int numAvailPro = Validation.intValidator("Enter the number of available products: ");


        System.out.print("Enter the colour of the clothing: ");
        String colour=sc.nextLine().trim();
        String size=Validation.sizeValidation("Enter the size of the clothing: ");

        Clothing clo=new Clothing(productID,produtName,price,numAvailPro,colour,size);
        productList.add(clo);
        System.out.println("Product added successfully!!");
    }
    @Override

    public void deleteProduct(Product product) {
        Scanner sc = new Scanner(System.in);

        String productIDToDelete = Validation.charValidation("Enter the product ID of the product which needs to be deleted: ").trim().toUpperCase();
        boolean found = false;

        for (Product currentProduct : productList) {
            if (currentProduct.getProductID().trim().toUpperCase().equals(productIDToDelete)) {
                productList.remove(currentProduct);
                found = true;
                System.out.println("Product deleted successfully.");
                break;
            }
        }

        if (!found) {
            System.out.println("Product " + productIDToDelete + " not found.");
        }
    }

    @Override

    public void printProductList(){
        List<Product> sortedList = new ArrayList<>(productList);

        // Sort the copied list based on natural order (compareTo method)
        Collections.sort(sortedList, Comparator.comparing(Product::getProductID));

        for(Product product : sortedList){
            if(product.getProductID().startsWith("EL")){
                System.out.println("Electronic product");
                displayDetails(product);
                System.out.println("Brand : "+((Electronics) product).getBrand());
                System.out.println("Warranty period : "+((Electronics) product).getWarrantyPeriod());
            }else if(product.getProductID().startsWith("CL")){
                System.out.println("Clothing product");
                displayDetails(product);
                System.out.println("Colour : "+((Clothing) product).getColour());
                System.out.println("Size : "+((Clothing) product).getSize());
            }
        }
    }
    public void displayDetails(Product product){
        System.out.println("Product ID : "+product.getProductID());
        System.out.println("Product name : "+product.getProductName());
        System.out.println("Number of available products : "+product.getNumAvailPro());
        System.out.println("Product price : "+product.getPrice());
    }

    @Override

    public void saveToFile(){
        try(
                BufferedWriter writer = new BufferedWriter(new FileWriter("ProductFile.txt"))){
            for(Product product: productList){
                if(product instanceof Electronics){
                    Electronics electronic=(Electronics) product;
                    String line= "ID: "+product.getProductID()+","+
                            "Name: "+product.getProductName()+","+
                            "Price: "+product.getPrice()+","+
                            "Num of products: "+product.getNumAvailPro()+","+
                            "Brand: "+((Electronics) product).getBrand()+","+
                            "Warranty period: "+((Electronics) product).getWarrantyPeriod();
                    writer.write(line);
                    writer.newLine();
                }else if(product instanceof Clothing){
                    Clothing clothing=(Clothing) product;
                    String line= "ID: "+product.getProductID()+","+
                            "Name: "+product.getProductName()+","+
                            "Price: "+product.getPrice()+","+
                            "Num of products: "+product.getNumAvailPro()+","+
                            "Colour: "+((Clothing) product).getColour()+","+
                            "Size: "+((Clothing) product).getSize();
                    writer.write(line);
                    writer.newLine();
                }
            }
            writer.close();
            System.out.println("Product list saved to file!!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void readFromFile(String ProductFile) {
        try (BufferedReader reader = new BufferedReader(new FileReader("ProductFile.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String productID = parts[0].substring(4).trim();
                    String productName = parts[1].substring(6).trim();
                    double price = Double.parseDouble(parts[2].substring(parts[2].indexOf(":") + 1).trim());
                    int numAvailPro = Integer.parseInt(parts[3].substring(17).trim());

                    if (parts[4].startsWith("Brand")) {
                        // Electronic product
                        String brand = parts[4].substring(7).trim();
                        int warrantyPeriod = Integer.parseInt(parts[5].substring(16).trim());

                        Electronics electronic = new Electronics(productID, productName, price, numAvailPro, brand, warrantyPeriod);
                        productList.add(electronic);
                    } else if (parts[4].startsWith("Colour")) {
                        // Clothing product
                        String colour = parts[4].substring(8).trim();
                        String size = parts[5].substring(6).trim();

                        Clothing clothing = new Clothing(productID, productName, price, numAvailPro, colour, size);
                        productList.add(clothing);
                    }
                }
            }
//            System.out.println("Product list loaded from file: " + ProductFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        WestminsterShoppingManager shoppingManager=new WestminsterShoppingManager();
        shoppingManager.menu();
    }
    public void displayMenu(){
        System.out.println("Select an option");
        System.out.println("1 => Add a product");
        System.out.println("2 => Delete a product");
        System.out.println("3 => Print the list of products");
        System.out.println("4 => Save products to a file");
        System.out.println("5 => Read from the file");
        System.out.println("0 => Exit");
    }

    public void menu(){

        Product product=new Product() {};
        WestminsterShoppingManager shoppingManager=new WestminsterShoppingManager();
        boolean exit=false;
        while (!exit){

            displayMenu();
            int option=Validation.intValidator("Enter your option: ");
            switch (option){
                case 1:
                    addProduct();
                    break;
                case 2:
                    deleteProduct(product);
                    break;
                case 3:
                    printProductList();
                    break;
                case 4:
                    saveToFile();
                    break;
                case 5:
                    shoppingManager.readFromFile("ProductFile.txt");
                    break;
                case 0:
                    exit=true;
                    System.out.println("Exiting the program.");
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }
        }
    }

}
