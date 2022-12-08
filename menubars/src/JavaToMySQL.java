import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JavaToMySQL {

    // JDBC URL, username and password of MySQL server
    private static final String url = "jdbc:mysql://localhost:3306/test2";
    private static final String user = "Igo";
    private static final String password = "12345";

    // JDBC variables for opening and managing connection
    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    public static void main(String args[]) {

        String query = "INSERT INTO test2.books (id, name, author) \n" +
                " VALUES (5, 'Head First Java3', 'Kathy Sieara3');"; // 3. Вставляем данные в таблицу

        try {
            // opening database connection to MySQL server
            con = DriverManager.getConnection(url, user, password);

            // getting Statement object to execute query
            stmt = con.createStatement();
            stmt.executeUpdate(query); // 3. Вставляем данные в таблицу

            // executing SELECT query
            rs = stmt.executeQuery(query); // 2. Запрос содержимого строк и столбоц


            // Запрос на количество строк
            while (rs.next()) {
                String id = rs.getString("fio");
            }


        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            //close connection ,stmt and resultset here
            try { con.close(); } catch(SQLException se) { /*can't do anything */ }
            try { stmt.close(); } catch(SQLException se) { /*can't do anything */ }
            try { rs.close(); } catch(SQLException se) { /*can't do anything */ }
        }
    }

}