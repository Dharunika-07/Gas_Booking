
import javax.swing.*;
import javax.swing.Timer;
import java.util.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
// Define a generic abstract class for User
abstract class User<T> {
 protected static final String FILE_PATH = 
"C:\\Users\\Hannah\\GasBill\\users_data.txt";
 protected String name;
 protected String id;
 protected int cylindersBought;
 protected int month;
 public User(String name, String id, int cylindersBought, int month) {
 this.name = name;
 this.id = id;
 this.cylindersBought = cylindersBought;
 this.month = month;
 }
 // Abstract method to get the user type
 public abstract String getUserType();
 // Method to book the gas cylinder
 public abstract void bookGasCylinder();
 // Method to get the current month (dummy implementation, replace with actual 
implementation)
 protected int getCurrentMonth() {
 return month; // Replace with actual implementation when available
 }
 // Save the user data to the file
 protected void saveUserData(String newData) {
 try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
 bw.write(newData);
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
}
// Define a generic concrete class for Domestic User
class DomesticUser extends User<DomesticUser> implements 
BookingService<DomesticUser>,payment<DomesticUser> {
String comp;
String gas;
 public DomesticUser(String name, String id, int cylindersBought, int month) {
 super(name, id, cylindersBought, month);
 }
 @Override
 public String getUserType() {
 return "Domestic";
 }
 @Override
 public void bookGasCylinder() {
 // Ask the user for the current month
 int currentMonth = askForCurrentMonth();
 comp = askForcompname();
 gas = askForgasname();
 StringBuilder newData = new StringBuilder();
 try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
 String line;
 while ((line = br.readLine()) != null) {
 String[] userData = line.split(",");
 String idFromFile = userData[1]; // Assuming id is the second element in 
the line
 String extractedId = extractFirstThreeCharacters(idFromFile);
 // Corrected condition to check if the extracted ID is "ABC"
 if (extractedId.equals("ABC") && id.equals(idFromFile)) {
 // Check if the month has changed
 if (currentMonth != getCurrentMonth()) {
 cylindersBought = 0; // Reset the cylinders bought for the new month
 month = currentMonth; // Update the current month
 }
 if (cylindersBought < 3) {
 cylindersBought++;
 JOptionPane.showMessageDialog(null, "Gas cylinder booked 
successfully.");
 } else {
 JOptionPane.showMessageDialog(null, "You can book only 3 cylinders 
per month.");
 }
 
newData.append(name).append(",").append(id).append(",").append(cylindersBought
)
 .append(",").append(month).append("\n");
 // Break out of the loop after finding the matching user
 break;
 } else if(extractedId.equals("XYZ") && id.equals(idFromFile)) {
 if (currentMonth != getCurrentMonth()) {
 cylindersBought = 0; // Reset the cylinders bought for the new month
 month = currentMonth; // Update the current month
 }
 if (cylindersBought < 3) {
 cylindersBought++;
 JOptionPane.showMessageDialog(null, "Gas cylinder booked 
successfully.");
 } else {
 JOptionPane.showMessageDialog(null, "You can book only 3 cylinders 
per month.");
 }
 
newData.append(name).append(",").append(id).append(",").append(cylindersBought
)
 .append(",").append(month).append("\n");
 // Break out of the loop after finding the matching user
 break;
 }else {
 newData.append(line).append("\n");
 }
 }
 // Append the rest of the file content after the matching user
 while ((line = br.readLine()) != null) {
 newData.append(line).append("\n");
 }
 saveUserData(newData.toString());
 } catch (IOException e) {
 e.printStackTrace();
 }
 showPaymentMethodSelectionGUI();
 }
 
 private void showPaymentMethodSelectionGUI() {
 JFrame paymentFrame = new JFrame();
 paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 paymentFrame.setTitle("Payment Method Selection");
 paymentFrame.setSize(300, 150);
 JPanel panel = new JPanel(new GridLayout(1, 2));
 JRadioButton cashButton = createRadioButton("Cash");
 JRadioButton onlineButton = createRadioButton("Online");
 ButtonGroup buttonGroup = new ButtonGroup();
 buttonGroup.add(cashButton);
 buttonGroup.add(onlineButton);
 panel.add(cashButton);
 panel.add(onlineButton);
 JButton submitButton = new JButton("Submit");
 submitButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 displaySelectedPaymentMethod(cashButton, onlineButton);
 paymentFrame.dispose(); // Close the payment frame after selection
 }
 });
 JPanel buttonPanel = new JPanel();
 buttonPanel.add(submitButton);
 paymentFrame.setLayout(new BorderLayout());
 paymentFrame.add(panel, BorderLayout.CENTER);
 paymentFrame.add(buttonPanel, BorderLayout.SOUTH);
 paymentFrame.setLocationRelativeTo(null);
 paymentFrame.setVisible(true);
 }
 private JRadioButton createRadioButton(String text) {
 JRadioButton radioButton = new JRadioButton(text);
 radioButton.setFont(new Font("Arial", Font.PLAIN, 14));
 radioButton.setHorizontalAlignment(SwingConstants.CENTER);
 return radioButton;
 }
 private void displaySelectedPaymentMethod(JRadioButton cashButton, 
JRadioButton onlineButton) {
 if (cashButton.isSelected()) {
 JOptionPane.showMessageDialog(null, "Selected Payment Method: 
Cash");
 generateOTP();
 } else if (onlineButton.isSelected()) {
 JOptionPane.showMessageDialog(null, "Selected Payment Method: 
Online");
 Pay();
 } else {
 JOptionPane.showMessageDialog(null, "Please select a payment 
method.");
 }
 }
 
 private void selectedpaymentplatform() {
 String FILE_PATH1 = "C:\\Users\\Hannah\\GasBill\\company.txt";
 try (BufferedReader b = new BufferedReader(new FileReader(FILE_PATH1))) 
{
 String line;
 while ((line = b.readLine()) != null) {
 String[] userData = line.split(",");
 if(comp.equalsIgnoreCase(userData[1]));
 {
 PaymentSuccess(userData,gas);
 }
 
 }
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 
 public void PaymentSuccess(String[] userData,String gas)
 {
 for(int i=2;i<userData.length;i++)
 {
 if(userData[i].equalsIgnoreCase(gas))
 {
 String amount = userData[i + 1];
 displayPaymentPanel(amount);
 break;
 
 }
 i++;
 }
 }
 
 public void OTPamount() {
 
 String FILE_PATH1 = "C:\\Users\\Hannah\\GasBill\\company.txt";
 try (BufferedReader b = new BufferedReader(new FileReader(FILE_PATH1))) 
{
 String line;
 while ((line = b.readLine()) != null) {
 String[] userData = line.split(",");
 if(comp.equalsIgnoreCase(userData[1]));
 {
 for(int i=2;i<userData.length;i++)
 {
 if(userData[i].equalsIgnoreCase(gas))
 {
 billgeneration(userData[i+1]);
 break;
 }
 i++;
 }
 }
 
 }
 } catch (IOException e) {
 e.printStackTrace();
 }
 }
 
 
 
 
 public void displayPaymentPanel(String amount) {
 JFrame paymentFrame = new JFrame();
 paymentFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
 paymentFrame.setTitle("Payment");
 JPanel panel = new JPanel(null);
 JLabel amountLabel = new JLabel("Amount: $" + amount);
 amountLabel.setBounds(10, 20, 165, 25);
 panel.add(amountLabel);
 
 JLabel pinLabel = new JLabel("Enter PIN:");
 pinLabel.setBounds(10, 50, 80, 25);
 panel.add(pinLabel);
 JPasswordField pinField = new JPasswordField();
 pinField.setBounds(100, 50, 165, 25);
 panel.add(pinField);
 JButton payButton = new JButton("Pay");
 payButton.setBounds(10, 80, 150, 25);
 payButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 String enteredPin = new String(pinField.getPassword());
 if (isValidPin(enteredPin)) {
 displayPaymentSuccessPanel(paymentFrame,amount);
 } else {
 JOptionPane.showMessageDialog(paymentFrame, "Invalid PIN. 
Please try again.");
 }
 }
 });
 panel.add(payButton);
 paymentFrame.getContentPane().setLayout(new BorderLayout());
 paymentFrame.getContentPane().add(panel, BorderLayout.CENTER);
 paymentFrame.setSize(300, 150);
 paymentFrame.setLocationRelativeTo(null);
 paymentFrame.setVisible(true);
 }
 private void displayPaymentSuccessPanel(JFrame parentFrame,String amount) 
{
 JFrame successFrame = new JFrame();
 successFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 successFrame.setTitle("Payment Success");
 JPanel panel = new JPanel();
 JLabel successLabel = new JLabel("Payment Successful!");
 successLabel.setFont(new Font("Arial", Font.BOLD, 16));
 successLabel.setForeground(Color.GREEN);
 panel.add(successLabel);
 successFrame.getContentPane().setLayout(new BorderLayout());
 successFrame.getContentPane().add(panel, BorderLayout.CENTER);
 successFrame.setSize(250, 100);
 successFrame.setLocationRelativeTo(parentFrame);
 successFrame.setVisible(true);
 
 int delay = 2000;
 Timer timer = new Timer(delay, new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 successFrame.dispose();
 billgeneration(amount);
 }
 });
 // Start the timer
 timer.setRepeats(false); // Set to false to run only once
 timer.start();
 
 
 }
 
 private boolean isValidPin(String pin) {
 
 return pin.length() == 6 && pin.matches("\\d+");
 }
 
 
 void billgeneration(String amount)
 {
 JFrame billFrame = new JFrame();
 billFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 billFrame.setTitle("Bill Generation");
 // Create and configure main panel
 JPanel mainPanel = new JPanel() {
 @Override
 protected void paintComponent(Graphics g) {
 super.paintComponent(g);
 Graphics2D g2d = (Graphics2D) g.create();
 int w = getWidth();
 int h = getHeight();
 GradientPaint gp = new GradientPaint(0, 0, new Color(52, 152, 219), 0, 
h, new Color(41, 128, 185));
 g2d.setPaint(gp);
 g2d.fill(new RoundRectangle2D.Double(0, 0, w, h, 20, 20));
 g2d.dispose();
 }
 };
 mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
 // Company Name Label
 BookingAppGUI obj = new BookingAppGUI();
 obj.setVisible(true); // Make sure the GUI is visible before trying to get data
 // Later in your code...
 String userId = obj.userId; 
 
 
 JLabel companyLabel = new JLabel("<html><div style='text-align: center; 
font-size: 25px;padding-left: 60px; font-weight: bold; padding-top: 10px;paddingbottom: 10px; " +
 "color: #ffffff;'>"
 + comp +" Company "+"</div></html>");
 companyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 mainPanel.add(companyLabel);
 try {
 Class.forName("com.mysql.cj.jdbc.Driver");
 Connection con = 
DriverManager.getConnection("jdbc:mysql://localhost:3306/users", "root", 
"Book123$");
 String sql = "SELECT Id, Name, Address FROM registration WHERE 
Id=?";
 PreparedStatement preparedStatement = con.prepareStatement(sql);
 preparedStatement.setString(1, userId);
 ResultSet resultSet = preparedStatement.executeQuery();
 if (resultSet.next()) {
 // Retrieve the Id, Name, and Address if there is a matching record
 String idFromDatabase = resultSet.getString("Id");
 String name = resultSet.getString("Name");
 String address = resultSet.getString("Address");
 // Check if provided idtext matches the Id in the database
 if ((userId).equals(idFromDatabase)) {
 // Display the Name and Address
 JLabel nameLabel = new JLabel("<html><div style='text-align: 
center;font-size: 15px; font-weight: bold; padding: 10px; " +
 "color: #ffffff;'>Name: " + name + "</div></html>");
 nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 mainPanel.add(nameLabel);
 JLabel addressLabel = new JLabel("<html><div style='text-align: 
center;font-size: 15px; font-weight: bold; padding: 10px; " +
 "color: #ffffff;'>Address: " + address + "</div></html>");
 addressLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 mainPanel.add(addressLabel);
 } else {
 JOptionPane.showMessageDialog(null, "Idtext and ID do not 
match.");
 }
 } else {
 JOptionPane.showMessageDialog(null, "No matching record found for 
Id: " + userId);
 }
 } catch (Exception e) {
 e.printStackTrace();
 }
 
 JLabel gasLabel = new JLabel("<html><div style='text-align: center;font-size: 
15px; font-weight: bold; padding: 10px; " +
 "color: #ffffff;'>Gas Name: " + gas + "</div></html>");
 gasLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 mainPanel.add(gasLabel);
 
 JLabel amountLabel = new JLabel("<html><div style='text-align: center;fontsize: 15px; font-weight: bold; padding: 10px; " +
 "color: #ffffff;'>Amount Paid: $" + amount + "</div></html>");
 amountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 mainPanel.add(amountLabel);
 
 
 JLabel onlinePaymentLabel = new JLabel("<html><div style='text-align: 
center; font-size: 10px; padding-left: 50px; padding-top:10px;padding-bottom:10px;" 
+
 "color: #ffffff;'>"
 + "&#9733;&#9733;&#9733;&#9733;&#9733 Payment 
&#9733;&#9733;&#9733;&#9733;&#9733;" + "</div></html>");
 onlinePaymentLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
 mainPanel.add(onlinePaymentLabel);
 
 mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 billFrame.getContentPane().setLayout(new BorderLayout());
 billFrame.getContentPane().add(mainPanel, BorderLayout.CENTER);
 billFrame.setSize(400, 250);
 billFrame.setLocationRelativeTo(null);
 billFrame.setVisible(true);
 
 Feedback();
 
 
 }
 
 void Feedback()
 {
 JFrame frame = new JFrame("Feedback Form");
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.setSize(300, 200);
 JPanel panel = new JPanel();
 panel.setLayout(new GridLayout(3, 2));
 JLabel idLabel = new JLabel("Enter your ID:");
 idLabel.setBounds(10,20,80,25);
 JTextField idField = new JTextField();
 idField.setBounds(100,20,165,25);
 JLabel feedbackLabel = new JLabel("Enter your feedback:");
 feedbackLabel.setBounds(10,60,80,25);
 JTextArea feedbackArea = new JTextArea();
 feedbackArea.setBounds(100,60,165,80);
 JButton submitButton = new JButton("Submit");
 submitButton.setBounds(10,120,150,25);
 submitButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 try {
 displayFeedback(frame);
 } catch (NumberFormatException ex) {
 JOptionPane.showMessageDialog(frame, "Please enter a valid ID.");
 }
 }
 });
 panel.add(idLabel);
 panel.add(idField);
 panel.add(feedbackLabel);
 panel.add(new JScrollPane(feedbackArea));
 panel.add(new JLabel()); // Empty label for spacing
 panel.add(submitButton);
 frame.add(panel);
 frame.setLocationRelativeTo(null);
 frame.setVisible(true);
 }
 
 public static void displayFeedback(JFrame frame) {
 JOptionPane.showMessageDialog(frame, "Feedback submitted 
successfully!");
 }
 
 
 
 public void generateOTP()
 {
 int otp = 1000 + new Random().nextInt(9000);
 JOptionPane.showMessageDialog(null, "Generated OTP: " + otp +"\n\n");
 JOptionPane.showMessageDialog(null, "The selected gas will be delivered on 
time."
 + "\n\nNote: Additional charges will be for delivery");
 
 int delay = 2000;
 Timer timer = new Timer(delay, new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 OTPamount();
 }
 });
 // Start the timer
 timer.setRepeats(false); // Set to false to run only once
 timer.start();
 }
 
 
 public void Pay()
 {
 JFrame paymentFrame = new JFrame();
 paymentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 paymentFrame.setTitle("Online Payment");
 paymentFrame.setSize(300, 150);
 JPanel panel = new JPanel(new GridLayout(1, 2));
 JRadioButton gpayButton = createRadioButton("GPay");
 JRadioButton paytmButton = createRadioButton("Paytm");
 ButtonGroup buttonGroup = new ButtonGroup();
 buttonGroup.add(gpayButton);
 buttonGroup.add(paytmButton);
 panel.add(gpayButton);
 panel.add(paytmButton);
 JButton submitButton = new JButton("Submit");
 submitButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 selectedpaymentplatform();
 paymentFrame.dispose(); // Close the payment frame after selection
 }
 });
 JPanel buttonPanel = new JPanel();
 buttonPanel.add(submitButton);
 paymentFrame.setLayout(new BorderLayout());
 paymentFrame.add(panel, BorderLayout.CENTER);
 paymentFrame.add(buttonPanel, BorderLayout.SOUTH);
 paymentFrame.setLocationRelativeTo(null);
 paymentFrame.setVisible(true);
 }
 // Method to extract the first three characters from the ID
 private String extractFirstThreeCharacters(String fullId) {
 return fullId.substring(0, 3);
 }
 // Method to ask the user for the current month
 private int askForCurrentMonth() {
 String input = JOptionPane.showInputDialog("Enter the current month (1-12):");
 JLabel monthLabel = new JLabel("Enter Month:");
 return Integer.parseInt(input);
 }
 private String askForcompname() {
 String input = JOptionPane.showInputDialog("Enter the company name:");
 JLabel compLabel = new JLabel("choose company:");
 return (input);
 }
 private String askForgasname() {
 String input = JOptionPane.showInputDialog("Enter the gas name:");
 JLabel gasLabel = new JLabel("Choose gas:");
 return (input);
 }
}
// Define a generic interface for BookingService
interface payment<T extends User<?>>{
void generateOTP();
void Pay();
}
interface BookingService<T extends User<?>> {
 void bookGasCylinder();
}
public class BookingAppGUI extends JFrame{
 private JTextField userIdField;
 private JTextField monthField;
 public String userId;
 public BookingAppGUI() {
 initializeUI();
 }
 private void initializeUI() {
 setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 setSize(400, 200);
 setTitle("Gas Booking Application");
 JPanel panel = new JPanel();
 getContentPane().add(panel);
 placeComponents(panel);
 setLocationRelativeTo(null);
 }
 private void placeComponents(JPanel panel) {
 panel.setLayout(null);
 JLabel userLabel = new JLabel("Enter User ID:");
 userLabel.setBounds(10, 20, 80, 25);
 panel.add(userLabel);
 userIdField = new JTextField(20);
 userIdField.setBounds(100, 20, 165, 25);
 panel.add(userIdField);
 
 JLabel monthLabel = new JLabel("Enter Month:");
 monthLabel.setBounds(10, 50, 80, 25);
 panel.add(monthLabel);
 monthField = new JTextField(20);
 monthField.setBounds(100, 50, 165, 25);
 panel.add(monthField);
 
 
 JButton bookButton = new JButton("Book Gas Cylinder");
 bookButton.setBounds(10, 80, 150, 25);
 bookButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 bookGasCylinder();
 }
 });
 panel.add(bookButton);
 
 }
 
 private void bookGasCylinder() {
 userId = userIdField.getText();
 String monthStr = monthField.getText();
 if (userId.isEmpty() || monthStr.isEmpty()) {
 JOptionPane.showMessageDialog(this, "Please enter both User ID and 
Month.");
 return;
 }
 try {
 int month = Integer.parseInt(monthStr);
 // Check if the id and month are valid
 User<?> user = getUsersData(userId);
 if (user != null) {
 BookingService<?> bookingService = (BookingService<?>) user;
 bookingService.bookGasCylinder();
 } else {
 JOptionPane.showMessageDialog(this, "Invalid user ID.");
 }
 } catch (NumberFormatException e) {
 JOptionPane.showMessageDialog(this, "Please enter a valid month 
(numeric).");
 }
 }
 // Method to get user data from the file
 private static User<?> getUsersData(String userId) {
 try (BufferedReader br = new BufferedReader(new 
FileReader(User.FILE_PATH))) {
 String line;
 while ((line = br.readLine()) != null) {
 String[] userData = line.split(",");
 String idFromFile = userData[1]; // Assuming id is the second element in 
the line
 String extractedId = extractFirstThreeCharacters(idFromFile);
 // Corrected condition to check if the extracted ID matches "ABC"
 if (extractedId.equals("ABC") && userId.equals(idFromFile)) {
 String name = userData[0];
 int cylindersBought = Integer.parseInt(userData[2]); // Assuming 
cylinders bought is the third element
 int month = Integer.parseInt(userData[3]); // Assuming month is the 
fourth element
 return new DomesticUser(name, userId, cylindersBought, month);
 }
 else if(extractedId.equals("XYZ") && userId.equals(idFromFile)){
 String name = userData[0];
 int cylindersBought = Integer.parseInt(userData[2]); // Assuming 
cylinders bought is the third element
 int month = Integer.parseInt(userData[3]); // Assuming month is the 
fourth element
 return new DomesticUser(name, userId, cylindersBought, month);
 }
 }
 } catch (IOException | NumberFormatException e) {
 e.printStackTrace();
 }
 return null; // Id not found
 }
 // Method to extract the first three characters from the ID
 private static String extractFirstThreeCharacters(String fullId) {
 return fullId.substring(0, 3);
 }
 public static void main(String[] args) {
 SwingUtilities.invokeLater(() -> {
 
 BookingAppGUI bookingAppSwing = new BookingAppGUI();
 bookingAppSwing.setVisible(true);
 
 });
 
 
 }
}
