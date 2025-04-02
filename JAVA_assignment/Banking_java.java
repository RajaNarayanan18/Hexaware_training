package test1;
 
import java.util.*;

public class Banking_java {
	
	// Task 1: check Loan Eligibility
    public static void checkLoanElig(int cScore, double aIncome) {
        if (cScore > 700 && aIncome >= 50000) {
            System.out.println(" Customer valid..Loan Approved!");
        } else {
            System.out.println("Loan Denied: Low credit score or income.");
        }
    }

    // Task 2: ATM Transaction
    public static void atmTxn() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter balance: ");
        double bal = sc.nextDouble();

        System.out.println("1. Check Balance\n2. Withdraw\n3. Deposit");
        int ch = sc.nextInt();

        if (ch == 1) {
            System.out.println("Balance: " + bal);
        } else if (ch == 2) {
            System.out.print("Withdraw amt: ");
            double wAmt = sc.nextDouble();
            if (wAmt > bal) {
                System.out.println("Failed: Low balance.");
            } else if (wAmt % 100 == 0 || wAmt % 500 == 0) {
                bal -= wAmt;
                System.out.println("Success! New Bal: " + bal);
            } else {
                System.out.println("Failed: Must be multiple of 100/500.");
            }
        } else if (ch == 3) {
            System.out.print("Deposit amt: ");
            double dAmt = sc.nextDouble();
            bal += dAmt;
            System.out.println("Success! New Bal: " + bal);
        } else {
            System.out.println("Invalid option.");
        }
    }

    // Task 3:Compound Interest Calculation
    public static void calcCompInt() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter customers to cal compound interest: ");
        int numC = sc.nextInt();

        for (int i = 0; i < numC; i++) {
            System.out.print("Init Bal: ");
            double iBal = sc.nextDouble();
            System.out.print("Interest (%): ");
            double aRate = sc.nextDouble();
            System.out.print("Years: ");
            int yrs = sc.nextInt();

            double fBal = iBal * Math.pow((1 + aRate / 100), yrs);
            System.out.printf("Future Bal: $%.2f\n", fBal);
        }
    }

    // Task 4: Account Balance Check
    public static void accBalCheck() {
        Scanner sc = new Scanner(System.in);
        Map<String, Double> accs = new HashMap<>();
        accs.put("1001", 5000.0);
        accs.put("1002", 10000.0);
        accs.put("1003", 7500.0);

        while (true) {
            System.out.print("Enter acc no: ");
            String accNum = sc.next();
            if (accs.containsKey(accNum)) {
                System.out.println("Balance: " + accs.get(accNum));
                break;
            } else {
                System.out.println("Invalid acc. Try again.");
            }
        }
    }

    // Task 5: Password Validation
    public static void valPass() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Create pass: ");
        String pass = sc.next();

        if (pass.length() >= 8 && pass.matches(".*[A-Z].*") && pass.matches(".*\\d.*")) {
            System.out.println("Pass valid.");
        } else {
            System.out.println("Invalid pass. 8+ chars, 1 upper, 1 digit.");
        }
    }

    // Task 6: Transaction History
    public static void txnHist() {
        Scanner sc = new Scanner(System.in);
        List<String> txns = new ArrayList<>();
        double bal = 0;

        while (true) {
            System.out.println("1. Deposit\n2. Withdraw\n3. View Txns\n4. Exit");
            int ch = sc.nextInt();

            if (ch == 1) {
                System.out.print("Deposit amt: ");
                double amt = sc.nextDouble();
                bal += amt;
                txns.add("Deposited: " + amt);
                System.out.println("Success! Bal: " + bal);
            } else if (ch == 2) {
                System.out.print("Withdraw amt: ");
                double amt = sc.nextDouble();
                if (amt > bal) {
                    System.out.println("Low balance.");
                } else {
                    bal -= amt;
                    txns.add("Withdrawn: " + amt);
                    System.out.println("Success! Bal: " + bal);
                }
            } else if (ch == 3) {
                System.out.println("Txns:");
                for (String t : txns) {
                    System.out.println(t);
                }
            } else if (ch == 4) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Credit Score: ");
        int cScore = sc.nextInt();
        System.out.print("Enter Annual Income: ");
        double aIncome = sc.nextDouble();
        checkLoanElig(cScore, aIncome);
        atmTxn();
        calcCompInt();
        accBalCheck();
        valPass();
        txnHist();
    }
}
