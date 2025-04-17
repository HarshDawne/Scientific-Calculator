import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;

public class ScientificCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private String currentInput = "";
    private double firstNumber = 0;
    private String operator = "";
    private boolean isNewInput = true;

    public ScientificCalculator() {
        setTitle("Scientific Calculator");
        setSize(450, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display field (Styled)
        display = new JTextField();
        display.setFont(new Font("Arial", Font.BOLD, 28));
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setBackground(Color.BLACK);
        display.setForeground(Color.GREEN);
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(display, BorderLayout.NORTH);

        // Panel for buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 5, 5, 5));
        panel.setBackground(Color.DARK_GRAY);

        String[] buttons = {
            "7", "8", "9", "/", "sin",
            "4", "5", "6", "*", "cos",
            "1", "2", "3", "-", "tan",
            "0", ".", "=", "+", "log",
            "√", "xʸ", "π", "e", "ln",
            "C", "DEL", "n!", "(", ")"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            button.setFont(new Font("Arial", Font.BOLD, 18));
            button.setBackground(Color.BLACK);
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            button.addActionListener(this);
            panel.add(button);
        }

        add(panel, BorderLayout.CENTER);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        try {
            switch (command) {
                case "C":
                    currentInput = "";
                    operator = "";
                    firstNumber = 0;
                    display.setText("");
                    break;
                case "DEL":
                    if (!currentInput.isEmpty()) {
                        currentInput = currentInput.substring(0, currentInput.length() - 1);
                        display.setText(currentInput);
                    }
                    break;
                case "=":
                    if (!currentInput.isEmpty()) {
                        double secondNumber = Double.parseDouble(currentInput);
                        double result = calculate(firstNumber, secondNumber, operator);
                        display.setText(new DecimalFormat("0.######").format(result));
                        currentInput = String.valueOf(result);
                        isNewInput = true;
                    }
                    break;
                case "+": case "-": case "*": case "/": case "xʸ":
                    if (!currentInput.isEmpty()) {
                        firstNumber = Double.parseDouble(currentInput);
                        operator = command;
                        currentInput = "";
                    }
                    break;
                case "sin": case "cos": case "tan": case "log": case "ln": case "√": case "n!":
                    if (!currentInput.isEmpty()) {
                        double value = Double.parseDouble(currentInput);
                        double specialResult = applyScientificFunction(value, command);
                        display.setText(new DecimalFormat("0.######").format(specialResult));
                        currentInput = String.valueOf(specialResult);
                        isNewInput = true;
                    }
                    break;
                case "π":
                    currentInput = String.valueOf(Math.PI);
                    display.setText(currentInput);
                    break;
                case "e":
                    currentInput = String.valueOf(Math.E);
                    display.setText(currentInput);
                    break;
                default:
                    if (isNewInput) {
                        currentInput = command;
                        isNewInput = false;
                    } else {
                        currentInput += command;
                    }
                    display.setText(currentInput);
            }
        } catch (Exception ex) {
            display.setText("Error");
            isNewInput = true;
        }
    }

    private double calculate(double a, double b, String op) {
        return switch (op) {
            case "+" -> a + b;
            case "-" -> a - b;
            case "*" -> a * b;
            case "/" -> a / b;
            case "xʸ" -> Math.pow(a, b);
            default -> 0;
        };
    }

    private double applyScientificFunction(double value, String function) {
        return switch (function) {
            case "sin" -> Math.sin(Math.toRadians(value));
            case "cos" -> Math.cos(Math.toRadians(value));
            case "tan" -> Math.tan(Math.toRadians(value));
            case "log" -> Math.log10(value);
            case "ln" -> Math.log(value);
            case "√" -> Math.sqrt(value);
            case "n!" -> factorial(value);
            default -> 0;
        };
    }

    private double factorial(double n) {
        if (n < 0) return Double.NaN;
        if (n == 0) return 1;
        double fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }

    public static void main(String[] args) {
        new ScientificCalculator();
    }
}
