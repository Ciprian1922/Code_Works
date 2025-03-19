package bookTrading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookSellerGui extends JFrame {
    private BookSellerAgent sellerAgent;

    public BookSellerGui(BookSellerAgent agent) {
        this.sellerAgent = agent;
        setTitle("Seller GUI");
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));

        JLabel bookLabel = new JLabel("Book Title:");
        JTextField bookField = new JTextField();
        JLabel priceLabel = new JLabel("Price:");
        JTextField priceField = new JTextField();
        JButton addButton = new JButton("Add Book");

        add(bookLabel);
        add(bookField);
        add(priceLabel);
        add(priceField);
        add(addButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String title = bookField.getText();
                int price = Integer.parseInt(priceField.getText());
                sellerAgent.updateCatalogue(title, price);
                JOptionPane.showMessageDialog(BookSellerGui.this, "Book Added!");
            }
        });

        setVisible(true);
    }
}
