import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class ManagerFrame extends JFrame {
    JLabel label1,productDetailsLabel;
    JComboBox dropDownMenu;
    DefaultTableModel tableModel;
    JTable productTable;

    JButton cartButton,addToCartButton;
    private CartGUI cartGUI;

    private Map<Product,Integer > productQuantityMap;

    private ArrayList<Product> products;

    private ArrayList<Product> productsToDisplay = new ArrayList<>();
    public ManagerFrame(ArrayList<Product> products, User user){
        this.productQuantityMap=new HashMap<>();
        this.products= products;
        String[] categories={"All","Electronic","Clothing"};

        label1=new JLabel();
        label1.setText("Select Product Category");
        label1.setBounds(50, 60, 200, 30);

        dropDownMenu=new JComboBox<>(categories);
        dropDownMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedCategory = (String) dropDownMenu.getSelectedItem();
                displayProducts(products, selectedCategory);
            }
        });

        cartButton = new JButton("Shopping Cart");
        cartButton.setLayout(new FlowLayout(FlowLayout.RIGHT));
        cartButton.setVisible(true);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Product ID");
        tableModel.addColumn("Category");
        tableModel.addColumn("Price");
        tableModel.addColumn("Info");

        productTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(productTable);

        // Set the preferred height of the table
        scrollPane.setPreferredSize(new Dimension(500, 300)); // Adjust the width as needed
        int preferredTableHeight = 300;  // Set your preferred height here
        scrollPane.setPreferredSize(new Dimension(scrollPane.getPreferredSize().width, preferredTableHeight));



        productTable.getColumnModel().getColumn(4).setPreferredWidth(250);
        productTable.setRowSelectionAllowed(true);
        productTable.setRowHeight(30);


        productDetailsLabel = new JLabel();
        productDetailsLabel.setVisible(false);  // Initialize as not visible
        productDetailsLabel.setVerticalAlignment(JLabel.TOP);  // Align text to the top


        addToCartButton = new JButton("Add to Cart");
        addToCartButton.setVisible(false);  // Initialize as not visible
        cartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (cartGUI == null) {
                    // If cartGUI is null, create a new instance
                    cartGUI = new CartGUI(productQuantityMap, user);
                }else {
                    cartGUI.updateCart();
                }

                cartGUI.setVisible(true);

            }
        });

        addToCartButton.addActionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                Product selectedProduct = productsToDisplay.get(selectedRow);
                selectedProduct.buyAvailableItems();
                findAndAdd(selectedProduct.getProductID());
                displayProductDetails(selectedProduct);

                if (cartGUI == null) {
                    cartGUI = new CartGUI(productQuantityMap, user);
                } else {
                    cartGUI.updateCart();
                }

            }
        });

        JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
        separator.setPreferredSize(new Dimension(500, 10));  // Adjust the size of the separator

        JPanel panel = new JPanel();
        panel.setVisible(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(separator);

        // Create a sub-panel for the product details and set its layout to FlowLayout with left alignment
        JPanel productDetailsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        productDetailsPanel.add(productDetailsLabel);

        panel.add(productDetailsPanel);

        // Create a sub-panel for the "Add to Cart" button and set its layout to FlowLayout with center alignment
        JPanel addToCartPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        addToCartPanel.add(addToCartButton);

        panel.add(addToCartPanel);

        productTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {

                displayProductDetails(productsToDisplay.get(selectedRow));
                panel.setVisible(true);
                addToCartButton.setVisible(true);  // Show the "Add to Cart" button
//
            }
        });



        this.setLayout(new FlowLayout());
        this.add(label1);
        this.add(dropDownMenu);
        this.add(cartButton);
        this.add(scrollPane);  // Add the JScrollPane to the frame
        this.add(panel);

    }

    private void findAndAdd(String selectedProductId) {
        for (Product product : productsToDisplay) {
            try {
                if (product.getProductID().equals(selectedProductId)) {
                    int quantity = productQuantityMap.getOrDefault(product, 0);
                    productQuantityMap.put(product, quantity + 1);
                    break;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "No items");
            }
        }
    }


    private void displayProducts(ArrayList<Product> shoppingManager, String selectedCategory) {
        productsToDisplay.clear();

        if ("All".equals(selectedCategory)) {
            productsToDisplay.addAll(shoppingManager);
        } else if ("Electronic".equalsIgnoreCase(selectedCategory)) {
            productsToDisplay.addAll(getElectronics(shoppingManager));
        } else if ("Clothing".equalsIgnoreCase(selectedCategory)) {
            productsToDisplay.addAll(getClothing(shoppingManager));
        }

        // Clear existing data in the table
        tableModel.setRowCount(0);

        for (Product product : productsToDisplay) {
            String categoryName = getCategoryName(product);
            String info = getProductInfo(product);

            Object[] rowData = {
                    product.getProductName(),
                    product.getProductID(),
                    categoryName,
                    String.format("%.2f", product.getPrice()),
                    info
            };

            tableModel.addRow(rowData);
        }
    }


    public ArrayList<Product> getElectronics(ArrayList<Product> productList){
        ArrayList<Product> electronicsList=new ArrayList<>();

        for(Product product:productList){
            if(product instanceof Electronics){
                electronicsList.add(product);
            }
        }
        return electronicsList;
    }

    public ArrayList<Product> getClothing(ArrayList<Product> productList){
        ArrayList<Product> clothingList=new ArrayList<>();

        for(Product product:productList){
            if(product instanceof Clothing){
                clothingList.add( product);
            }
        }
        return clothingList;
    }

    private void displayProductDetails(Product selectedProduct) {
        // Get the details of the selected product
        String productDetails = getProductDetails(selectedProduct);

        // Set the text of the JLabel
        productDetailsLabel.setText("<html>" + productDetails.replaceAll("\n", "<br>") + "</html>");

        // Make the JLabel visible
        productDetailsLabel.setVisible(true);
    }

    private String getCategoryName(Product product) {
        if (product instanceof Electronics) {
            return "Electronic";
        } else if (product instanceof Clothing) {
            return "Clothing";
        }
        return "Unknown";
    }

    private String getProductInfo(Product product) {
        if (product instanceof Electronics) {
            Electronics electronic = (Electronics) product;
            return String.format("Brand: %s, Warranty Period: %d", electronic.getBrand(), electronic.getWarrantyPeriod());
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            return String.format("Size: %s, Color: %s", clothing.getSize(), clothing.getColour());
        } else {
            return "Unknown";
        }
    }

    private String getProductDetails(Product product) {
        // Modify this method based on the information you want to display for each product type
        StringBuilder details = new StringBuilder();

        details.append("Product Details \n \n");
        details.append("Product Name: ").append(product.getProductName()).append("\n");
        details.append("Product ID: ").append(product.getProductID()).append("\n");
        details.append("Category: ").append(getCategoryName(product)).append("\n");
        details.append("Price: ").append(String.format("%.2f", product.getPrice())).append("\n");


        // Display additional details based on the product type
        if (product instanceof Electronics) {
            Electronics electronic = (Electronics) product;
            details.append("Brand: ").append(electronic.getBrand()).append("\n");
            details.append("Warranty Period: ").append(electronic.getWarrantyPeriod()).append("\n");
        } else if (product instanceof Clothing) {
            Clothing clothing = (Clothing) product;
            details.append("Size: ").append(clothing.getSize()).append("\n");
            details.append("Color: ").append(clothing.getColour()).append("\n");
        }

        details.append("number of available products: ").append(product.getNumAvailPro()).append("\n");

        details.append("\n");
        return details.toString();
    }

}

public class WestminsterShoppingCentreGUI {
    public static void main(String[] args) {
        WestminsterShoppingManager shoppingManager = new WestminsterShoppingManager();
        User user= User.getUserInput();

        ManagerFrame frame=new ManagerFrame(shoppingManager.getProductList(),user);
        frame.setTitle("Westminster Shopping Centre");
        frame.setSize(700,700);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
