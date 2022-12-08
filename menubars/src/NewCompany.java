import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewCompany  extends SuperFrame implements ActionListener {

    JPanel companyPanel = new JPanel();

    JButton sozdanieContacta;
    JButton clearContact;

    JScrollPane scroll;

    // Переменные карточки компании
    JTextField nameCompText; // Название компании
    JTextField adressCompText; // Адресс компании
    JTextField innCompText; // ИНН компании
    JTextField phoneCompText; // Телефон компании
    JTextField wwwCompText; // Сайт компании
    JTextField emailCompText; // Email компании
    JTextField directorCompText; // Генеральный директор
    JTextField directorPhoneCompText; // Телефон директор компании
    JTextField contact1CompText; // Контактное лицо 1
    JTextField contact1PhoneCompText; // Телефон Контактное лицо 1
    JTextField contact2CompText; // Контактное лицо 2
    JTextField contact2PhoneCompText; // Телефон Контактное лицо 2

    // Стринговые переменные
    String companyName = ""; // Имя компании
    String companyAdress = ""; // Адрес компании
    String companyINN = ""; // ИНН компании
    String companyPhone = ""; // Телефон компании
    String companyWWW = ""; // Сайт компании
    String companyEmail = ""; // Email компании
    String companyDirector = ""; // Генеральный директор
    String directorPhone = ""; // Телефон директор компании
    String companyConact1 = ""; // Контактное лицо 1
    String phoneContact1 = ""; // Телефон Контактное лицо 1
    String companyConact2 = ""; // Контактное лицо 2
    String phoneContact2 = ""; // Телефон Контактное лицо 2

    public NewCompany(String postMan, String fioMan, int idMan) {
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // HIDE_ON_CLOSE
        this.setSize(700, 320);
        this.setLayout(new BorderLayout(10, 10));
        this.setResizable(false);
        this.setTitle("Создать компанию");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        this.post = postMan;
        this.fio = fioMan;
        this.idManager = idMan;

        createCompany();

        this.add(companyPanel, BorderLayout.NORTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // обработка кнопки закрыть Х
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new MyFrame(post, fio, idManager);
            }
        });
    }

    // создаем карточку компании
    private void createCompany() {
        companyPanel.setBackground(Color.lightGray);
        companyPanel.setPreferredSize(new Dimension(700, 320));
        companyPanel.setLayout(null);

        // Указатель Название компании
        JLabel nameCompLabel = new JLabel("Название компании*");
        nameCompLabel.setBounds(20, 10, 130, 20);

        // Поле Название компании
        nameCompText = new JTextField();
        nameCompText.setBounds(160, 10, 500, 20);

        // Указатель Адрес компании
        JLabel adressCompLabel = new JLabel("Адрес компании");
        adressCompLabel.setBounds(20, 40, 130, 20);

        // Поле Адрес компании
        adressCompText = new JTextField();
        adressCompText.setBounds(160, 40, 500, 20);

        // Указатель ИНН компании
        JLabel innCompLabel = new JLabel("ИНН компании");
        innCompLabel.setBounds(20, 70, 130, 20);

        // Поле ИНН компании
        innCompText = new JTextField();
        innCompText.setBounds(160, 70, 175, 20);
        setNumber(innCompText);

        // Указатель Телефон компании
        JLabel phoneCompLabel = new JLabel("Телефон компании");
        phoneCompLabel.setBounds(345, 70, 130, 20);

        // Поле Телефон компании
        phoneCompText = new JTextField();
        phoneCompText.setBounds(485, 70, 175, 20);

        // Указатель Сайт компании
        JLabel wwwCompLabel = new JLabel("Сайт компании");
        wwwCompLabel.setBounds(20, 100, 130, 20);

        // Поле Сайт компании
        wwwCompText = new JTextField();
        wwwCompText.setBounds(160, 100, 175, 20);

        // Указатель Email компании
        JLabel emailCompLabel = new JLabel("Email компании");
        emailCompLabel.setBounds(345, 100, 130, 20);

        // Поле Email компании
        emailCompText = new JTextField();
        emailCompText.setBounds(485, 100, 175, 20);

        // Указатель Генеральный директор
        JLabel directorCompLabel = new JLabel("Генеральный директор");
        directorCompLabel.setBounds(20, 130, 150, 20);

        // Поле Генеральный директор
        directorCompText = new JTextField();
        directorCompText.setBounds(180, 130, 255, 20);

        // Указатель Телефон директор
        JLabel directorPhoneCompLabel = new JLabel("Телефон");
        directorPhoneCompLabel.setBounds(445, 130, 65, 20);

        // Поле Телефон директора
        directorPhoneCompText = new JTextField();
        directorPhoneCompText.setBounds(520, 130, 140, 20);

        // Указатель Контактное лицо 1
        JLabel contact1CompLabel = new JLabel("Контактное лицо");
        contact1CompLabel.setBounds(20, 160, 150, 20);

        // Поле Контактное лицо 1
        contact1CompText = new JTextField();
        contact1CompText.setBounds(180, 160, 255, 20);

        // Указатель Телефон лица 1
        JLabel contact1PhoneCompLabel = new JLabel("Телефон");
        contact1PhoneCompLabel.setBounds(445, 160, 65, 20);

        // Поле Телефон лица 1
        contact1PhoneCompText = new JTextField();
        contact1PhoneCompText.setBounds(520, 160, 140, 20);

        // Указатель Контактное лицо 2
        JLabel contact2CompLabel = new JLabel("Контактное лицо");
        contact2CompLabel.setBounds(20, 190, 150, 20);

        // Поле Контактное лицо 2
        contact2CompText = new JTextField();
        contact2CompText.setBounds(180, 190, 255, 20);

        // Указатель Телефон лица 2
        JLabel contact2PhoneCompLabel = new JLabel("Телефон");
        contact2PhoneCompLabel.setBounds(445, 190, 65, 20);

        // Поле Телефон лица 2
        contact2PhoneCompText = new JTextField();
        contact2PhoneCompText.setBounds(520, 190, 140, 20);

        // Кнопки

        sozdanieContacta = new JButton("Создать");
        sozdanieContacta.setBounds(200,230,160, 25);
        sozdanieContacta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                companyName = nameCompText.getText(); // Имя компании
                companyAdress = adressCompText.getText(); // Адрес компании
                companyINN = innCompText.getText(); // ИНН компании
                companyPhone = phoneCompText.getText(); // Телефон компании
                companyWWW = wwwCompText.getText(); // Сайт компании
                companyEmail = emailCompText.getText(); // Email компании
                companyDirector = directorCompText.getText(); // Генеральный директор
                directorPhone = directorPhoneCompText.getText(); // Телефон директор компании
                companyConact1 = contact1CompText.getText(); // Контактное лицо 1
                phoneContact1 = contact1PhoneCompText.getText(); // Телефон Контактное лицо 1
                companyConact2 = contact2CompText.getText(); // Контактное лицо 2
                phoneContact2 = contact2PhoneCompText.getText(); // Телефон Контактное лицо 2

                // Проверка заполнения поля "Название компании"
                if (companyName.equals("")) {
                    new Alert("Название компании обязательно к заполнению!");
                } else {

                    // Запрос к базе данных
                   bdQuerry ( "INSERT INTO `kompany`(name_comp, inn, address, phone_comp, site, " +
                           "email, director, phone_director, cont_1, phone_cont_1, cont_2, phone_cont_2, " +
                           "id_manager) VALUES ('" + companyName + "','" + companyINN + "','" + companyAdress + "','" +
                           companyPhone + "', '" + companyWWW + "','" + companyEmail + "', '" + companyDirector + "','" +
                           directorPhone + "'," + "'" + companyConact1 + "','" + phoneContact1 + "','" + companyConact2 + "','" +
                           phoneContact2 + "'," + "'" + idManager + "')", 3);

                    NewCompany.this.dispose();
                    new MyFrame(post, fio, idManager);
                }
            }
        });

        clearContact = new JButton("Отмена");
        clearContact.setBounds(370,230,160, 25);;
        clearContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                NewCompany.this.dispose();
                new MyFrame(post, fio, idManager);
            }
        });

        companyPanel.add(nameCompLabel);
        companyPanel.add(nameCompText); // Запуск поля Название компании

        companyPanel.add(adressCompLabel);
        companyPanel.add(adressCompText); // Запуск поля Адрес компании

        companyPanel.add(innCompLabel);
        companyPanel.add(innCompText); // Запуск поля ИНН компании

        companyPanel.add(phoneCompLabel);
        companyPanel.add(phoneCompText); // Запуск поля Телефон компании

        companyPanel.add(wwwCompLabel);
        companyPanel.add(wwwCompText); // Запуск поля Сайт компании

        companyPanel.add(emailCompLabel);
        companyPanel.add(emailCompText); // Запуск поля Email компании

        companyPanel.add(directorCompLabel);
        companyPanel.add(directorCompText); // Запуск поля Генеральный директор

        companyPanel.add(directorPhoneCompLabel);
        companyPanel.add(directorPhoneCompText); // Запуск поля Телефон директора

        companyPanel.add(contact1CompLabel);
        companyPanel.add(contact1CompText); // Запуск поля Контактное лицо 1

        companyPanel.add(contact1PhoneCompLabel);
        companyPanel.add(contact1PhoneCompText); // Запуск поля Телефон лица 1

        companyPanel.add(contact2CompLabel);
        companyPanel.add(contact2CompText); // Запуск поля Контактное лицо 2

        companyPanel.add(contact2PhoneCompLabel);
        companyPanel.add(contact2PhoneCompText); // Запуск поля Телефон лица 2

        companyPanel.add(sozdanieContacta);
        companyPanel.add(clearContact);
    }

    // Обрабатываем нажатие кнопки закрыть окно Х
    public  void setNumber(JTextField field){
        ((AbstractDocument)field.getDocument()).setDocumentFilter(new DocumentFilter(){
            Pattern regEx = Pattern.compile("\\d*");

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                Matcher matcher = regEx.matcher(text);
                if(!matcher.matches()){
                    return;
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== sozdanieContacta){
        }
        if(e.getSource()== clearContact){
        }
    }

    public class TableLabel extends JLabel {
        public TableLabel(String text) {
            this.setText(text);
            this.setBorder(new CompoundBorder( // задаёт 2 бордера
                    BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                    BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            this.setFont(new Font("", Font.PLAIN, 17));
        }
    }

    public class TableLabel2 extends JLabel {
        public TableLabel2(String text) {
            this.setText(text);
            this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            this.setBorder(new CompoundBorder( // задаёт 2 бордера
                    BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                    BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            this.setFont(new Font("", Font.PLAIN, 17));
        }
    }

        // создаем таблицу
        public void createTable() {

            GridBagConstraints constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.insets = new Insets(1,0,1,0); // отступы между ячейками

            constraints.gridy = 0; // нулевая строка по вертикали
            constraints.weightx = 0.5;

            constraints.gridx = 0; // нулевая ячейка по горизонтали

            constraints.gridx = 1;  // 1-ая ячейка по горизонтали

            constraints.gridx = 2;  // 2-ая ячейка по горизонтали

            constraints.gridy = 1; // 1 строка по вертикали

            constraints.gridx = 0;

            constraints.gridx = 1;

            constraints.gridx = 2;

            //

            for (int i = 2; i < 5; i++) {
                constraints.gridy = i; // добавляем строку по вертикали

                constraints.gridx = 0;

                constraints.gridx = 1;

                constraints.gridx = 2;
            }

            // кнопка внизу таблицы
            constraints.gridy = 5; // 2 строка по вертикали
            constraints.gridx = 2; // нулевая строка по горизонтали
            constraints.weighty = 1.0;  // установить отступ
            constraints.anchor = GridBagConstraints.PAGE_END;
            JButton endButton = new JButton("Выход из программы");
            endButton.setVisible(false);
        }
}


