
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class Main {
    // jdbc connetion
    static Connection conn;
    static Statement stmt;
    static PreparedStatement pstmt;
    static int id = -1;
    static boolean login = false;
    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/userdata", "root", "");
            stmt = conn.createStatement();
             //pstmt = conn.prepareStatement(null);
            System.out.println("Connection Success");
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (Exception e) {
            System.out.println(e);
        }

    }
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            System.out.println("---------------------------");
            System.out.println("1 : Registration");
            System.out.println("2 : Login");
            System.out.println("3 : Update");
            System.out.println("4 : Delete");
            System.out.println("5 : Exit");
            System.out.println("6 : Show Profile");
            System.out.println("--------------------------");
            System.out.println("Enter your choise :");
            int ch = sc.nextInt();
            switch (ch) {
                case 1:
                    register();
                    break;

                case 2:
                    login();

                    break;

                case 3:
                    Update();
                    break;

                case 4:
                    delete();

                    break;

                case 6:
                    showData();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Enter valid input  ");

            }
            sc.nextLine();
            if (ch == 5)
                break;
        }

    }

    // ------------------------------------------------
    public static void register() {
        sc.nextLine();
        System.out.println("Enter name  : ");
        String name = sc.nextLine();
        System.out.println("Enter Email id :-");
        String email = sc.nextLine();
        System.out.println("Eneter phone number :");
        String num = sc.nextLine();

        System.out.println("Enter password");

        String pass = sc.nextLine();

        System.out.println("Enter Address");

        String Add = sc.nextLine();
        System.out.println("Success");
        // query
        String query = "insert into Student values('" + name + "' , '" + email + "', '" + num + "' ,'" + pass + "',0,'"
                + Add + "')";

        try {
            stmt.executeUpdate(query);
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // --------------------------------------------------------
    public static void login() {

        System.out.println("Enter Email id :-");
        sc.nextLine();
        String email = sc.nextLine();

        System.out.println("Enter password");

        String pass = sc.nextLine();

        // other code
        String query = "Select pass,id from Student where email = '" + email + "'";
        try {
            ResultSet set = stmt.executeQuery(query);
            while (set.next()) {
                if (pass.equals(set.getString("pass"))) {
                    System.out.println("Login Successfull");
                    id = set.getInt("id");
                    login = true;
                    break;
                }
            }
            if (!login) {
                System.out.println("Email or password  is wrong");
            }
        } catch (SQLException e) {

            e.printStackTrace();
        }
    }

    // ------------------------------------------------------
    // update
    public static void Update() {
        if (!login) {
            System.out.println("you must login first");
            sc.nextLine();
            return;
        }

        System.out.println("What you want to Update :");
        System.out.println("1  == name  ");
        System.out.println("2  == email  ");
        System.out.println("3  == mob  ");
        System.out.println("4  == password  ");
        System.out.println("5  == exit   ");
        int ch = sc.nextInt();
        String query = "";
        sc.nextLine();

        switch (ch) {

            case 1:
                // name
                System.out.println("Enter New Name : ");
                String name = sc.nextLine();
                query = "update Student set name = '" + name + "' where id = " + id;

                break;

            case 2:
                // email
                System.out.println("Enter New Email : ");
                String email = sc.nextLine();
                query = "update Student set email = '" + email + "' where id = " + id;

                break;
            case 3:
                // mob
                System.out.println("Enter New mob : ");
                String phone = sc.nextLine();
                query = "update Student set phone = '" + phone + "' where id = " + id;

                break;
            case 4:
                // password
                System.out.println("Enter New Password : ");
                String pass = sc.nextLine();
                query = "update Student set pass = '" + pass + "' where id = " + id;

                break;

            case 5:
                return;

            default:
                System.out.println("Enter valid input  ");

        }

        try {
            stmt.executeUpdate(query);
            System.out.println("Updation Successfull");
            sc.nextLine();
        } catch (SQLException e) {

            e.printStackTrace();
        }

    }

    // ------------------------------------------------------1
    public static void delete() {

        if (!login) {
            System.out.println("Please Login first");
            return;
        }
        String query = "delete from Student where id = " + id;
        try {
            stmt.executeUpdate(query);
            System.out.println("Account Delete Successfully..");
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    // show Profile
    public static void showData() {
        if (!login) {
            System.out.println("No data to show, Please login first");
        }
        String query = "Select * from Student where id =  " + id;
        try (ResultSet set = stmt.executeQuery(query)) {
            while (set.next()) {
                System.out.println("Name  : " + set.getString("name"));
                System.out.println("email  : " + set.getString("email"));
                System.out.println("Address  : " + set.getString("address"));
                System.out.println(" Mobile Number : " + set.getString("phone"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

}