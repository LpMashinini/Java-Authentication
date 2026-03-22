import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{

        String name, email,password;

        Scanner userInput = new Scanner(System.in);

        System.out.println("Enter name : ");
        name = userInput.nextLine();

        System.out.println("Enter email : ");
        email = userInput.nextLine();

        System.out.println("Create password: ");
        password = userInput.nextLine();

        Hashing hashPass = new Hashing();
        String hashedPass = hashPass.hashPassword(password);

        try{

            Connection connection = DbConnection.getConnection();
            String query = "INSERT INTO user(name,email,password) VALUES(?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1,name);
            preparedStatement.setString(2,email);
            preparedStatement.setString(3,hashedPass);

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

    }
}