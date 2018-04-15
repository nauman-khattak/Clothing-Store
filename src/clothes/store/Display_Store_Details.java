package clothes.store;

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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

public class Display_Store_Details extends JFrame {

    String dbUserName = "hudayjah"; //database username, you entered at the time of creation, leave blank if you didn't specified
    String dbPassword = "hudayjah"; 
//    if you want to that database is created if it isn't already then use create=true in dbURL i.e uncomment the below dbURL and use it
//    String dbURL = "jdbc:derby://localhost:1527/Clothes-Store-DB;create=true;user="+dbUserName+";password="+dbPassword;
//    I created the database with user=hudayjah  and password=hudayjah
    String dbURL = "jdbc:derby://localhost:1527/Clothes-Store-DB;user=" + dbUserName + ";password=" + dbPassword;
    String tableName = " \"Clothes Store\" ";
    Connection conn = null; //jdbc connection
    Statement stmt = null;

    public Display_Store_Details() throws ClassNotFoundException {
        initializeUI();
        //insertClothesStore(5, "Shirts", "XL");
        shutdown();
    }

    public void initializeUI() throws ClassNotFoundException {
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();
        JPanel toppanel = new JPanel();
        JPanel labelpanel = new JPanel();
        toppanel.setLayout(new BorderLayout());
        setTitle("Clothes Store Data");
        setSize(900, 700);
        setLocation(250, 20);
        getContentPane().setBackground(Color.lightGray);
        setVisible(true);

        toppanel.setSize(850, 400);
        labelpanel.setSize(50, 590);
        Border thickBorder = new LineBorder(Color.RED, 2);

        // sets the dimensions and the maximum size required for the program//
        Dimension d = new Dimension(130, 50);
        Font f = new Font("SansSherif", Font.BOLD, 20);

        displayStoreContents(dataVector, columnNamesVector);
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

        //this will close the program when X at the top right corner get clicked
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void createConnection() {
        try {
            //DriverManager.registerDriver(new org.apache.derby.jdbc.EmbeddedDriver());
            Class.forName("org.apache.derby.jdbc.ClientDriver");
            conn = DriverManager.getConnection(dbURL); //Get a connection
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public void insertClothesStore(int quantity, String type, String size) {
        try {
            stmt = conn.createStatement();
            stmt.execute("insert into " + tableName + " values ("
                    + quantity + ",'" + type + "','" + size + "')");
            stmt.close();
        } catch (SQLException sqlExcept) {
            sqlExcept.printStackTrace();
        }
    }*/
    public void displayStoreContents(Vector dataVector, Vector columnNamesVector) throws ClassNotFoundException {
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        createConnection();
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select * From " + tableName);
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
            e.printStackTrace();
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

    private void shutdown() {
        try {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
//                DriverManager.getConnection(dbURL + ";shutdown=true");
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
