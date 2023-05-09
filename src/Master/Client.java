package Master;

public class Client {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    
    // Inputs: id as an integer, firstName as String, lastName as String, email as String
    // Internal Processes: Initializes the instance variables with provided values
    // Outputs: None (constructor)
    public Client(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public Client(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
