package Master;

import View.BankView;
import Controller.AccountDao;
import Controller.ClientDao;
import Controller.BankController;
import Controller.PostgreSQLAccountDao;
import Controller.PostgreSQLClientDao;

public class Main {

    public static void main(String[] args) {
        // Replace with your actual PostgreSQL database credentials
        String url = "jdbc:postgresql://localhost:5432/bank_db";
        String user = "postgres";
        String password = "trauboN541!";

        ClientDao clientDao = new PostgreSQLClientDao(url, user, password);
        AccountDao accountDao = new PostgreSQLAccountDao(url, user, password);

        BankView view = new BankView();
        BankController controller = new BankController(clientDao, accountDao, view);

        // Set the controller for the view after creating both instances
        view.setController(controller);

        // Load initial data
        controller.getAllClients();
        if (view.clientTable.getRowCount() > 0) {
            view.clientTable.setRowSelectionInterval(0, 0);
            int clientId = (int) view.clientTable.getValueAt(0, 0);
            controller.getAccountsByClientId(clientId);
        }
    }
}
