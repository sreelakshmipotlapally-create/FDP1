import java.util.*;

// Class to store transaction details
class Transaction {
    String type;       // Deposit or Withdraw
    double amount;
    Date date;

    public Transaction(String type, double amount) {
        this.type = type;
        this.amount = amount;
        this.date = new Date(); // current date & time
    }

    public String toString() {
        return date + " | " + type + " | Amount: " + amount;
    }
}

// Bank Account Class
class BankAccount {
    private String accountNumber;
    private String accountHolder;
    private String pin;
    private double balance;
    private ArrayList<Transaction> history;

    // Constructor
    public BankAccount(String accNo, String name, String pin, double initialBalance) {
        this.accountNumber = accNo;
        this.accountHolder = name;
        this.pin = pin;
        this.balance = initialBalance;
        this.history = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    // PIN Validation
    public boolean validatePin(String enteredPin) {
        return pin.equals(enteredPin);
    }

    // Deposit
    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            history.add(new Transaction("Deposit", amount));
            System.out.println("Deposited: " + amount);
        } else {
            System.out.println("Invalid deposit amount!");
        }
    }

    // Withdraw
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            history.add(new Transaction("Withdraw", amount));
            System.out.println("Withdrawn: " + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    // Balance Check
    public void checkBalance() {
        System.out.println("Current Balance: " + balance);
    }

    // Show Transaction History
    public void showHistory() {
        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            System.out.println("\n--- Transaction History ---");
            for (Transaction t : history) {
                System.out.println(t);
            }
        }
    }

    // Interest Calculation
    public void calculateInterest(double rate, int years) {
        double interest = (balance * rate * years) / 100;
        System.out.println("Interest after " + years + " years at " + rate + "%: " + interest);
        System.out.println("Total balance with interest: " + (balance + interest));
    }
}

// Main Banking System
public class BankingSystem2 {
    private static Scanner sc = new Scanner(System.in);
    private static HashMap<String, BankAccount> accounts = new HashMap<>();

    public static void main(String[] args) {
        int choice;
        do {
            System.out.println("\n--- Java Bank ---");
            System.out.println("1. Create Account");
            System.out.println("2. Login to Account");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.out.println("Thank you for banking with us!");
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        } while (choice != 3);
    }

    // Create Account
    private static void createAccount() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();

        if (accounts.containsKey(accNo)) {
            System.out.println("Account number already exists!");
            return;
        }

        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();

        System.out.print("Set 4-digit PIN: ");
        String pin = sc.nextLine();

        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();

        BankAccount account = new BankAccount(accNo, name, pin, balance);
        accounts.put(accNo, account);

        System.out.println("Account created successfully!");
    }

    // Login
    private static void login() {
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();

        if (!accounts.containsKey(accNo)) {
            System.out.println("Account not found!");
            return;
        }

        BankAccount account = accounts.get(accNo);

        System.out.print("Enter PIN: ");
        String enteredPin = sc.nextLine();

        if (account.validatePin(enteredPin)) {
            System.out.println("Login successful! Welcome " + accNo);
            accountMenu(account);
        } else {
            System.out.println("Incorrect PIN!");
        }
    }

    // Account Menu
    private static void accountMenu(BankAccount account) {
        int choice;
        do {
            System.out.println("\n--- Account Menu ---");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            System.out.println("3. Check Balance");
            System.out.println("4. Transaction History");
            System.out.println("5. Interest Calculation");
            System.out.println("6. Logout");
            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    double dep = sc.nextDouble();
                    account.deposit(dep);
                    break;
                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    double wd = sc.nextDouble();
                    account.withdraw(wd);
                    break;
                case 3:
                    account.checkBalance();
                    break;
                case 4:
                    account.showHistory();
                    break;
                case 5:
                    System.out.print("Enter interest rate (%): ");
                    double rate = sc.nextDouble();
                    System.out.print("Enter time (years): ");
                    int years = sc.nextInt();
                    account.calculateInterest(rate, years);
                    break;
                case 6:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 6);
    }
}
