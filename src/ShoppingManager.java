public interface ShoppingManager {

    void addProduct();
    void deleteProduct(Product product);
    void printProductList();
    void saveToFile();
    void readFromFile(String ProductFile);
}
