package Controller;

import Master.Account;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PostgreSQLAccountDao implements AccountDao {

    private final String url;
    private final String user;
    private final String password;
    
    // Inputs: None
    // Internal Processes: Attempts to load the PostgreSQL driver
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
    public PostgreSQLAccountDao(String url, String user, String password) {
        this.url = url;
        this.user = user;
        this.password = password;
    }

    // Inputs: None
    // Internal Processes: Connects to the PostgreSQL database using instance variables
    // Outputs: Connection object
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    // Inputs: Account object
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and retrieves generated keys to set the ID of the account
    // Outputs: Account object with updated ID
    @Override
    public Account createAccount(Account account) {
        String sql = "INSERT INTO accounts (client_id, balance) VALUES (?, ?)";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, account.getClientId());
            pstmt.setDouble(2, account.getBalance());

            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    account.setId(generatedKeys.getInt(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    // Inputs: Account id as an integer
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and retrieves result set to create account object
    // Outputs: Account object or null if account is not found
    @Override
    public Account getAccountById(int id) {
        String sql = "SELECT * FROM accounts WHERE id = ?";
        Account account = null;

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                account = new Account(rs.getInt("client_id"), rs.getDouble("balance"));
                account.setId(rs.getInt("id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    // Inputs: Client id as an integer
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and retrieves result set to create list of accounts
    // Outputs: List of Account objects
    @Override
    public List<Account> getAccountsByClientId(int clientId) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE client_id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, clientId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Account account = new Account(rs.getInt("client_id"), rs.getDouble("balance"));
                account.setId(rs.getInt("id"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    // Inputs: Account object
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and checks rows affected
    // Outputs: Boolean indicating whether the update was successful
    @Override
    public boolean updateAccount(Account account) {
        String sql = "UPDATE accounts SET client_id = ?, balance = ? WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, account.getClientId());
            pstmt.setDouble(2, account.getBalance());
            pstmt.setInt(3, account.getId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Inputs: Account object
    // Internal Processes: Connects to database, prepares SQL statement, sets parameters, executes SQL and checks rows affected
    // Outputs: Boolean indicating whether the deletion was successful
    @Override
    public boolean deleteAccount(int id) {
        String sql = "DELETE FROM accounts WHERE id = ?";

        try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}       

