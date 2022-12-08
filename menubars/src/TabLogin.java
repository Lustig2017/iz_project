import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TabLogin extends SuperFrame implements ActionListener {
    // Переменные
    // Для первого окна
    JPanel loginPasswordPanel = new JPanel();
    JTextField enterLoginText; // Поле Введите логин
    JTextField enterPasswordText; // Поле Введите пароль
    String loginFromBD = ""; // Логин
    String passFromBD= ""; // Пароль

    String postFromBD = ""; // Должность
    String fioFromBD = ""; // ФИО
    int idFromBD; // ID

    // Для второго окна
    JPanel loginAdminPanel = new JPanel();
    JTextField enterLoginAdminText; // Поле Введите логин
    JTextField enterPasswordAdminText; // Поле Введите пароль
    String loginFromBD2 = ""; // Логин
    String passFromBD2 = ""; // Пароль
    int idFromBD2; // ID
    Boolean isEnterAdmin = false;

    // Конструктор
    TabLogin(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // HIDE_ON_CLOSE
        this.setSize(380, 200);
        this.setResizable(false);
        this.setTitle("Авторизация");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        JTabbedPane tabbedPane = new JTabbedPane();
        this.add(tabbedPane);

        // Первая вкладка
        tabbedPane.add("Вход в систему", loginPasswordPanel);
        loginPasswordPanel.setSize(190, 200);
        loginPasswordPanel.setLayout(null);
        loginPasswordPanel.setBackground(null);

        // Указатель Введите логин
        JLabel enterLoginLabel = new JLabel("Введите логин");
        enterLoginLabel.setBounds(20, 10, 130, 20);

        // Поле Введите логин
        enterLoginText = new JTextField();
        enterLoginText.setBounds(160, 10, 170, 20);

        // Указатель Введите пароль
        JLabel enterPasswordLabel = new JLabel("Введите пароль");
        enterPasswordLabel.setBounds(20, 40, 130, 20);

        // Поле Введите пароль
        enterPasswordText = new JTextField();
        enterPasswordText.setBounds(160, 40, 170, 20);

        JButton enter = new JButton("Вход");
        enter.setBounds(150,90,80, 25);
        enter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEnterAdmin = false;
                String userName = enterLoginText.getText();
                String userPassword = enterPasswordText.getText();

                if (userName.equals("") || userPassword.equals("")) {
                    new Alert("Заполните все поля");
                } else {
                    // Запрос к БД
                    bdQuerry("SELECT * FROM managers WHERE login = '" + userName + "'", 2);

                    if (userName.equals(loginFromBD) && userPassword.equals(passFromBD)) {
                        // Принимаем Должность и ФИО
                        new MyFrame(postFromBD, fioFromBD, idFromBD);
                        TabLogin.this.dispose();
                    }
                    else {
                        new Alert("Вы ввели некорректный Логин или Пароль.");
                    }
                }
            }
        });

        loginPasswordPanel.add(enter);

        loginPasswordPanel.add(enterLoginLabel);
        loginPasswordPanel.add(enterLoginText); // Запуск поля Введите логин

        loginPasswordPanel.add(enterPasswordLabel);
        loginPasswordPanel.add(enterPasswordText); // Запуск поля Введите пароль

        // Вторая вкладка
        tabbedPane.add("Вход в админку", loginAdminPanel);
        loginAdminPanel.setSize(190, 200);
        loginAdminPanel.setLayout(null);
        loginAdminPanel.setBackground(null);

        // Указатель Введите логин
        JLabel enterLoginLabel2 = new JLabel("Введите логин");
        enterLoginLabel2.setBounds(20, 10, 130, 20);

        // Поле Введите логин
        enterLoginAdminText = new JTextField();
        enterLoginAdminText.setBounds(160, 10, 170, 20);

        // Указатель Введите пароль
        JLabel enterPasswordLabel2 = new JLabel("Введите пароль");
        enterPasswordLabel2.setBounds(20, 40, 130, 20);

        // Поле Введите пароль
        enterPasswordAdminText = new JTextField();
        enterPasswordAdminText.setBounds(160, 40, 170, 20);

        JButton enter2 = new JButton("Вход");
        enter2.setBounds(150,90,80, 25);
        enter2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isEnterAdmin = true;
                String userName2 = enterLoginAdminText.getText();
                String userPassword2 = enterPasswordAdminText.getText();

                if (userName2.equals("") || userPassword2.equals("")) {
                    new Alert("Заполните все поля");
                } else {
                    // Запрос к БД
                    bdQuerry("SELECT * FROM admins WHERE login = '" + userName2 + "'", 2);

                    if (userName2.equals(loginFromBD2) && userPassword2.equals(passFromBD2)) {
                        // Принимаем Должность и ФИО
                        new AdminFrame();
                        TabLogin.this.dispose();
                    }
                    else {
                        new Alert("Вы ввели некорректный Логин или Пароль.");
                    }
                }
            }
        });

        loginAdminPanel.add(enter2);

        loginAdminPanel.add(enterLoginLabel2);
        loginAdminPanel.add(enterLoginAdminText); // Запуск поля Введите логин

        loginAdminPanel.add(enterPasswordLabel2);
        loginAdminPanel.add(enterPasswordAdminText); // Запуск поля Введите пароль


        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Методы и функции
    @Override
    public int fillValue(ResultSet rs){

        try {
            while (rs.next()) {

                if (isEnterAdmin == false) {
                    // Логин
                    loginFromBD = rs.getString("login");

                    // Пароль
                    passFromBD = rs.getString("pass");

                    // Должность
                    postFromBD = rs.getString("post");

                    // ФИО
                    fioFromBD = rs.getString("fio");

                    // ID
                    idFromBD = rs.getInt("id");
                }
                if (isEnterAdmin == true) {
                    // Логин
                    loginFromBD2 = rs.getString("login");

                    // Пароль
                    passFromBD2 = rs.getString("pass");

                    // ID
                    idFromBD2 = rs.getInt("id");
                }

            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return  0;
    }
}
