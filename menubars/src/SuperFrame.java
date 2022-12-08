import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SuperFrame extends JFrame {
    // Переменные
    private static final String url = "jdbc:mysql://localhost:3306/crm";
    private static final String user = "Igo";
    private static final String password = "12345";

    // Переменные для принятия данных из таблицы managers базы данных
    public String post;
    public String fio;
    public int idManager;
    public int idCompany;
    public String nameCompany;

    private static Connection con;
    private static Statement stmt;
    private static ResultSet rs;

    List<String> arrayManagers = new ArrayList<>();
    int indexIdManager = 0;

    // Конструктор

    // Методы
    public int bdQuerry (String query, int Do) {
        // Do = 1 == выполнить первый case свитча: заполнить форму
        // Do = 2 == выполнить первый case свитча: заполнить таблицу
        // Do = 3 == выполнить первый case свитча: вставить в таблицу данные


        int line = 0;

        try {
            con = DriverManager.getConnection(url, user, password);
            stmt = con.createStatement();

            switch (Do) {
                case  (1):
                    rs = stmt.executeQuery(query);
                    line = fillTable(rs); // Заполнить таблицу
                    break;
                case (2):
                    rs = stmt.executeQuery(query);
                    line = fillValue(rs); // Заполнить форму
                    break;
                case (3):
                    stmt.executeUpdate(query); // Передать в таблицу
                    break;
                case (4):
                    rs = stmt.executeQuery(query); //
                    line = fillManagers(rs);
                    break;
                default:
                    break;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        } finally {
            try { con.close(); } catch(SQLException se) { /**/ }
            try { stmt.close(); } catch(SQLException se) { /**/ }
            try {   if(rs != null) {
                    rs.close();
                    }
                }
            catch(SQLException se) { /**/ }
        }

        return line;
    }

    public int fillTable(ResultSet rs){
        return  0;
    }

    public int fillValue(ResultSet rs){
        return  0;
    }

    public int fillManagers(ResultSet rs){
        return  0;
    }

}
