import java.sql.*;
import java.util.Scanner;

public class InvoiceSystem {

    private static Scanner scanner = new Scanner(System.in);

    public static String scan() {
        return scanner.nextLine();
    }

    //Service Management Methods

    public static void addService(Connection conn) {
        try {
            System.out.println("Enter service name:");
            String name = scan();
            System.out.println("Enter billed hours:");
            int billedHours = Integer.parseInt(scan());
            String sqlInsert = "INSERT INTO services (name, billed_hours) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setString(1, name);
            pstmt.setInt(2, billedHours);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Service added successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viewService(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM services");
            if (!rs.isBeforeFirst()) {
                System.out.println("No services available.");
            } else {
                System.out.println("List of Services:");
                while (rs.next()) {
                    System.out.println("Service Name: " + rs.getString("name") + ", Billed Hours: " + rs.getInt("billed_hours"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void editService(Connection conn) {
        try {
            System.out.println("Enter service name to edit:");
            String name = scan();
            System.out.println("Enter new name:");
            String newName = scan();
            System.out.println("Enter new billed hours:");
            int newBilledHours = Integer.parseInt(scan());
            String sqlUpdate = "UPDATE services SET name = ?, billed_hours = ? WHERE name = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setString(1, newName);
            pstmt.setInt(2, newBilledHours);
            pstmt.setString(3, name);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Service information updated successfully!");
            } else {
                System.out.println("Service not found!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteService(Connection conn) {
        try {
            System.out.println("Enter service ID to delete:");
            int id = Integer.parseInt(scan());
            String sqlDelete = "DELETE FROM services WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Service deleted successfully!");
                // Update remaining service IDs if necessary
                updateServiceIDs(conn);
            } else {
                System.out.println("Service ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void updateServiceIDs(Connection conn) {
        try {
            // Update remaining service IDs
            String sqlUpdateIDs = "UPDATE services SET id = id - 1 WHERE id > ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdateIDs);
            pstmt.setInt(1, 1); // Start from ID 1
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Service IDs updated successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    // Client Management Methods

    
    public static void deleteClient(Connection conn) {
        try {
            System.out.println("Enter client ID to delete:");
            int id = Integer.parseInt(scan());
            String sqlDelete = "DELETE FROM clients WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Client deleted successfully!");
                // Update remaining client IDs if necessary
                updateClientIDs(conn);
            } else {
                System.out.println("Client ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void updateClientIDs(Connection conn) {
        try {
            // Update remaining client IDs
            String sqlUpdateIDs = "UPDATE clients SET id = id - 1 WHERE id > ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdateIDs);
            pstmt.setInt(1, 1); // Start from ID 1
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client IDs updated successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void editClient(Connection conn) {
        try {
            System.out.println("Enter client ID to edit:");
            int id = Integer.parseInt(scan());
            System.out.println("Enter new name:");
            String newName = scan();
            System.out.println("Enter new email:");
            String newEmail = scan();
            String sqlUpdate = "UPDATE clients SET name = ?, email = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setString(1, newName);
            pstmt.setString(2, newEmail);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Client information updated successfully!");
            } else {
                System.out.println("Client ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viewClient(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM clients");
            if (!rs.isBeforeFirst()) {
                System.out.println("No clients available.");
            } else {
                System.out.println("List of Clients:");
                while (rs.next()) {
                    System.out.println("Client ID: " + rs.getInt("id") + ", Name: " + rs.getString("name") + ", Email: " + rs.getString("email"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addClient(Connection conn) {
        try {
            System.out.println("Enter client name:");
            String name = scan();
            System.out.println("Enter client email:");
            String email = scan();
            String sqlInsert = "INSERT INTO clients (name, email) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Client added successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Invoice Management Methods

    public static void deleteInvoice(Connection conn) {
        try {
            System.out.println("Enter invoice ID to delete:");
            int id = Integer.parseInt(scan());
            String sqlDelete = "DELETE FROM invoices WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Invoice deleted successfully!");
                // Update remaining invoice IDs if necessary
                updateInvoiceIDs(conn);
            } else {
                System.out.println("Invoice ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    private static void updateInvoiceIDs(Connection conn) {
        try {
            // Update remaining invoice IDs
            String sqlUpdateIDs = "UPDATE invoices SET id = id - 1 WHERE id > ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdateIDs);
            pstmt.setInt(1, 1); // Start from ID 1
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Invoice IDs updated successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    

    public static void editInvoice(Connection conn) {
        try {
            System.out.println("Enter invoice ID to edit:");
            int id = Integer.parseInt(scan());
            System.out.println("Enter new client ID:");
            int newClientId = Integer.parseInt(scan());
            System.out.println("Enter new amount:");
            double newAmount = Double.parseDouble(scan());
            String sqlUpdate = "UPDATE invoices SET client_id = ?, amount = ? WHERE id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sqlUpdate);
            pstmt.setInt(1, newClientId);
            pstmt.setDouble(2, newAmount);
            pstmt.setInt(3, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Invoice information updated successfully!");
            } else {
                System.out.println("Invoice ID not found.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viewInvoice(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM invoices");
            if (!rs.isBeforeFirst()) {
                System.out.println("No invoices available.");
            } else {
                System.out.println("List of Invoices:");
                while (rs.next()) {
                    System.out.println("Invoice ID: " + rs.getInt("id") + ", Client ID: " + rs.getInt("client_id") + ", Amount: " + rs.getDouble("amount"));
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void addInvoice(Connection conn) {
        try {
            System.out.println("Enter client ID:");
            int clientId = Integer.parseInt(scan());
            System.out.println("Enter amount:");
            double amount = Double.parseDouble(scan());
            String sqlInsert = "INSERT INTO invoices (client_id, amount) VALUES (?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sqlInsert);
            pstmt.setInt(1, clientId);
            pstmt.setDouble(2, amount);
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Invoice added successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viewTotalBilledAmount(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT client_id, SUM(amount) AS total_amount FROM invoices GROUP BY client_id");
            if (!rs.isBeforeFirst()) {
                System.out.println("No billed amount available.");
            } else {
                System.out.println("Total Billed Amount for Each Client:");
                while (rs.next()) {
                    int clientId = rs.getInt("client_id");
                    double totalAmount = rs.getDouble("total_amount");
                    System.out.println("Client ID: " + clientId + ", Total Billed Amount: " + totalAmount);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void viewAllInvoicesForClient(Connection conn) {
        try {
            System.out.println("Enter client ID:");
            int clientId = Integer.parseInt(scan());
            String sql = "SELECT * FROM invoices WHERE client_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();
            if (!rs.isBeforeFirst()) {
                System.out.println("No invoices available for client with ID: " + clientId);
            } else {
                System.out.println("Invoices for Client ID: " + clientId);
                while (rs.next()) {
                    int invoiceId = rs.getInt("id");
                    double amount = rs.getDouble("amount");
                    System.out.println("Invoice ID: " + invoiceId + ", Amount: " + amount);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    //Choices Methods

    public static void ServiceChoice(Connection conn) {
        System.out.println("[1] Add Service");
        System.out.println("[2] View Service");
        System.out.println("[3] Edit Service");
        System.out.println("[4] Delete Service");
        System.out.println("[5] Go back");
        System.out.println("[6] Exit");
        String loginSelection = scan();
        if (loginSelection.equals("1")) {
            addService(conn);
    } else if (loginSelection.equals("2")) {
            viewService(conn);
    } else if (loginSelection.equals("3")) {
            editService(conn);
    } else if (loginSelection.equals("4")) {
            deleteService(conn);
    } else if (loginSelection.equals("5")) {
            selection2(conn);    
    } else if (loginSelection.equals("6")) {
            System.out.println("Quit.");
            System.exit(0);  
    }  else {
        System.out.println("Wrong input. Please input from the selection above.");
        selection2(conn);
    }
}

    public static void ClientChoice(Connection conn) {
        System.out.println("[1] Add Client ");
        System.out.println("[2] View Client");
        System.out.println("[3] Edit Client");
        System.out.println("[4] Delete Client");
        System.out.println("[5] Go back");
        System.out.println("[6] Exit");
        String loginSelection = scan();
    if (loginSelection.equals("1")) {
        addClient(conn);
    } else if (loginSelection.equals("2")) {
        viewClient(conn);
    } else if (loginSelection.equals("3")) {
        editClient(conn);
    } else if (loginSelection.equals("4")) {
        deleteClient(conn);
    } else if (loginSelection.equals("5")) {
        selection2(conn); 
    } else if (loginSelection.equals("6")) {
        System.out.println("Quit.");
        System.exit(0); 
    }  else {
        System.out.println("Wrong input. Please input from the selection above.");
        selection2(conn);
    }
}
    public static void InvoiceChoice(Connection conn) {
        System.out.println("[1] Add Invoice");
        System.out.println("[2] View Invoice");
        System.out.println("[3] Edit Invoice");
        System.out.println("[4] Delete Invoice");
        System.out.println("[5] Go back");
        System.out.println("[6] Exit");
    String loginSelection = scan();
    if (loginSelection.equals("1")) {
        addInvoice(conn);
    } else if (loginSelection.equals("2")) {
        viewInvoice(conn);
    } else if (loginSelection.equals("3")) {
        editInvoice(conn);
    } else if (loginSelection.equals("4")) {
        deleteInvoice(conn);
    } else if (loginSelection.equals("5")) {
        selection2(conn); 
    } else if (loginSelection.equals("6")) {
        System.out.println("Quit.");
        System.exit(0); 
    }  else {
        System.out.println("Wrong input. Please input from the selection above.");
        selection2(conn);
    }
}

public static void selection2(Connection conn) {
    while (true) {
        System.out.println("Input the desired number:");
        System.out.println("[1] Service Management");
        System.out.println("[2] Client Management");
        System.out.println("[3] Invoice Management");
        System.out.println("[4] View Total Billed Amount for Each Client");
        System.out.println("[5] View All Invoices for a Particular Client");
        System.out.println("[6] Exit");
        
        String loginSelection = scan();
        if (loginSelection.equals("1")) {
            ServiceChoice(conn);
        } else if (loginSelection.equals("2")) {
            ClientChoice(conn);
        } else if (loginSelection.equals("3")) {
            InvoiceChoice(conn);
        } else if (loginSelection.equals("4")) {
            viewTotalBilledAmount(conn);
        } else if (loginSelection.equals("5")) {
            viewAllInvoicesForClient(conn);
        } else if (loginSelection.equals("6")) {
            System.out.println("Quit.");
            System.exit(0);
        } else {
            System.out.println("Wrong input. Please input from the selection above.");
        }
    }
}

    /*public static void selection(Connection conn) {
        String loginSelection = scan();
        if (loginSelection.equals("1")) {
            addService(conn);
        } else if (loginSelection.equals("2")) {
            viewService(conn);
        } else if (loginSelection.equals("3")) {
            editService(conn);
        } else if (loginSelection.equals("4")) {
            deleteService(conn);
        } else if (loginSelection.equals("5")) {
            addClient(conn);
        } else if (loginSelection.equals("6")) {
            viewClient(conn);
        } else if (loginSelection.equals("7")) {
            editClient(conn);
        } else if (loginSelection.equals("8")) {
            deleteClient(conn);
        } else if (loginSelection.equals("9")) {
            addInvoice(conn);
        } else if (loginSelection.equals("10")) {
            viewInvoice(conn);
        } else if (loginSelection.equals("11")) {
            editInvoice(conn);
        } else if (loginSelection.equals("12")) {
            deleteInvoice(conn);
        } else if (loginSelection.equals("13")) {
            viewTotalBilledAmount(conn);
        } else if (loginSelection.equals("14")) {
            viewAllInvoicesForClient(conn);
        } else if (loginSelection.equals("15")) {
            System.out.println("Quit.");
            System.exit(0);
        } else {
            System.out.println("Wrong input. Please input from the selection above.");
            selection(conn);
        }
    }*/
    
    // Intro Method

    /*public static void intro(Connection conn) {
        while (true) {
            System.out.println("Choices\nService Management \n[1] - Add Service \n[2] - View Service \n[3] - Edit Service \n[4] - Delete Service" + "\n\nClient Management" +
                    " \n[5] - Add Client \n[6] - View Client \n[7] - Edit Client \n[8] - Delete Client" + "\n\nInvoice Management" + 
                    " \n[9] - Add Invoice \n[10] - View Invoice \n[11] - Edit Invoice \n[12] - Delete Invoice" + "\n\nViewTotal" +
                    " \n[13] - View Total Billed Amount for Each Client \n[14] - View All Invoices for a Particular Client" +
                    " \n[15] - Exit");
            selection(conn);
        }
    }*/

    // Main Method

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/invoicesystem",
                "myUser", "1234")) {
            selection2(conn);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
