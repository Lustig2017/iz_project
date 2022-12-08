import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddUser extends SuperFrame implements ActionListener  {
    // Переменные
    JPanel managerPanel = new JPanel();
    JButton addManager;
    JButton cancelAddManager;
    JTextField familiyaText; // Текстовое поле фамилии
    JTextField imyaText; // Текстовое поле имя
    JTextField otchectvoText; // Текстовое поле отчество
    JTextField loginText; // Текстовое поле логин
    JTextField passwordText; // Текстовое поле логин
    String postFromComboBox;

    JComboBox comboboxPost;

    // Начало конструктора
    AddUser() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(400,320);
        this.setLayout(new BorderLayout(10,10));
        this.setResizable(false);
        this.setTitle("Создание аккаунта");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        createAccount();

        this.add(managerPanel,BorderLayout.NORTH);

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
    // Конец конструктора

    // Создаем аккаунт
    public void createAccount() {
        managerPanel.setBackground(Color.lightGray);
        managerPanel.setPreferredSize(new Dimension(400, 320));
        managerPanel.setLayout(null);

        // Первая строка
        JLabel familyaLabel = new JLabel("Фамилия");
        familyaLabel.setBounds(50,10,70, 20);

        // текстовое поле первой стоки
        familiyaText = new JTextField();
        familiyaText.setBounds(130,10,180, 20);
        familiyaText.setFocusable(true);

        // Вторая строка
        JLabel imyaLabel = new JLabel("Имя");
        imyaLabel.setBounds(50,40,70, 20);

        // текстовое поле второй стоки
        imyaText = new JTextField();
        imyaText.setBounds(130,40,180, 20);
        imyaText.setFocusable(true);

        // Третья строка
        JLabel otchectvoLabel = new JLabel("Отчество");
        otchectvoLabel.setBounds(50,70,70, 20);

        // текстовое поле третьей стоки
        otchectvoText = new JTextField();
        otchectvoText.setBounds(130,70,180, 20);
        otchectvoText.setFocusable(true);

        // Четвертая строка
        JLabel loginLabel = new JLabel("Логин");
        loginLabel.setBounds(50,100,70, 20);

        // текстовое поле четвертой стоки
        loginText = new JTextField();
        loginText.setBounds(130,100,180, 20);
        loginText.setFocusable(true);

        // Пятая строка
        JLabel passwordLabel = new JLabel("Пароль");
        passwordLabel.setBounds(50,130,70, 20);

        // текстовое поле пятой стоки
        passwordText = new JTextField();
        passwordText.setBounds(130,130,180, 20);
        passwordText.setFocusable(true);

        // Шестая строка
        JLabel postLabel = new JLabel("Должность");
        postLabel.setBounds(50,160,70, 20);

        // текстовое поле шестой стоки
        String[] types = {"Менеджер", "Начальник"};
        comboboxPost = new JComboBox(types);
        comboboxPost.setBounds(130,160,180, 20);
        comboboxPost.setSelectedIndex(0);

        // Кнопки
        // Создать
        addManager = new JButton("Создать");
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
                AddUser.this.dispose();
            }
        });

        // Объявляем Labels
        managerPanel.add(familyaLabel); // Поле Фамилия
        managerPanel.add(familiyaText); // Текст Фамилия

        managerPanel.add(imyaLabel); // Поле Имя
        managerPanel.add(imyaText); // Текст Имя

        managerPanel.add(otchectvoLabel); // Поле Отчество
        managerPanel.add(otchectvoText); // Текст Отчество

        managerPanel.add(loginLabel); // Поле Логин
        managerPanel.add(loginText); // Текст Логин

        managerPanel.add(passwordLabel); // Поле Пароль
        managerPanel.add(passwordText); // Текст Пароль

        managerPanel.add(postLabel); // Поле Должность
        managerPanel.add(comboboxPost); // Текст Должность

        managerPanel.add(addManager); // Кнопка Создать
        managerPanel.add(cancelAddManager); // Кнопка Отменить
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== addManager){
        }
        if(e.getSource()== cancelAddManager){
        }
    }

    // Метод отправки данных в базу данных
    public void setAccount() {
        String familiya = familiyaText.getText();
        String imya = imyaText.getText();
        String otchectvo = otchectvoText.getText();
        String login = loginText.getText();
        String password = passwordText.getText();
        postFromComboBox = comboboxPost.getSelectedItem().toString();

        if (familiya.equals("") || imya.equals("") || otchectvo.equals("") || login.equals("") || password.equals("") || postFromComboBox.equals("")) {
            new Alert("Заполните все поля");
        } else {
            bdQuerry("INSERT INTO managers (fio, post, login, pass, isActive) VALUES ('" + familiya + " " + imya + " " + otchectvo + "', '" + postFromComboBox + "', '" + login + "', '" + password + "', '1')", 3);
            new AdminFrame();
            AddUser.this.dispose();
        }
    }
}
