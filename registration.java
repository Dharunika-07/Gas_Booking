import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.sql.ConnectionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
public class Gui implements ActionListener{
private static JLabel userlabel;
private static JLabel username;
private static JTextField userText;
private static JTextField usertext;
private static JTextField addresstext;
private static JTextField gentext;
private static JLabel passwordLabel;
private static JPasswordField passText;
private static JButton button;
private static JLabel success;
private static JLabel addressLabel;
private static JLabel genLabel;
private static JLabel ageLabel;
private static JTextField agetext;
private static JLabel mobLabel;
private static JTextField mobtext;
private static JLabel emailLabel;
private static JTextField emailtext;
private static JLabel idLabel;
private static JTextField idtext;
private static JButton resetbtn;
public Gui(){
//Frame is window && panel is layout
JPanel panel = new JPanel();
JFrame frame = new JFrame();
frame.setSize(350,200);
frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
frame.add(panel);
panel.setLayout(null);
username = new JLabel("Name");
username.setBounds(10,20,80,25);
panel.add(username);
usertext = new JTextField(20);
usertext.setBounds(100,20,165,25);
panel.add(usertext);
addressLabel = new JLabel("Address");
addressLabel.setBounds(10,50,80,25);
panel.add(addressLabel);
addresstext = new JTextField(20);
addresstext.setBounds(100,50,165,25);
panel.add(addresstext);
genLabel = new JLabel("Gender");
genLabel.setBounds(10,80,80,25);
panel.add(genLabel);
gentext = new JTextField(20);
gentext.setBounds(100,80,165,25);
panel.add(gentext);
ageLabel = new JLabel("Age");
ageLabel.setBounds(10, 110, 80, 25);
panel.add(ageLabel);
agetext = new JTextField(20);
agetext.setBounds(100,110,165,25);
panel.add(agetext);
mobLabel = new JLabel("Mobile");
mobLabel.setBounds(10,140,80,25);
panel.add(mobLabel);
mobtext = new JTextField(20);
mobtext.setBounds(100,140,165,25);
panel.add(mobtext);
emailLabel = new JLabel("Email");
emailLabel.setBounds(10,170,80,25);
panel.add(emailLabel);
emailtext = new JTextField(20);
emailtext.setBounds(100,170,165,25);
panel.add(emailtext);
idLabel = new JLabel("Id");
idLabel.setBounds(10,200,80,25);
panel.add(idLabel);
idtext = new JTextField(20);
idtext.setBounds(100,200,165,25);
panel.add(idtext);
userlabel = new JLabel("User");
userlabel.setBounds(10, 230, 80, 25);
panel.add(userlabel);
userText = new JTextField(20);
userText.setBounds(100,230,165,25);
panel.add(userText);
passwordLabel = new JLabel("Password");
passwordLabel.setBounds(10,260,80,25);
panel.add(passwordLabel);
passText = new JPasswordField(20);
passText.setBounds(100,260,165,25);
panel.add(passText);
button = new JButton("Register");
button.setBounds(10,290,100,25);
button.addActionListener(this);
panel.add(button);
resetbtn = new JButton("Reset");
resetbtn.setBounds(150,290,80,25);
resetbtn.addActionListener(this);
panel.add(resetbtn);
success = new JLabel("");
success.setBounds(10,110,300,25);
panel.add(success);
frame.setVisible(true);
}
public void actionPerformed(ActionEvent e) {
 if (e.getSource() == button) {
 try {
 Class.forName("com.mysql.cj.jdbc.Driver");
 Connection con = 
DriverManager.getConnection("jdbc:mysql://localhost:3306/users","root","Book123$")
;
 String query = "INSERT INTO registration values(?,?,?,?,?,?,?,?,?)";
 PreparedStatement ps = con.prepareStatement(query);
 ps.setString(1, usertext.getText());
 ps.setString(2, addresstext.getText());
 ps.setString(3, gentext.getText());
 ps.setString(4, agetext.getText());
 ps.setString(5, mobtext.getText());
 ps.setString(6, emailtext.getText());
 ps.setString(7, idtext.getText());
 ps.setString(8, userText.getText());
 ps.setString(9, passText.getText());
 
 int i = ps.executeUpdate();
 JOptionPane.showMessageDialog(button,i+" Record updated! ");
 ps.close();
 con.close();
 
 }catch(Exception e1){
 e1.printStackTrace();
 }
 
 } else if (e.getSource() == resetbtn) {
 usertext.setText("");
 addresstext.setText("");
 gentext.setText("");
 agetext.setText("");
 mobtext.setText("");
 emailtext.setText("");
 idtext.setText("");
 userText.setText("");
 passText.setText("");
 }
 }
public static void main(String[] args)
{
new Gui();
SwingUtilities.invokeLater(() -> {
 JFrame frame = new JFrame("Swing Transition Example");
 frame.setSize(300, 200);
 frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 JButton nextButton = new JButton("Sign Up");
 frame.add(nextButton);
 // Add ActionListener to the button
 nextButton.addActionListener(new ActionListener() {
 @Override
 public void actionPerformed(ActionEvent e) {
 // Open the next class
 new SignUp();
 // Close the current frame if needed
 frame.dispose();
 }
 });
 frame.setVisible(true);
 });
}
}