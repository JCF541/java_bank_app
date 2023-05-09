package Controller;

import Master.Client;
import java.util.List;

public interface ClientDao {
     /**
     * Input: A Client object
     * Process: Creates a client in the database using the client object's details
     * Output: Returns the created Client object or null if the client creation fails
     */
    Client createClient(Client client);
    
    /**
     * Input: A client id
     * Process: Fetches the client from the database matching the provided id
     * Output: Returns the Client object if found or null if no client matches the provided id
     */
    Client getClientById(int id);
    
    /**
     * Input: None
     * Process: Fetches all clients from the database
     * Output: Returns a list of Client objects or an empty list if no clients are found
     */
    List<Client> getAllClients();
    
    /**
     * Input: A Client object
     * Process: Updates the client in the database matching the client object's id with the client object's details
     * Output: Returns true if the client was updated successfully or false if the client update failed
     */
    boolean updateClient(Client client);
    
    /**
     * Input: A Client object
     * Process: Deletes the client in the database matching the client object's id with the client object's details
     * Output: Returns true if the client was deleted successfully or false if the client deletion failed
     */
    boolean deleteClient(int id);
}


