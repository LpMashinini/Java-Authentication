import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        String name, email, password;

        Scanner userOptionInput = new Scanner(System.in);

        System.out.println("Select Option (1 OR 2): ");
        System.out.println("1) Sign up");
        System.out.println("2) Login ");

        int userOption = userOptionInput.nextInt();

        if (userOption == 1) {

            //Sign up option

            Hashing hashPass = new Hashing();

            Scanner userInput = new Scanner(System.in);

            System.out.println("Enter name : ");
            name = userInput.nextLine();

            System.out.println("Enter email : ");
            email = userInput.nextLine();

            System.out.println("Create password: ");
            password = userInput.nextLine();

            String hashedPass = hashPass.hashPassword(password);

            try {

                Connection connection = DbConnection.getConnection();
                String query = "INSERT INTO user(name,email,password) VALUES(?,?,?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);

                preparedStatement.setString(1, name);
                preparedStatement.setString(2, email);
                preparedStatement.setString(3, hashedPass);

                int result = preparedStatement.executeUpdate();

                if (result == 1) {
                    System.out.println("Details saved succesfully");
                } else {
                    System.out.println("Data not captured");
                }

                preparedStatement.close();
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (userOption == 2) {

            //Login option

            try {

                Validation verifyPass = new Validation();

                String auth_Email, auth_password;

                Scanner userAuthInput = new Scanner(System.in);

                System.out.println("Enter email : ");
                auth_Email = userAuthInput.nextLine();

                System.out.println("Enter password: ");
                auth_password = userAuthInput.nextLine();

                Connection connection = DbConnection.getConnection();
                String Query = "SELECT email,password FROM user";

                Statement statement = connection.createStatement();

                ResultSet resultSet = statement.executeQuery(Query);


                while (resultSet.next()) {

                    String Db_email = resultSet.getString("email");
                    String Db_password = resultSet.getString("password");

                    boolean validPass = verifyPass.verifyPassword(auth_password, Db_password);

                    if (validPass && auth_Email.equals(Db_email)) {
                        System.out.println("Login successfully");
                    } else {
                        System.out.println("Incorrect email or password");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        } else {
            //incorrect value input
            System.out.println("Enter correct value");
        }


    }
}