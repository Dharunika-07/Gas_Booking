package main;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.*;
class GasProvider 
{
 private String company;
 private String ID;
 private String address;
 private String interconnector;
 private String shipper;
 private Map<String, String> gasdetails;
 public GasProvider() 
 {
 gasdetails = new HashMap<>();
 }
 public void setCompany(String company)
 {
 this.company = company;
 }
 public String getCompany() 
 {
 return company;
 }
 public void setID(String ID) 
 {
 this.ID = ID;
 }
 public String getID() 
 {
 return ID;
 }
 public void setAddress(String address) 
 {
 this.address = address;
 }
 public String getAddress()
 {
 return address;
 }
 public void setInterconnector(String interconnector)
 {
 this.interconnector = interconnector;
 }
 public String getInterconnector()
 {
    return interconnector;
}
public void setShipper(String shipper) 
{
this.shipper = shipper;
}
public String getShipper() 
{
return shipper;
}
public void addGas(String gas, String amount)
{
gasdetails.put(gas, amount);
}
public Map<String, String> getGasDetails() 
{
return gasdetails;
}
public void saveToFile()
{
try 
{
String fileName = "C:\\\\Users\\\\Hannah\\\\GasBill\\\\company.txt";
File f = new File(fileName);
FileWriter fw = new FileWriter(f, true);
String id = getID();
fw.write(id + "," + getCompany() + ",");
for (Map.Entry<String, String> entry : gasdetails.entrySet())
{
String gas = entry.getKey();
String original_amount = entry.getValue();
String adjusted_amount = subsidy(id, original_amount);
fw.write(gas + "," + adjusted_amount);
if (!entry.equals(get_last_entry()))
{
fw.write(",");
}
}
fw.write("\n");
System.out.println("data Written");
fw.close();
//System.out.println("Data written to " + fileName);
} 
catch (IOException e) 
{
System.out.println("An error occurred: " + e.getMessage());
}
}
private String subsidy(String id, String original_amount) 
{
if (id.startsWith("ABC")) 
{
int amount = Integer.parseInt(original_amount) - 300;
return String.valueOf(Math.max(amount, 0)); 
} 
else if (id.startsWith("XYZ")) 
{
return original_amount;
} 
else 
{
return "0"; 
}
}
private Map.Entry<String, String> get_last_entry() 
{
Map.Entry<String, String> last_entry = null;
for (Map.Entry<String, String> entry : gasdetails.entrySet())
{
last_entry = entry;
}
return last_entry;
}
}
public class Gasproviders {
public static void main(String[] args) {
Scanner sc = new Scanner(System.in);
GasProvider provider = new GasProvider();
System.out.println("Welcome Gas providers");
System.out.println("Your company name:");
String companyName = sc.nextLine();
provider.setCompany(companyName);
System.out.println("Your ID:");
provider.setID(sc.next());
sc.nextLine();
System.out.println("Your address:");
provider.setAddress(sc.nextLine());
System.out.println("Tell us your Gas interconnector:");
provider.setInterconnector(sc.nextLine());
System.out.println("Tell us your Gas Shipper:");
provider.setShipper(sc.nextLine());
System.out.println("Enter the gases you supply along with their amounts (Eg:gas1 amount1, gas2 amount2):");
String gasesInput = sc.nextLine();
String[] gas_amount_pairs = gasesInput.split(",");
for (String pair : gas_amount_pairs) 
{
String[] gasAndAmount = pair.trim().split(" ");
provider.addGas(gasAndAmount[0], gasAndAmount[1]);
}
provider.saveToFile();
}
}