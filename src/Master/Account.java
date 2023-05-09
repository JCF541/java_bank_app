package Master;

public class Account {

    private int id;
    private int clientId;
    private double balance;

    // Inputs: Client id as an integer, balance as a double
    // Internal Processes: Initializes the instance variables with provided values
    // Outputs: None (constructor
    public Account(int clientId, double balance) {
        this.clientId = clientId;
        this.balance = balance;
    }

    // Getters and setters
    // Inputs: None for getters, respective type value for setters
    // Internal Processes: Returns the respective value for getters, sets the respective value for setters
    // Outputs: Respective type value for getters, none for setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}


