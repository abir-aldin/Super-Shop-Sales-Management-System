package Frame;

import Logic.*;
import Entity.*;

import java.lang.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class FrameDesign extends JFrame{
    private JLabel label1,label2,label3,label4,label5,label6;
    private JTextField tf1 , tf2 , tf3 ;
    private Font BOLD_Font = new Font("Arial", Font.BOLD, 30);
    private Font Label_Font = new Font("Arial",Font.PLAIN,19);
    private Font Tf_Font = new Font("Arial", Font.PLAIN, 18);
    private JButton button1 = new JButton("+");
    private JButton button2 = new JButton("-");
    private JButton addButton,editButton,clearButton,deleteButton,confirmButton;
    private Container c = getContentPane();
    private DefaultTableModel model ;
    private JTable table;
    private JScrollPane scroll;
    private JComboBox<String> cb1,cb2;
    private Shop s ;
    private Sales sales;
    private Customer customer;

    public JTextField getTF1()
    {
        return tf1;
    }
    public JTextField getTF2()
    {
        return tf2;
    }

    public void work()
    {
        s = new Shop();
        customer = new Customer();
        
        label1 = new JLabel();
        label1.setText("Billing product");
        label1.setFont(BOLD_Font);
        label1.setBounds(380,20,250,35);
        add(label1);

        label2 = new JLabel("Customer name");
        label2.setFont(Label_Font);
        label2.setBounds(20,75,140,30);
        add(label2);

        tf1 = new JTextField();
        tf1.setBounds(165,75,200,30);
        tf1.setFont(Tf_Font);
        add(tf1);
        customer.setName(tf1.getText());

        label2 = new JLabel("Contact number");
        label2.setBounds(580,75,138,30);
        label2.setFont(Label_Font);
        add(label2);

        tf2 = new JTextField();
        tf2.setBounds(730,75,200,30);
        tf2.setFont(Tf_Font);
        add(tf2);
        customer.setContactInfo(tf2.getText());

        label3 = new JLabel("Category");
        label3.setBounds(20,120,100,30);
        label3.setFont(Label_Font);
        add(label3);
        
        String category[] = new String[s.getCategories().length];
        for(int i=0;i<s.getCategories().length;i++)
        {
            category[i] = s.getCategories()[i].getType();
        }
        cb1 = new JComboBox<>(category);
        cb1.setBounds(165,120,200,30);
        cb1.setFont(Tf_Font);
        add(cb1);

        label4 = new JLabel("Products");
        label4.setBounds(580,120,100,30);
        label4.setFont(Label_Font);
        add(label4);
        
        cb2 = new JComboBox<>(new String[]{"Select category first..."});
        cb2.setFont(Tf_Font);
        add(cb2);
        cb2.setBounds(730,120,200,30);

        label5 = new JLabel("Quantity");
        label5.setBounds(20,165,100,30);
        label5.setFont(Label_Font);
        add(label5);

        tf3 = new JTextField("1");
        tf3.setBounds(165,165,200,30);
        tf3.setFont(Tf_Font);
        add(tf3);

        button1.setBounds(200,200,45,20);
        Font fb1 = new Font("Arial", Font.BOLD, 15) ;
        button1.setFont(fb1);
        button1.setBackground(Color.GREEN);
        add(button1);

        button2.setBounds(260,200,45,20);
        button2.setFont(fb1);
        button2.setBackground(Color.RED);
        add(button2);

        addButton = new JButton("Add");
        addButton.setBounds(180,250,70,30);
        addButton.setFont(fb1);
        addButton.setBackground(Color.GREEN);
        add(addButton);

        editButton = new JButton("Edit");
        editButton.setBounds(360,250,70,30);
        editButton.setFont(fb1);
        editButton.setBackground(Color.pink);
        add(editButton);

        clearButton = new JButton("Clear");
        clearButton.setBounds(540,250,80,30);
        clearButton.setFont(fb1);
        clearButton.setBackground(Color.ORANGE);
        add(clearButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(720,250,80,30);
        deleteButton.setFont(fb1);
        deleteButton.setBackground(Color.RED);
        add(deleteButton);

        label6 = new JLabel("Product List");
        label6.setFont(BOLD_Font);
        label6.setBounds(380,310,250,35);
        add(label6);

        String column[] = {"Product Name","Category","Unit Price","Quantity","Total"};
        String rows[] = new String[5];
        
        
        // rows[3]="Aotul4";
        // rows[4]="Aotul5";
        table = new JTable();
        model = new DefaultTableModel();
        model.setColumnIdentifiers(column);
        
        table.setModel(model);
        table.setFont(Tf_Font);
        table.setSelectionBackground(Color.GREEN);
        table.setBackground(Color.WHITE);
        table.setRowHeight(30);

        scroll = new JScrollPane(table);
        scroll.setBounds(130,360,740,265);
        add(scroll);

        confirmButton = new JButton("Confirm");
        confirmButton.setBounds(450,650,130,35);
        confirmButton.setFont(fb1);
        confirmButton.setBackground(Color.PINK);
        add(confirmButton);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                rows[0] = cb2.getItemAt(cb2.getSelectedIndex());
                rows[1]= cb1.getItemAt(cb1.getSelectedIndex());
                rows[2]= String.valueOf(s.getCategories()[cb1.getSelectedIndex()].getProduct()[cb2.getSelectedIndex()].getPrice());
                rows[3] = tf3.getText();
                double Total = Integer.parseInt(rows[3]) * s.getCategories()[cb1.getSelectedIndex()].getProduct()[cb2.getSelectedIndex()].getPrice();
                rows[4] = String.valueOf(Total);
                int n = Integer.parseInt(rows[3]);
                boolean check = s.getCategories()[cb1.getSelectedIndex()].getProduct()[cb2.getSelectedIndex()].checkStock(n);

                if(check)
                {
                    s.getCategories()[cb1.getSelectedIndex()].getProduct()[cb2.getSelectedIndex()].availableQuantity(n); // quantity minus hoye jabe
                    model.addRow(rows);
                }
                if(!check)
                {
                    JOptionPane.showMessageDialog(FrameDesign.this, "Stock out");
                }
            }
        });

        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
                int choice = JOptionPane.showConfirmDialog(FrameDesign.this,"If you clear, all data will be erased.\n\nAre you sure?\n\n","Confirm Clear",JOptionPane.YES_NO_OPTION);
                
                if(choice==JOptionPane.YES_OPTION)
                {
                    tf1.setText("");
                    tf2.setText("");
                    tf3.setText("1");
                    model.setRowCount(0);
                }

            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) 
            {
                int num_row = table.getSelectedRow();
                if (num_row >= 0) 
                {
        
                    String prodName=String.valueOf(model.getValueAt(num_row, 0));
                    String catName=String.valueOf(model.getValueAt(num_row, 1)); 
                    String qObj=model.getValueAt(num_row, 3).toString();              

                    int qty = Integer.parseInt(qObj);
                   
                    for (Category cat : s.getCategories()) 
                    {
                        if (catName.equals(cat.getType())) 
                        {
                            for (Product p : cat.getProduct()) 
                            {
                                if (prodName.equals(p.getName())) 
                                {
                                    p.addQuantity(qty); 
                                    break;
                                }
                            }
                            break;
                        }
                }

                    model.removeRow(num_row);
                } 
                else 
                {
                    JOptionPane.showMessageDialog(FrameDesign.this, "No Row has been selected");
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        int row = table.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(FrameDesign.this, "No Row has been selected");
            return;
        }

        
        String oldProdName = String.valueOf(model.getValueAt(row, 0));
        String oldCatName  = String.valueOf(model.getValueAt(row, 1));
        int oldQty         = Integer.parseInt(String.valueOf(model.getValueAt(row, 3)));

       
        int catIdx  = cb1.getSelectedIndex();
        int prodIdx = cb2.getSelectedIndex();
        if (catIdx <= 0 || prodIdx < 0 || "Select category first...".equals(cb2.getSelectedItem())) {
            JOptionPane.showMessageDialog(FrameDesign.this, "Please select category & product.");
            return;
        }
        int newQty = Integer.parseInt(tf3.getText());

        Category newCat = s.getCategories()[catIdx];
        Product  newProd = newCat.getProduct()[prodIdx];

       
        boolean sameProduct = newCat.getType().equals(oldCatName) && newProd.getName().equals(oldProdName);

        if (sameProduct) {
            int delta = newQty - oldQty; 
            if (delta > 0) {
                
                if (!newProd.checkStock(delta)) {
                    JOptionPane.showMessageDialog(FrameDesign.this, "Stock out");
                    return;
                }
                newProd.availableQuantity(delta); 
            } else if (delta < 0) {
                newProd.addQuantity(-delta);      
            }
        } else {
           
            if (!newProd.checkStock(newQty)) {
                JOptionPane.showMessageDialog(FrameDesign.this, "Stock out");
                return;
            }
            
            for (Category cat : s.getCategories()) {
                if (oldCatName.equals(cat.getType())) {
                    for (Product p : cat.getProduct()) {
                        if (oldProdName.equals(p.getName())) {
                            p.addQuantity(oldQty);
                            break;
                        }
                    }
                    break;
                }
            }
            newProd.availableQuantity(newQty);
        }

        String ProductName   = newProd.getName();
        String CategoryName  = newCat.getType();
        String UnitPrice     = String.valueOf(newProd.getPrice());
        String QuantityInfo  = String.valueOf(newQty);
        String TotalInfo     = String.valueOf(newProd.getPrice() * newQty);

        model.setValueAt(ProductName,  row, 0);
        model.setValueAt(CategoryName, row, 1);
        model.setValueAt(UnitPrice,    row, 2);
        model.setValueAt(QuantityInfo, row, 3);
        model.setValueAt(TotalInfo,    row, 4);
    }
});


        cb1.addActionListener(e -> 
        {
            int x = cb1.getSelectedIndex();  
            String[] items;

            if (x <= 0) 
            {
                items = new String[]{"Select category first..."};
            } 
            else 
            {
                int n = s.getCategories()[x].getProduct().length;
                items = new String[n];
                for (int i = 0; i < n; i++) 
                {
                    items[i] = s.getCategories()[x].getProduct()[i].getName();
                }
            }

            cb2.setModel(new DefaultComboBoxModel<>(items));
            cb2.setSelectedIndex(0);
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int QUANTITY = Integer.parseInt(tf3.getText());
                QUANTITY++;
                tf3.setText(String.valueOf(QUANTITY));
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                int QUANTITY = Integer.parseInt(tf3.getText());
                QUANTITY--;
                if(QUANTITY>0)
                {
                    tf3.setText(String.valueOf(QUANTITY));
                }
                
            }
        });

        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                customer.setName(tf1.getText());
                customer.setContactInfo(tf2.getText());
                int row_number = table.getRowCount();
                String String_totalValue[] = new String[row_number] ;
                double Double_totalValue[] = new double[row_number];
                double total_amount = 0 ;
                for(int i=0;i<row_number;i++)
                {
                    String_totalValue[i] = model.getValueAt(i, 4).toString() ;
                    Double_totalValue[i] = Double.parseDouble(String_totalValue[i]);
                }
                for(int i=0;i<Double_totalValue.length;i++)
                {
                    total_amount += Double_totalValue[i] ;
                }

                if(row_number==0)
                {
                    JOptionPane.showMessageDialog(FrameDesign.this,"Table is Empty\n");
                }
                else
                {
                    int choice = JOptionPane.showConfirmDialog(FrameDesign.this,"Total Bill: "+total_amount+"\nDiscount: "+customer.applyDiscount(total_amount),"Confirmation",JOptionPane.YES_NO_OPTION);

                    if(choice == JOptionPane.YES_OPTION)
                    {
                        JOptionPane.showMessageDialog(FrameDesign.this, "Payment recipt file saved");
                        sales = new Sales();
                        sales.insertInfo(model, customer);
                        check();
                    }
                }


            }
        });


        c.setBackground(Color.CYAN);
        setSize(1050,750);
        setTitle("Super Shop Sales Management System");
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void check() {

        StringBuilder sb = new StringBuilder();
        try
        {
            File file = new File("./Data/Payment receipt.txt");
            if (file.exists()) {
				FileReader fr = new FileReader(file); 
                BufferedReader br = new BufferedReader(fr); 
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line).append('\n');

                }
                br.close();
            }
        }
		catch(IOException ioe) 
		{
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(this,"Error!");
		}
    }


    // public static void main(String[] args) {
    //     FrameDesign fd = new FrameDesign();
    //     fd.work();
    // }
}









// 0101010101
// 1010101010
// 00101011110010010110
// 0110101
// 1011001001000101011001110110
// 1011001011001001001101
// 010110101100110
// 101101101101010101010101101010010110101101010100101010
// 01011010100011010110
// 0101010101110100100101010110111101011011100011001
