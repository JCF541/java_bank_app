package Controller;

import Master.Account;
import View.BankView;
import Master.Client;
import java.util.List;

public class BankController {
    private final ClientDao clientDao;
    private final AccountDao accountDao;
    private final BankView bankView;
    
    // Inputs: ClientDao, AccountDao, and BankView objects
    // Internal Processes: Assigns the provided objects to the class attributes
    // Outputs: None (constructor)
    public BankController(ClientDao clientDao, AccountDao accountDao, BankView view) {
        this.clientDao = clientDao;
        this.accountDao = accountDao;
        this.bankView = view;
    }
    
    // Inputs: First name, last name, and email as strings
    // Internal Processes: Creates a new Client object and attempts to store it using the ClientDao. If successful, displays the client info.
    // Outputs: None (void function)
    public void createClient(String firstName, String lastName, String email) {
        Client client = new Client(firstName, lastName, email);
        Client createdClient = clientDao.createClient(client);
        if (createdClient != null) {
            bankView.displayClientInfo(createdClient);
        } else {
            bankView.displayError("Failed to create client.");
        }
    }

    // Inputs: Client id as integer
    // Internal Processes: Attempts to retrieve a Client with the provided id using the ClientDao. If successful, displays the client info.
    // Outputs: None (void function)
    public void getClientById(int id) {
        Client client = clientDao.getClientById(id);
        if (client != null) {
            bankView.displayClientInfo(client);
        } else {
            bankView.displayError("Client not found.");
        }
    }

    // Inputs: None
    // Internal Processes: Retrieves all clients using the ClientDao and displays them.
    // Outputs: None (void function)
    public void getAllClients() {
        List<Client> clients = clientDao.getAllClients();
        bankView.displayClientList(clients);
    }

    // Inputs: Client object
    // Internal Processes: Attempts to update a client using the ClientDao. If successful, displays a success message.
    // Outputs: None (void function)
    public void updateClient(Client client) {
        boolean updated = clientDao.updateClient(client);
        if (updated) {
            bankView.displaySuccess("Client updated successfully.");
        } else {
            bankView.displayError("Failed to update client.");
        }
    }

    // Inputs: Client id as integer
    // Internal Processes: Attempts to delete a client using the ClientDao. If successful, displays a success message.
    // Outputs: None (void function)
    public void deleteClient(int id) {
        boolean deleted = clientDao.deleteClient(id);
        if (deleted) {
            bankView.displaySuccess("Client deleted successfully.");
        } else {
            bankView.displayError("Failed to delete client.");
        }
    }

    // Inputs: Client id as integer, Account balance as double
    // Internal Processes: Creates a new Account object and attempts to store it using the AccountDao. If successful, displays the account info.
    // Outputs: None (void function)
    public void createAccount(int clientId, double balance) {
        Account account = new Account(clientId, balance);
        Account createdAccount = accountDao.createAccount(account);
        if (createdAccount != null) {
            bankView.displayAccountInfo(createdAccount);
        } else {
            bankView.displayError("Failed to create account.");
        }
    }

    // Inputs: Account id as integer
    // Internal Processes: Attempts to retrieve an Account with the provided id using the AccountDao. If successful, displays the account info.
    // Outputs: None (void function)
    public void getAccountById(int id) {
        Account account = accountDao.getAccountById(id);
        if (account != null) {
            bankView.displayAccountInfo(account);
        } else {
            bankView.displayError("Account not found.");
        }
    }

    // Inputs: Client id as integer
    // Internal Processes: Retrieves all accounts associated with the provided client id using the AccountDao and displays them.
    // Outputs: None (void function)
    public void getAccountsByClientId(int clientId) {
        List<Account> accounts = accountDao.getAccountsByClientId(clientId);
         bankView.displayAccountList(accounts);
    }

    // Inputs: Account object
    // Internal Processes: Attempts to update an account using the AccountDao. If successful, displays a success message.
    // Outputs: None (void function)
    public void updateAccount(Account account) {
        boolean updated = accountDao.updateAccount(account);
        if (updated) {
            bankView.displaySuccess("Account updated successfully.");
        } else {
            bankView.displayError("Failed to update account.");
        }
    }

    // Inputs: Account id as integer
    // Internal Processes: Attempts to delete an account using the AccountDao. If successful, displays a success message.
    // Outputs: None (void function)
    public void deleteAccount(int id) {
        boolean deleted = accountDao.deleteAccount(id);
        if (deleted) {
            bankView.displaySuccess("Account deleted successfully.");
        } else {
            bankView.displayError("Failed to delete account.");
        }
    }
}

