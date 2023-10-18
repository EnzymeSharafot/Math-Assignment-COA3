import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class GeneralEquationGUI extends JFrame {
    private JTextField[] coefficientTextFields;
    private JTextField angleTextField;
    private JTextArea resultTextArea;

    private String[] coefficientNames = {"a", "b", "c", "2h", "2g", "2f"};

    public GeneralEquationGUI() {
        setTitle("General Equation Rotation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.LIGHT_GRAY)));
        JLabel titleLabel = new JLabel("<html><h1 style='color: #0000FF;'>Original Equation To Rotated Equation</h1></html>");
        JLabel titleLabe2 = new JLabel("<html><h2 style='color: #0000FF;'>General Equation : ax^2+2hxy+by^2+2gx+2fy+c=0 </h2></html>");
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputPanel.add(titleLabel);
        inputPanel.add(titleLabe2);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        coefficientTextFields = new JTextField[coefficientNames.length];

        for (int i = 0; i < coefficientNames.length; i++) {
            JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JLabel label = new JLabel(coefficientNames[i] + " : ");
            coefficientTextFields[i] = new JTextField(10);
            coefficientTextFields[i].setToolTipText("Enter a number");

            rowPanel.add(label);
            rowPanel.add(coefficientTextFields[i]);

            inputPanel.add(rowPanel);
            inputPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        JPanel anglePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel angleLabel = new JLabel("Enter The Angle of Rotation:");
        angleTextField = new JTextField(5);
        angleTextField.setToolTipText("Enter an angle");

        anglePanel.add(angleLabel);
        anglePanel.add(angleTextField);

        inputPanel.add(anglePanel);
        inputPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JButton rotateButton = new JButton("Rotate Equation");
        rotateButton.setBackground(new Color(0, 123, 255));
        rotateButton.setForeground(Color.WHITE);
        rotateButton.addActionListener(e -> rotateEquation());

        inputPanel.add(rotateButton);

        add(inputPanel, BorderLayout.WEST);

        resultTextArea = new JTextArea();
        resultTextArea.setEditable(false);
        resultTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        resultTextArea.setBorder(new CompoundBorder(new EmptyBorder(10, 10, 10, 10), new LineBorder(Color.LIGHT_GRAY)));

        JScrollPane scrollPane = new JScrollPane(resultTextArea);
        add(scrollPane, BorderLayout.CENTER);

        pack(); // Automatically set the frame size based on components
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void rotateEquation() {
        try {
            // Input validation
            double[] coefficients = new double[coefficientNames.length];
            for (int i = 0; i < coefficientNames.length; i++) {
                coefficients[i] = Double.parseDouble(coefficientTextFields[i].getText());
            }
            double T = Double.parseDouble(angleTextField.getText());

            // Perform the rotation calculation
            double sinT = Math.sin(Math.toRadians(T));
            double cosT = Math.cos(Math.toRadians(T));
            double cos2T = Math.cos(Math.toRadians(2 * T));
            double sin2T = Math.sin(Math.toRadians(2 * T));
            double a = coefficients[0];
            double b = coefficients[1];
            double c = coefficients[2];
            double h = coefficients[3] / 2;
            double f = coefficients[5] / 2;
            double g = coefficients[4] / 2;

            double first = (a * cosT * cosT) + (b * sinT * sinT) + (h * sin2T);
            double second = (a * sinT * sinT) + (b * cosT * cosT) - (h * sin2T);
            double third = (b * sin2T) - (a * sin2T) + (2 * h * cos2T);
            double fourth = (2 * f * sinT) + (2 * g * cosT);
            double five = (- 2 * g * sinT) + (2 * f * cosT);

            String result = String.format("The Equation After Rotation Is:\n(%.2f)x^2 + %.2fxy + (%.2f)y^2 + (%.2f)x + (%.2f)y + (%.2f) = 0", first, third, second, fourth, five, c);

            resultTextArea.setText(result);
        } catch (NumberFormatException e) {
            resultTextArea.setText("Invalid input. Please enter valid numbers.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                // Use the Nimbus look and feel for a modern appearance
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            new GeneralEquationGUI();
        });
    }
}