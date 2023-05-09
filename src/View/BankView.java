package View;

import Controller.BankController;
import Master.Account;
import Master.Client;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class BankView {

    public JFrame frame;
    public JTable clientTable;
    public JTable accountTable;
    public JTextField clientIdField;
    public JTextField clientFirstNameField;
    public JTextField clientLastNameField;
    public JTextField clientEmailField;
    public JTextField accountIdField;
    public JTextField accountClientIdField;
    public JTextField accountBalanceField;
    public BankController controller;
    public DefaultTableModel clientTableModel;
    public DefaultTableModel accountTableModel;

    public BankView() {
        initComponents();
    }

    public void setController(BankController controller) {
        this.controller = controller;
    }

    private void initComponents() {
        frame = new JFrame("Banking App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 1024);

        // Top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        // Client table
        clientTableModel = new DefaultTableModel(new Object[]{"ID", "First Name", "Last Name", "Email"}, 0);
        clientTable = new JTable(clientTableModel);
        clientTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        clientTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = clientTable.getSelectedRow();
                if (selectedRow >= 0) {
                    int clientId = (int) clientTable.getValueAt(selectedRow, 0);
                    controller.getAccountsByClientId(clientId);
                }
            }
        });
        JScrollPane clientTableScrollPane = new JScrollPane(clientTable);
        topPanel.add(clientTableScrollPane, BorderLayout.CENTER);

// Client form
        JPanel clientFormPanel = new JPanel();
        GridBagLayout gridBagLayout = new GridBagLayout();
        clientFormPanel.setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1;
        topPanel.add(clientFormPanel, BorderLayout.EAST);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        clientFormPanel.add(new JLabel("ID:"), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        clientIdField = new JTextField(10);
        clientFormPanel.add(clientIdField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        clientFormPanel.add(new JLabel("First Name:"), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        clientFirstNameField = new JTextField(10);
        clientFormPanel.add(clientFirstNameField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        clientFormPanel.add(new JLabel("Last Name:"), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        clientLastNameField = new JTextField(10);
        clientFormPanel.add(clientLastNameField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        clientFormPanel.add(new JLabel("Email:"), gridBagConstraints);

        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        clientEmailField = new JTextField(10);
        clientFormPanel.add(clientEmailField, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        
        JButton createClientButton = new JButton("Create");
        createClientButton.addActionListener(e -> {
            String firstName = clientFirstNameField.getText();
            String lastName = clientLastNameField.getText();
            String email = clientEmailField.getText();
            controller.createClient(firstName, lastName, email);
            controller.getAllClients();

            // Clear the input fields
            clientIdField.setText("");
            clientFirstNameField.setText("");
            clientLastNameField.setText("");
            clientEmailField.setText("");
        });
        clientFormPanel.add(createClientButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        
        JButton updateClientButton = new JButton("Update");
        updateClientButton.addActionListener(e -> {
            if (!clientIdField.getText().isEmpty() && !clientFirstNameField.getText().isEmpty() && !clientLastNameField.getText().isEmpty() && !clientEmailField.getText().isEmpty()) {
                int id = Integer.parseInt(clientIdField.getText());
                String firstName = clientFirstNameField.getText();
                String lastName = clientLastNameField.getText();
                String email = clientEmailField.getText();
                Client client = new Client(id, firstName, lastName, email);
                controller.updateClient(client);
                controller.getAllClients();

                // Clear the input fields
                clientIdField.setText("");
                clientFirstNameField.setText("");
                clientLastNameField.setText("");
                clientEmailField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields before updating.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        clientFormPanel.add(updateClientButton, gridBagConstraints);

        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.NONE;
        gridBagConstraints.anchor = GridBagConstraints.CENTER;
        
        JButton deleteClientButton = new JButton("Delete");
        deleteClientButton.addActionListener(e -> {
            int id = Integer.parseInt(clientIdField.getText());
            controller.deleteClient(id);
            controller.getAllClients();
        });
        clientFormPanel.add(deleteClientButton, gridBagConstraints);
        

        // Bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        //frame.add(bottomPanel, BorderLayout.CENTER);

        // Account table
        accountTableModel = new DefaultTableModel(new Object[]{"ID", "Client ID", "Balance"}, 0);
        accountTable = new JTable(accountTableModel);
        JScrollPane accountTableScrollPane = new JScrollPane(accountTable);
        bottomPanel.add(accountTableScrollPane, BorderLayout.CENTER);

        // Account form
        JPanel accountFormPanel = new JPanel();
        GridBagLayout accountGridBagLayout = new GridBagLayout();
        accountFormPanel.setLayout(accountGridBagLayout);
        GridBagConstraints accountGridBagConstraints = new GridBagConstraints();
        accountGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        accountGridBagConstraints.weightx = 1;
        bottomPanel.add(accountFormPanel, BorderLayout.EAST);

        accountGridBagConstraints.gridx = 0;
        accountGridBagConstraints.gridy = 0;
        accountFormPanel.add(new JLabel("ID:"), accountGridBagConstraints);

        accountGridBagConstraints.gridx = 1;
        accountGridBagConstraints.gridy = 0;
        accountIdField = new JTextField(10);
        accountFormPanel.add(accountIdField, accountGridBagConstraints);

        accountGridBagConstraints.gridx = 0;
        accountGridBagConstraints.gridy = 1;
        accountFormPanel.add(new JLabel("Client ID:"), accountGridBagConstraints);

        accountGridBagConstraints.gridx = 1;
        accountGridBagConstraints.gridy = 1;
        accountClientIdField = new JTextField(10);
        accountFormPanel.add(accountClientIdField, accountGridBagConstraints);

        accountGridBagConstraints.gridx = 0;
        accountGridBagConstraints.gridy = 2;
        accountFormPanel.add(new JLabel("Balance:"), accountGridBagConstraints);

        accountGridBagConstraints.gridx = 1;
        accountGridBagConstraints.gridy = 2;
        accountBalanceField = new JTextField(10);
        accountFormPanel.add(accountBalanceField, accountGridBagConstraints);

        accountGridBagConstraints.gridx = 0;
        accountGridBagConstraints.gridy = 3;
        accountGridBagConstraints.gridwidth = 2;
        accountGridBagConstraints.fill = GridBagConstraints.NONE;
        accountGridBagConstraints.anchor = GridBagConstraints.CENTER;
        JButton createAccountButton = new JButton("Create");
        createAccountButton.addActionListener(e -> {
            int clientId = Integer.parseInt(accountClientIdField.getText());
            double balance = Double.parseDouble(accountBalanceField.getText());
            controller.createAccount(clientId, balance);
            controller.getAccountsByClientId(clientId);

            // Clear the input fields
            accountIdField.setText("");
            accountClientIdField.setText("");
            accountBalanceField.setText("");
        });
        accountFormPanel.add(createAccountButton, accountGridBagConstraints);

        accountGridBagConstraints.gridx = 0;
        accountGridBagConstraints.gridy = 4;
        accountGridBagConstraints.gridwidth = 2;
        accountGridBagConstraints.fill = GridBagConstraints.NONE;
        accountGridBagConstraints.anchor = GridBagConstraints.CENTER;
        JButton updateAccountButton = new JButton("Update");
        updateAccountButton.addActionListener(e -> {
            if (!accountIdField.getText().isEmpty() && !accountClientIdField.getText().isEmpty() && !accountBalanceField.getText().isEmpty()) {
                int id = Integer.parseInt(accountIdField.getText());
                int clientId = Integer.parseInt(accountClientIdField.getText());
                double balance = Double.parseDouble(accountBalanceField.getText());
                Account account = new Account(clientId, balance);
                account.setId(id);
                controller.updateAccount(account);
                controller.getAccountsByClientId(clientId);

                // Clear the input fields
                accountIdField.setText("");
                accountClientIdField.setText("");
                accountBalanceField.setText("");
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill in all fields before updating.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        accountFormPanel.add(updateAccountButton, accountGridBagConstraints);

        accountGridBagConstraints.gridx = 0;
        accountGridBagConstraints.gridy = 5;
        accountGridBagConstraints.gridwidth = 2;
        accountGridBagConstraints.fill = GridBagConstraints.NONE;
        accountGridBagConstraints.anchor = GridBagConstraints.CENTER;
        JButton deleteAccountButton = new JButton("Delete");
        deleteAccountButton.addActionListener(e -> {
            int id = Integer.parseInt(accountIdField.getText());
            int clientId = Integer.parseInt(accountClientIdField.getText()); // Add this line
            controller.deleteAccount(id);
            controller.getAccountsByClientId(clientId); // Add this line

            // Clear the input fields
            accountIdField.setText("");
            accountClientIdField.setText("");
            accountBalanceField.setText("");
        });

        accountFormPanel.add(deleteAccountButton, accountGridBagConstraints);

        // Add JSplitPane to manage top and bottom panels
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
        splitPane.setResizeWeight(0.5); // Make top and bottom panels have the same size
        frame.add(splitPane);

        frame.setVisible(true);
    }

    public void displayClientList(List<Client> clients) {
        clientTableModel.setRowCount(0);
        for (Client client : clients) {
            clientTableModel.addRow(new Object[]{client.getId(), client.getFirstName(), client.getLastName(), client.getEmail()});
        }
    }

    public void displayAccountList(List<Account> accounts) {
        accountTableModel.setRowCount(0);
        for (Account account : accounts) {
            accountTableModel.addRow(new Object[]{account.getId(), account.getClientId(), account.getBalance()});
        }
    }

    public void displayClientInfo(Client client) {
        clientIdField.setText(String.valueOf(client.getId()));
        clientFirstNameField.setText(client.getFirstName());
        clientLastNameField.setText(client.getLastName());
        clientEmailField.setText(client.getEmail());
    }

    public void displayAccountInfo(Account account) {
        accountIdField.setText(String.valueOf(account.getId()));
        accountClientIdField.setText(String.valueOf(account.getClientId()));
        accountBalanceField.setText(String.valueOf(account.getBalance()));
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(frame, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void displaySuccess(String message) {
        JOptionPane.showMessageDialog(frame, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
