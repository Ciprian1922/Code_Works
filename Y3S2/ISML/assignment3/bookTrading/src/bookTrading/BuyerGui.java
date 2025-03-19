package bookTrading;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BuyerGui extends JFrame {
    private JTextField bookField;
    private JButton buyButton;
    private BookBuyerAgent buyerAgent;

    public BuyerGui(BookBuyerAgent agent) {
        super("Book Buyer");
        buyerAgent = agent;

        setLayout(new GridLayout(2, 1));
        bookField = new JTextField(20);
        buyButton = new JButton("Buy Book");

        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookTitle = bookField.getText().trim();
                if (!bookTitle.isEmpty()) {
                    buyerAgent.startBuyingProcess(bookTitle);
                }
            }
        });

        add(bookField);
        add(buyButton);
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}
