import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PizzaGUIFrame extends JFrame {

    private JRadioButton thinCrustRadioButton, regularCrustRadioButton, deepDishCrustRadioButton;
    private JComboBox<String> sizeComboBox;
    private JCheckBox topping1CheckBox, topping2CheckBox, topping3CheckBox, topping4CheckBox, topping5CheckBox, topping6CheckBox;
    private JTextArea orderTextArea;
    private JButton orderButton, clearButton, quitButton;

    public PizzaGUIFrame() {
        setTitle("Pizza Order");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        thinCrustRadioButton = new JRadioButton("Thin");
        regularCrustRadioButton = new JRadioButton("Regular");
        deepDishCrustRadioButton = new JRadioButton("Deep-dish");

        sizeComboBox = new JComboBox<>(new String[]{"Small", "Medium", "Large", "Super"});

        topping1CheckBox = new JCheckBox("Pepperoni");
        topping2CheckBox = new JCheckBox("Sausage");
        topping3CheckBox = new JCheckBox("Bacon");
        topping4CheckBox = new JCheckBox("Ham");
        topping5CheckBox = new JCheckBox("Peppers");
        topping6CheckBox = new JCheckBox("Pineapple");

        orderTextArea = new JTextArea(10, 30);
        orderTextArea.setEditable(false);

        orderButton = new JButton("Order");
        clearButton = new JButton("Clear");
        quitButton = new JButton("Quit");

        JPanel crustPanel = createTitledBorderPanel("Type of Crust", thinCrustRadioButton, regularCrustRadioButton, deepDishCrustRadioButton);
        JPanel sizePanel = createTitledBorderPanel("Size", sizeComboBox);
        JPanel toppingsPanel = createTitledBorderPanel("Toppings", topping1CheckBox, topping2CheckBox, topping3CheckBox, topping4CheckBox, topping5CheckBox, topping6CheckBox);

        JPanel orderPanel = new JPanel(new BorderLayout());
        orderPanel.setBorder(BorderFactory.createTitledBorder("Order Details"));
        orderPanel.add(new JScrollPane(orderTextArea), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(orderButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(quitButton);

        add(crustPanel, BorderLayout.NORTH);
        add(sizePanel, BorderLayout.WEST);
        add(toppingsPanel, BorderLayout.EAST);
        add(orderPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayOrder();
            }
        });

        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                confirmQuit();
            }
        });

        pack();
        setLocationRelativeTo(null);
    }

    private JPanel createTitledBorderPanel(String title, JComponent... components) {
        JPanel panel = new JPanel(new GridLayout(components.length, 1));
        panel.setBorder(BorderFactory.createTitledBorder(title));
        for (JComponent component : components) {
            panel.add(component);
        }
        return panel;
    }

    private void displayOrder() {
        StringBuilder orderDetails = new StringBuilder();

        if (thinCrustRadioButton.isSelected() || regularCrustRadioButton.isSelected() || deepDishCrustRadioButton.isSelected()) {
            if (sizeComboBox.getSelectedIndex() != -1) {
                double baseCost;
                switch (sizeComboBox.getSelectedIndex()) {
                    case 0: baseCost = 8.00; break; // Small
                    case 1: baseCost = 12.00; break; // Medium
                    case 2: baseCost = 16.00; break; // Large
                    case 3: baseCost = 20.00; break; // Super
                    default: baseCost = 0.00;
                }

                int toppingsCount = 0;
                double toppingsCost = 0.00;
                JCheckBox[] toppings = {topping1CheckBox, topping2CheckBox, topping3CheckBox, topping4CheckBox, topping5CheckBox, topping6CheckBox};
                for (JCheckBox topping : toppings) {
                    if (topping.isSelected()) {
                        toppingsCount++;
                        toppingsCost += 1.00;
                    }
                }

                double subTotal = baseCost + toppingsCost;
                double tax = 0.07 * subTotal;
                double total = subTotal + tax;

                orderDetails.append("=========================================\n");
                orderDetails.append("Type of Crust & Size\t\t\tPrice\n");
                orderDetails.append("=========================================\n");

                orderDetails.append(String.format("Crust: %s & Size: %s\t\t$%8.2f\n", getSelectedCrust(), sizeComboBox.getSelectedItem(), baseCost));

                orderDetails.append("Toppings:\n");
                for (int i = 0; i < toppings.length; i++) {
                    if (toppings[i].isSelected()) {
                        orderDetails.append(String.format("\t- %s\t\t\t$1.00\n", toppings[i].getText()));
                    }
                }

                orderDetails.append(String.format("Sub-total:\t\t\t\t$%8.2f\n", subTotal));
                orderDetails.append(String.format("Tax:\t\t\t\t$%8.2f\n", tax));
                orderDetails.append("---------------------------------------------------------------------\n");
                orderDetails.append(String.format("Total:\t\t\t\t$%8.2f\n", total));
                orderDetails.append("=========================================\n");

                orderTextArea.setText(orderDetails.toString());
            } else {
                JOptionPane.showMessageDialog(this, "Please select a size.", "Size Selection", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a crust type.", "Crust Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void clearForm() {
        thinCrustRadioButton.setSelected(false);
        regularCrustRadioButton.setSelected(false);
        deepDishCrustRadioButton.setSelected(false);
        sizeComboBox.setSelectedIndex(0);
        topping1CheckBox.setSelected(false);
        topping2CheckBox.setSelected(false);
        topping3CheckBox.setSelected(false);
        topping4CheckBox.setSelected(false);
        topping5CheckBox.setSelected(false);
        topping6CheckBox.setSelected(false);
        orderTextArea.setText("");
    }

    private void confirmQuit() {
        int option = JOptionPane.showConfirmDialog(this, "Are you sure you want to quit?", "Quit Confirmation", JOptionPane.YES_NO_OPTION);
        if (option == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }

    private String getSelectedCrust() {
        if (thinCrustRadioButton.isSelected()) {
            return "Thin";
        } else if (regularCrustRadioButton.isSelected()) {
            return "Regular";
        } else if (deepDishCrustRadioButton.isSelected()) {
            return "Deep-dish";
        } else {
            return "";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PizzaGUIFrame pizzaGUIFrame = new PizzaGUIFrame();
            pizzaGUIFrame.setVisible(true);
        });
    }
}

