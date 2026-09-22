package Entity;

import Frame.*;

import javax.swing.*;
import javax.swing.table.TableModel;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Logic.Customer;

public class Sales {
	


    public Sales() {}

    public void insertInfo(TableModel model, Customer customer) {
        File file = new File("./Data/Payment receipt.txt");
        try {
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("HH:mm a, dd/MM/yyyy");

            double subtotal = 0.0;
            for (int i = 0; i < model.getRowCount(); i++) {
                subtotal += toDouble(model.getValueAt(i, 4)); 
            }
            double discount = (customer != null) ? customer.applyDiscount(subtotal) : 0.0;
            double payable  = subtotal - discount;

            try (FileWriter fw = new FileWriter(file, true);
                 BufferedWriter bw = new BufferedWriter(fw)) {

                bw.write("Date and Time: " + now.format(fmt)); bw.newLine();
                bw.write("========================================================"); bw.newLine();

                String custName = (customer != null && customer.getName() != null) ? customer.getName() : "-";
                String contact  = (customer != null && customer.getContact() != null) ? customer.getContact() : "-";
                bw.write("Customer : " + custName); bw.newLine();
                bw.write("Contact  : " + contact);  bw.newLine();
                bw.write("--------------------------------------------------------"); bw.newLine();

                bw.write(String.format("%-3s %-20s %-12s %-8s %-12s","#", "Product", "Unit Price", "Qty", "Total"));
                bw.newLine();
                bw.write("--------------------------------------------------------"); bw.newLine();

                for (int i = 0; i < model.getRowCount(); i++) {
                    String product = String.valueOf(model.getValueAt(i, 0)); 
                    double unit     = toDouble(model.getValueAt(i, 2));    
                    int qty         = toInt(model.getValueAt(i, 3));       
                    double total    = toDouble(model.getValueAt(i, 4));  

                    bw.write(String.format("%-3d %-20s %-12.2f %-8d %-12.2f",
                            (i + 1), product, unit, qty, total));
                    bw.newLine();
                }

                bw.write("--------------------------------------------------------"); bw.newLine();
                bw.write(String.format("%-40s %-12.2f", "Grand Total:", subtotal)); bw.newLine();
                bw.write(String.format("%-40s %-12.2f", "Discount:", discount));  bw.newLine();
                bw.write(String.format("%-40s %-12.2f", "Payable:",  payable));   bw.newLine();
                bw.write("PAYMENT=CONFIRMED"); bw.newLine(); 
                bw.write("========================================================"); bw.newLine();
                bw.newLine();
            }

        } catch (IOException ioe) {
            ioe.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error writing to file!");
        }
    }

    
    private double toDouble(Object o) {
        try { return Double.parseDouble(String.valueOf(o)); }
        catch (Exception e) { return 0.0; }
    }
    private int toInt(Object o) {
        try { return Integer.parseInt(String.valueOf(o).trim()); }
        catch (Exception e) { return 0; }
    }
}
