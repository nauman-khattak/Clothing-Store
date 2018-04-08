package clothes.store;

//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
//import java.sql.SQLException;
//import java.sql.Statement;
//
//public class Display_Store_Details {
//
//    String dbURL = "jdbc:derby://localhost:1527/Clothes-Store-DB;create=true;user=hudayjah;password=hudayjah";
//    String tableName = " \"Clothes Store\" ";
//    // jdbc Connection
//    private static Connection conn = null;
//    private static Statement stmt = null;
//
//    public Display_Store_Details() {
//        createConnection();
////        insertRestaurants(5, "LaVals", "Berkeley");
//        selecClothesStore();
//        shutdown();
//    }
//
//    private void createConnection() {
//        try {
////            DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
//            Class.forName("org.apache.derby.jdbc.ClientDriver");
//            //Get a connection
//            conn = DriverManager.getConnection(dbURL);
//        } catch (Exception except) {
//            except.printStackTrace();
//        }
//    }
//
//    /*private static void insertRestaurants(int id, String restName, String cityName) {
//        try {
//            stmt = conn.createStatement();
//            stmt.execute("insert into " + tableName + " values ("
//                    + id + ",'" + restName + "','" + cityName + "')");
//            stmt.close();
//        } catch (SQLException sqlExcept) {
//            sqlExcept.printStackTrace();
//        }
//    }*/
//
//    private void selecClothesStore() {
//        try {
//            stmt = conn.createStatement();
//            ResultSet results = stmt.executeQuery("select * from " + tableName);
//            ResultSetMetaData rsmd = results.getMetaData();
//            int numberCols = rsmd.getColumnCount();
//            for (int i = 1; i <= numberCols; i++) {
//                //print Column Names
//                System.out.print(rsmd.getColumnLabel(i) + "\t\t");
//            }
//
//            System.out.println("\n-------------------------------------------------");
//
//            while (results.next()) {
//                int quantity = results.getInt(1);
//                String type = results.getString(2);
//                String size = results.getString(3);
//                System.out.println(quantity + "\t\t" + type + "\t\t" + size);
//            }
//            results.close();
//            stmt.close();
//        } catch (SQLException sqlExcept) {
//            sqlExcept.printStackTrace();
//        }
//    }
//
//    private void shutdown() {
//        try {
//            if (stmt != null) {
//                stmt.close();
//            }
//            if (conn != null) {
//                DriverManager.getConnection(dbURL + ";shutdown=true");
//                conn.close();
//            }
//        } catch (SQLException sqlExcept) {
//
//        }
//
//    }
//}

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class Display_Store_Details extends JFrame {

    public Display_Store_Details() throws SQLException, ClassNotFoundException {
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();
        JPanel toppanel = new JPanel();
        JPanel labelpanel = new JPanel();
        toppanel.setLayout(new BorderLayout());
        // this set code is for count it will count the number of registered product are
        // inside the entire record of the database and appear on screen.//
        JLabel countsent = new JLabel("The Total Registered Students are: ");
        JLabel count = new JLabel();
        setTitle("Welcome to Student Fee Dues Page");
        setSize(900, 700);
        setLocation(250, 20);
        getContentPane().setBackground(Color.lightGray);
        setVisible(true);
        // set set of code allows to set the button bond size
        //and  setvisible as true to ensure tha the button appear on screen//

        toppanel.setSize(850, 400);
        labelpanel.setSize(50, 590);
        Border thickBorder = new LineBorder(Color.RED, 2);

        // sets the dimensions and the maximum size required for the program//
        Dimension d = new Dimension(130, 50);
        Font f = new Font("SansSherif", Font.BOLD, 20);
        count.setBorder(thickBorder);
        count.setFont(f);
        // background color for count label//
        count.setBackground(Color.WHITE);
        // this code below adds labls and button textboxes so that when the program
        // runs they all will appear on screen as a user interface//
        labelpanel.add(count);
        count.setForeground(Color.RED);

        displayFeesDue(dataVector, columnNamesVector);
        //  Create table with database data    
        JTable table = new JTable(dataVector, columnNamesVector) {
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();
                    }
                }

                return Object.class;
            }
        };
// below here are the settings for the tabble 
// example like background color layout settings table font size//
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(34, 640, 830, 400);
        scrollPane.setViewportView(table);
        //add(scrollPane, BorderLayout.CENTER);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(0, 96, 169));
        table.getTableHeader().setForeground(Color.BLACK);
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setDragEnabled(false);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(scrollPane.getWidth(), 89));
        table.getTableHeader().setReorderingAllowed(false);
        table.setFont(new Font("Tahoma", Font.BOLD, 10));
        table.setVisible(true);
        table.setRowHeight(0, 50);
        table.setRowMargin(5);
        TableColumnModel columnModel = table.getColumnModel();
        table.setRowHeight(20);
        table.getTableHeader().setColumnModel(columnModel);
        toppanel.add(scrollPane);
        add(labelpanel, BorderLayout.NORTH);
        add(toppanel, BorderLayout.CENTER);

        revalidate();
        repaint();
        scrollPane.setVisible(true);
        getContentPane().setBackground(Color.DARK_GRAY);
// When X button is pressed the program will exit //
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void displayFeesDue(Vector dataVector, Vector columnNamesVector) throws ClassNotFoundException {
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        String url = "jdbc:derby://localhost:1527/Clothes-Store-DB;user=hudayjah;password=hudayjah";
        String sql = "SELECT * FROM \"Clothes Store\" ";

        // Java SE 7 has try-with-resources
        // This will ensure that the sql objects are closed when the program 
        // is finished with them
        try (Connection connection = DriverManager.getConnection(url);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();
            //  Get row data
            ResultSetMetaData metaData = rs.getMetaData();
            int count = metaData.getColumnCount(); //number of column

            for (int i = 1; i <= count; i++) {
                columnNames.add(metaData.getColumnLabel(i));
            }

            while (rs.next()) {
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++) {
                    row.add(rs.getObject(i));
                }

                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        for (int i = 0; i < data.size(); i++) {
            ArrayList subArray = (ArrayList) data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++) {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++) {
            columnNamesVector.add(columnNames.get(i));
        }
    }

}

