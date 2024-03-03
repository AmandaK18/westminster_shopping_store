

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Map;

public class CartGUI extends JFrame {
    private DefaultTableModel cartTableModel;
    private JTable cartTable;
    private JScrollPane scrollPane;
    private JPanel mainPanel;
    private JLabel totalLabel, firstDiscountLabel, categoryDiscountLabel, finalTotalLabel;
    private Map<Product, Integer> productQuantityMap;
    private User user;

    public CartGUI(Map<Product, Integer> productQuantity, User user) {
        this.user=user;
        this.productQuantityMap = productQuantity;

        cartTableModel = new DefaultTableModel();
        cartTableModel.addColumn("Product");
        cartTableModel.addColumn("Price");
        cartTableModel.addColumn("Quantity");

        cartTable = new JTable(cartTableModel);
        scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(200, 200));
        int rowHeight = 50;
        cartTable.setRowHeight(rowHeight);

        totalLabel = new JLabel("Total: $0.00");
        firstDiscountLabel = new JLabel("First Purchase Discount(10%): $0.00");
        categoryDiscountLabel = new JLabel("Three Items in the Same Category Discount(20%): $0.00");
        finalTotalLabel = new JLabel("Final Total: $0.00");

        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        firstDiscountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        categoryDiscountLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        finalTotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        int labelMargin = 10;
        totalLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, labelMargin, 0));
        firstDiscountLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, labelMargin, 0));
        categoryDiscountLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, labelMargin, 0));
        finalTotalLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, labelMargin, 0));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new GridLayout(4, 1));
        labelPanel.add(totalLabel);
        labelPanel.add(firstDiscountLabel);
        labelPanel.add(categoryDiscountLabel);
        labelPanel.add(finalTotalLabel);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(labelPanel, BorderLayout.EAST);

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 100, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);


        updateCart();


        setTitle("Shopping Cart");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }

    public void updateCart() {
        cartTableModel.setRowCount(0);

        double total = 0.0;

        for (Map.Entry<Product, Integer> entry : productQuantityMap.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();

            double itemTotal = product.getPrice() * quantity;

            total += itemTotal;

            cartTableModel.addRow(new Object[]{product.getCartProductDetails(product), product.getPrice(), quantity});
        }

        double firstPurchaseDiscount = firstPurchaseDiscount(total);
        double categoryDiscount = calculateCategoryDiscount(total);

        double finalTotal = total - firstPurchaseDiscount - categoryDiscount;

        totalLabel.setText(String.format("Total: $%.2f", total));
        firstDiscountLabel.setText(String.format("First Purchase Discount(10%%): -$%.2f", firstPurchaseDiscount));
        categoryDiscountLabel.setText(String.format("Three Items in the Same Category Discount(20%%): -$%.2f", categoryDiscount));
        finalTotalLabel.setText(String.format("Final Total: $%.2f", finalTotal));
    }


    private double firstPurchaseDiscount(Double total) {
        if(user.isFirstPurchase()){
            return total*0.10;
        }
        return 0.0;
    }

    private double calculateCategoryDiscount(double total) {
        for (int value:productQuantityMap.values()){
            if (value>=3){
                return total*0.20;
            }
        }

        return 0.0; // Placeholder
    }
}

