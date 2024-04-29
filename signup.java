
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class SignUp implements ActionListener{
private static JLabel userlabel;
private static JLabel backgroundLabel;
private static JTextField userText;
private static JLabel passwordLabel;
private static JPasswordField passText;
private static JButton button;
private static JButton resetbtn;
private static JLabel IDlabel;
private static JTextField IDText;
public SignUp(){
JPanel panel = new JPanel();
JFrame frame = new JFrame();
frame.setSize(350,200);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.add(panel);
panel.setLayout(null);
userlabel = new JLabel("Username");
userlabel.setBounds(10, 20, 80, 25);
panel.add(userlabel);
userText = new JTextField(20);
userText.setBounds(100,20,165,25);
panel.add(userText);
passwordLabel = new JLabel("Password");
passwordLabel.setBounds(10,50,80,25);
panel.add(passwordLabel);
passText = new JPasswordField(20);
passText.setBounds(100,50,165,25);
panel.add(passText);
IDlabel = new JLabel("ID");
IDlabel.setBounds(10,80,80,25);
panel.add(IDlabel);
IDText = new JTextField(20);
IDText.setBounds(100,80,165,25);
panel.add(IDText);
button = new JButton("Login");
button.setBounds(10,110,100,25);
button.addActionListener(this);
panel.add(button);
resetbtn = new JButton("Reset");
resetbtn.setBounds(150,110,80,25);
resetbtn.addActionListener(this);
panel.add(resetbtn);
frame.setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e) {
if(e.getSource() == button)
{
try {
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con = 
DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","Book123$")
;
Statement stmt = con.createStatement();
String sql = "SELECT * FROM registration where 
User='"+userText.getText()+"' and Password='"+passText.getText().toString()+"'";
ResultSet rs = stmt.executeQuery(sql);
if(rs.next())
{
JOptionPane.showMessageDialog(null, "Login 
Sucessfull....");
}
else
{
JOptionPane.showMessageDialog(null, "Incorrect 
Username and Password....");
}
String id = IDText.getText();
String FILE_PATH = 
"C:\\Users\\Hannah\\GasBill\\company.txt";
String extractedId = extractFirstThreeCharacters(id);
JTextArea textArea = new JTextArea();
 textArea.setEditable(false);
 // Move panel and frame creation outside the inner try-catch block
 JPanel panel = new JPanel();
 JFrame frame = new JFrame();
 frame.setSize(350, 200);
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 frame.add(panel);
 panel.setLayout(new BorderLayout());
 panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
 if(extractedId.equals("ABC"))
 {
 textArea.append("WELCOME DOMESTIC GAS USERS...");
 }
 else {
 textArea.append("WELCOME COMMERCIAL GAS USERS...");
 }
try (BufferedReader br = new BufferedReader(new 
FileReader(FILE_PATH))) {
 String line;
 while ((line = br.readLine()) != null) {
 String[] userData = line.split(",");
 String idFromFile = 
extractFirstThreeCharacters(userData[0]); // Assuming id is the second element in 
the line
 // Corrected condition to check if the extracted ID matches 
"ABC"
 
 if (extractedId.equalsIgnoreCase(idFromFile)) {
 textArea.append("\n\n");
 textArea.append("COMPANY NAME : " + userData[1] + 
"\n");
 for(int i=2;i<userData.length;i++)
 {
 textArea.append("GAS NAME : " + userData[i] + " 
Amount : Rs" + userData[i + 1] + "\n");
 i++;
 
 }
 }
 
 
 }
 frame.setVisible(true);
 } catch (IOException | NumberFormatException e2) {
 e2.printStackTrace();
 }
con.close();
}catch(Exception e1){
e1.printStackTrace();
}
}
else if (e.getSource() == resetbtn) {
userText.setText("");
 passText.setText("");
 IDText.setText("");
}
}
public static String extractFirstThreeCharacters(String fullId) {
 return fullId.substring(0, 3);
 }
public static void main(String[] args) {
SwingUtilities.invokeLater(() -> new SignUp());
 
 JFrame frame = new JFrame("Swing Transition Example");
 frame.setSize(300, 200);
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 JButton nextButton = new JButton("Booking APP");
 frame.add(nextButton);
 // Add ActionListener to the button
 nextButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 // Open the next class
 BookingAppGUI bookingAppSwing = new BookingAppGUI();
 bookingAppSwing.setVisible(true);
 
 frame.dispose();
 }
 });
 frame.setVisible(true); 
 
}
}