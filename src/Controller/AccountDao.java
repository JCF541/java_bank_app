package Controller;

import Master.Account;
import java.util.List;

public interface AccountDao {
     /**
     * Input: An Account object
     * Process: Creates an account in the database using the account object's details
     * Output: Returns the created Account object or null if the account creation fails
     */
    Account createAccount(Account account);
    
     /**
     * Input: An account id
     * Process: Fetches the account from the database matching the provided id
     * Output: Returns the Account object if found or null if no account matches the provided id
     */
    Account getAccountById(int id);
    
     /**
     * Input: A client id
     * Process: Fetches all accounts from the database associated with the provided client id
     * Output: Returns a list of Account objects or an empty list if no accounts are found for the client
     */
    List<Account> getAccountsByClientId(int clientId);
    
     /**
     * Input: An Account object
     * Process: Updates the account in the database matching the account object's id with the account object's details
     * Output: Returns true if the account was updated successfully or false if the account update failed
     */
    boolean updateAccount(Account account);
    
     /**
     * Input: An account id
     * Process: Deletes the account from the database matching the provided id
     * Output: Returns true if the account was deleted successfully or false if the account deletion failed
     */
    boolean deleteAccount(int id);
}

