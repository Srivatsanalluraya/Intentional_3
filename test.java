import java.io.*;
import java.sql.*;
import java.util.Scanner;

public class Main {

    // ---------- LOW RISK ----------
    public static void printUser(String name) {
        System.out.println("User: " + name); // harmless
    }


    // ---------- MEDIUM RISK ----------
    // Hardcoded password (medium)
    private static final String DB_PASS = "password123";


    // ---------- HIGH RISK (1 ONLY) ----------
    // SQL Injection (High)
    public static void vulnerableQuery(String userInput) throws Exception {

        Connection conn = DriverManager.getConnection(
            "jdbc:mysql://localhost/test",
            "root",
            DB_PASS
        );

        Statement stmt = conn.createStatement();

        // 🚨 SQL Injection
        String query = "SELECT * FROM users WHERE name = '" + userInput + "'";
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            System.out.println(rs.getString("name"));
        }

        conn.close();
    }


    // ---------- MEDIUM RISK ----------
    // Command execution (unsafe input)
    public static void runCommand(String cmd) throws IOException {

        // semgrep/bandit-like tools flag this
        Runtime.getRuntime().exec(cmd);
    }


    // ---------- LOW RISK ----------
    public static void safeMethod() {
        int a = 5;
        int b = 10;
        System.out.println(a + b);
    }


    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter name: ");
        String name = sc.nextLine();

        printUser(name);

        // Call medium risk
        runCommand("ls");

        // Call high risk
        vulnerableQuery(name);

        safeMethod();
    }
}
