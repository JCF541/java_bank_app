package Controller;

import Master.Client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLClientDao implements ClientDao {

    private String url;
    private String user;
    private String password;
    
    // Inputs: None
    // Internal Processes: Load PostgreSQL driver
    // Outputs: None
    static {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Inputs: Database URL as a string, user as a string, password as a string
    // Internal Processes: Initializes the instance variables with provided values
    // Outputs: None (constructor)
    public PostgreSQLClientDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Inputs: None
    // Internal Processes: Connects to the PostgreSQL database using instance variables
    // Outputs: Connection object
    private Connection connect() throws Exception {
        Connection conn = DriverManager.getConnection(url, user, password);
        return conn;
    }

    // Inputs: Client object
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and retrieves generated keys to set the ID of the client
    // Outputs: Client object with updated ID or null if operation fails
    @Override
    public Client createClient(Client client) {
        String SQL = "INSERT INTO clients (first_name, last_name, email) VALUES (?, ?, ?) RETURNING id";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setString(3, client.getEmail());
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt(1);
                client.setId(id);
                return client;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return null;
    }

    // Inputs: Client id as an integer
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and retrieves result set to create client object
    // Outputs: Client object or null if client is not found
    @Override
    public Client getClientById(int id) {
        String SQL = "SELECT * FROM clients WHERE id = ?";
        Client client = null;

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                client = new Client(id, firstName, lastName, email);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return client;
    }

    // Inputs: None
    // Internal Processes: Connects to database, prepares SQL statement, executes SQL and retrieves result set to create list of clients
    // Outputs: List of Client objects
    @Override
    public List<Client> getAllClients() {
        String SQL = "SELECT * FROM clients";
        List<Client> clients = new ArrayList<>();

        try (Connection conn = connect(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                Client client = new Client(id, firstName, lastName, email);
                clients.add(client);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return clients;
    }

    // Inputs: Client object
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and checks affected rows
    // Outputs: True if update was successful, false otherwise
    @Override
    public boolean updateClient(Client client) {
        String SQL = "UPDATE clients SET first_name = ?, last_name = ?, email = ? WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setString(1, client.getFirstName());
            pstmt.setString(2, client.getLastName());
            pstmt.setString(3, client.getEmail());
            pstmt.setInt(4, client.getId());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }

    // Inputs: Client id as an integer
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and checks affected rows
    // Outputs: True if delete was successful, false otherwise
    @Override
    public boolean deleteClient(int id) {
        String SQL = "DELETE FROM clients WHERE id = ?";

        try (Connection conn = connect(); PreparedStatement pstmt = conn.prepareStatement(SQL)) {

            pstmt.setInt(1, id);
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                return true;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        return false;
    }
}


