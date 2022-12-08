import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EditUser extends SuperFrame implements ActionListener {
    // Переменные
    JPanel editPanel = new JPanel();
    JButton addManager;
    JButton cancelAddManager;
    JTextField fioText; // Текстовое поле фамилии
    JTextField loginText; // Текстовое поле логин
    JTextField passwordText; // Текстовое поле логин

    String postFromComboBox;
    JComboBox comboboxPost;
    JComboBox comboboxStatus; // Статус

    Boolean haveUserCompany;

    // Конструктор
    EditUser(int idFromBD){
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,320);
        this.setLayout(new BorderLayout(10,10));
        this.setResizable(false);
        this.idManager = idFromBD;
        this.setTitle("Редактирование пользователя");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        editAccount();

        this.add(editPanel,BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // обработка кнопки закрыть Х
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new AdminFrame();
            }
        });
    }

    // Методы и функции
    // Создаем область редактирования
    public void editAccount(){
        editPanel.setBackground(Color.lightGray);
        editPanel.setPreferredSize(new Dimension(400, 320));
        editPanel.setLayout(null);

        // Первая строка
        JLabel fioyaLabel = new JLabel("ФИО");
        fioyaLabel.setBounds(50,10,70, 20);

        // текстовое поле первой стоки
        fioText = new JTextField();
        fioText.setBounds(130,10,180, 20);
        fioText.setFocusable(true);

        // Вторая строка
        JLabel loginLabel = new JLabel("Логин");
        loginLabel.setBounds(50,40,70, 20);

        // текстовое поле второй стоки
        loginText = new JTextField();
        loginText.setBounds(130,40,180, 20);
        loginText.setFocusable(true);

        // Третья строка
        JLabel passwordLabel = new JLabel("Пароль");
        passwordLabel.setBounds(50,70,70, 20);

        // текстовое поле третей стоки
        passwordText = new JTextField();
        passwordText.setBounds(130,70,180, 20);
        passwordText.setFocusable(true);

        // Четвертая строка
        JLabel postLabel = new JLabel("Должность");
        postLabel.setBounds(50,100,70, 20);

        // текстовое поле четвертой стоки
        String[] types = {"Менеджер", "Начальник"};
        comboboxPost = new JComboBox(types);
        comboboxPost.setBounds(130,100,180, 20);

        // Пятая строка
        JLabel statusLabel = new JLabel("Статус");
        statusLabel.setBounds(50,130,70, 20);

        // текстовое поле пятой стоки
        String[] status = {"Активен", "Заблокирован"};
        comboboxStatus = new JComboBox(status);
        comboboxStatus.setBounds(130,130,180, 20);


        // Кнопки
        // Редактировать
        addManager = new JButton("Сохранить");
        addManager.setBounds(30,210,160, 25);
        addManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                setAccount();
            }
        });

        // Отмена
        cancelAddManager = new JButton("Отмена");
        cancelAddManager.setBounds(200,210,160, 25);;
        cancelAddManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                new AdminFrame();
                EditUser.this.dispose();
            }
        });

        // Объявляем Labels
        editPanel.add(fioyaLabel); // Поле ФИО
        editPanel.add(fioText); // Текст ФИО

        editPanel.add(loginLabel); // Поле Логин
        editPanel.add(loginText); // Текст Логин

        editPanel.add(passwordLabel); // Поле Пароль
        editPanel.add(passwordText); // Текст Пароль

        editPanel.add(postLabel); // Поле Должность
        editPanel.add(comboboxPost); // Текст Должность

        editPanel.add(statusLabel); // Поле Должность
        editPanel.add(comboboxStatus); // Текст Должность

        editPanel.add(addManager); // Кнопка Создать
        editPanel.add(cancelAddManager); // Кнопка Отменить

        // Запрос к базе данных
        int line = bdQuerry ("select * from managers WHERE id = " + idManager,
                2);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== addManager){
        }
        if(e.getSource()== cancelAddManager){
        }
    }

    // Заполняем окно редактирования из БД
    @Override
    public int fillValue(ResultSet rs){

        try {
            for (int i = 1; rs.next(); i++) {

                // ФИО
                String fioAccount = rs.getString("fio");
                fioText.setText(fioAccount); // в параметры конструктора NameAccaunt() должны передаваться параметры

                // Логин
                String loginAccount = rs.getString("login");
                loginText.setText(loginAccount);

                // Пароль
                String passwordAccount = rs.getString("pass");
                passwordText.setText(passwordAccount);

                // Должность
                String postAccount = rs.getString("post");
                int numberPost = 2; // Объявляется целочисленная переменная для преобразования должности в цифру
                if (postAccount.equals("Менеджер")) {
                    numberPost = 0;
                } else if (postAccount.equals("Начальник")) {
                    numberPost = 1;
                }
                comboboxPost.setSelectedIndex(numberPost); // Должность в виде цифрового обозначения

                // Статус
                String statusAccount = rs.getString("isActive");
                int numberStatus = 2; // Объявляется целочисленная переменная для преобразования должности в цифру
                if (statusAccount.equals("0")) {
                    numberStatus = 1;
                } else if (statusAccount.equals("1")) {
                    numberStatus = 0;
                }
                comboboxStatus.setSelectedIndex(numberStatus); // Статус в виде цифрового обозначения
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return  0;
    }

    // Метод отправки данных в базу данных
    public void setAccount() {
        String fio = fioText.getText();
        postFromComboBox = comboboxPost.getSelectedItem().toString();
        String login = loginText.getText();
        String password = passwordText.getText();

        // Статус
        String statusAccountForBD = comboboxStatus.getSelectedItem().toString();
        int numberStatus = 2;
        if (statusAccountForBD.equals("Заблокирован")) {
            numberStatus = 0;
        } else if (statusAccountForBD.equals("Активен")) {
            numberStatus = 1;
        }

        if (fioText.equals("") || login.equals("") || password.equals("") || postFromComboBox.equals("") || statusAccountForBD.equals("")) {
            new Alert("Заполните все поля");
            return;
        }

        if(statusAccountForBD.equals("Заблокирован")){
            bdQuerry("SELECT id FROM kompany WHERE id_manager = " + idManager, 1);
            if (haveUserCompany == true){
                new TransferCompany(idManager);
                EditUser.this.dispose();
            } else {
                bdQuerry("UPDATE managers SET fio='" + fio + "', post='" + postFromComboBox + "', login='" + login + "', pass='" + password + "', isActive='" + numberStatus + "' WHERE id = " + idManager, 3);
                new AdminFrame();
                EditUser.this.dispose();
            }
        } else {
            bdQuerry("UPDATE managers SET fio='" + fio + "', post='" + postFromComboBox + "', login='" + login + "', pass='" + password + "', isActive='" + numberStatus + "' WHERE id = " + idManager, 3);
            new AdminFrame();
            EditUser.this.dispose();
        }
    }

    @Override
    public int fillTable(ResultSet rs){
        try {
            if (!rs.next()) {
                haveUserCompany = false;
            }
            else {
                haveUserCompany = true;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return  0;
    }
}
