import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProsmotrContact extends SuperFrame implements ActionListener {
        JPanel companyPanel = new JPanel();
        JPanel tablePanel = new JPanel();

        JTextField nameCompText;
        JTextField adressCompText;
        JTextField innCompText;
        JTextField phoneCompText;
        JTextField wwwCompText;
        JTextField emailCompText;
        JTextField directorCompText;
        JTextField directorPhoneCompText;
        JTextField contact1CompText;
        JTextField contact1PhoneCompText;
        JTextField contact2CompText;
        JTextField contact2PhoneCompText;

    // Стринговые переменные
    String nameCompany = ""; // Переменная для передачи вновый контакт
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

    String contactComment; // Стринг для передачи комментария

    int idManagerFromBD; // переменная для прием ID менеджера по запросу из БД

    // Переменные для чтения из БД данных компании
    String innCompany;
    String addressCompany;
    String phoneCompany;
    String siteCompany;
    String emailCompany;
    String directorCompany;
    String phoneDirectorCompany;
    String cont1Company;
    String phoneCont1Company;
    String cont2Company;
    String phoneCont2Company;


        JButton sozdanieContacta;
        JButton editData;
        JButton cancel;
        JButton printButton;
        JButton changeManagerButton;

        boolean editMode = false; // режим "заблокирован"

        JScrollPane scroll;

        // Глобально объявляем переменную
        GridBagConstraints constraints;

        Format formatter = new SimpleDateFormat("dd.MM.yyyy");

        //Конструктор:
        public ProsmotrContact(int compNumber, int compManager, String post, String fio) {
            this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // HIDE_ON_CLOSE
            this.setSize(1000, 800);
            this.setLayout(new BorderLayout(10, 10));
            this.idCompany = compNumber;
            this.idManager = compManager;
            this.post = post;
            this.fio = fio;
            this.setTitle("Просмотр компании");
            this.setIconImage(new ImageIcon("img/logo.jpg").getImage());


            viewCompany();
            createTable();

            this.add(companyPanel, BorderLayout.NORTH);
            this.add(scroll,BorderLayout.CENTER);

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
        private void viewCompany() {
            companyPanel.setBackground(Color.lightGray);
            companyPanel.setPreferredSize(new Dimension(800, 250));
            companyPanel.setLayout(null);

            // Указатель Название компании
            JLabel nameCompLabel = new JLabel("Название компании");
            nameCompLabel.setBounds(20, 10, 130, 20);

            // Поле Название компании
            nameCompText = new JTextField();
            nameCompText.setBounds(160, 10, 500, 20);
            nameCompText.setBackground(Color.lightGray);
            nameCompText.setFocusable(false);

            // Указатель Адрес компании
            JLabel adressCompLabel = new JLabel("Адрес компании");
            adressCompLabel.setBounds(20, 40, 130, 20);

            // Поле Адрес компании
            adressCompText = new JTextField();
            adressCompText.setFocusable(false);
            adressCompText.setBackground(Color.lightGray);
            adressCompText.setBounds(160, 40, 500, 20);

            // Указатель ИНН компании
            JLabel innCompLabel = new JLabel("ИНН компании");
            innCompLabel.setBounds(20, 70, 130, 20);

            // Поле ИНН компании
            innCompText = new JTextField();
            innCompText.setFocusable(false);
            innCompText.setBackground(Color.lightGray);
            innCompText.setBounds(160, 70, 175, 20);
            setNumber(innCompText);

            // Указатель Телефон компании
            JLabel phoneCompLabel = new JLabel("Телефон компании");
            phoneCompLabel.setBounds(345, 70, 130, 20);

            // Поле Телефон компании
            phoneCompText = new JTextField();
            phoneCompText.setFocusable(false);
            phoneCompText.setBackground(Color.lightGray);
            phoneCompText.setBounds(485, 70, 175, 20);

            // Указатель Сайт компании
            JLabel wwwCompLabel = new JLabel("Сайт компании");
            wwwCompLabel.setBounds(20, 100, 130, 20);

            // Поле Сайт компании
            wwwCompText = new JTextField();
            wwwCompText.setFocusable(false);
            wwwCompText.setBackground(Color.lightGray);
            wwwCompText.setBounds(160, 100, 175, 20);

            // Указатель Email компании
            JLabel emailCompLabel = new JLabel("Email компании");
            emailCompLabel.setBounds(345, 100, 130, 20);

            // Поле Email компании
            emailCompText = new JTextField();
            emailCompText.setFocusable(false);
            emailCompText.setBackground(Color.lightGray);
            emailCompText.setBounds(485, 100, 175, 20);

            // Указатель Генеральный директор
            JLabel directorCompLabel = new JLabel("Генеральный директор");
            directorCompLabel.setBounds(20, 130, 150, 20);

            // Поле Генеральный директор
            directorCompText = new JTextField();
            directorCompText.setFocusable(false);
            directorCompText.setBackground(Color.lightGray);
            directorCompText.setBounds(180, 130, 255, 20);

            // Указатель Телефон директор
            JLabel directorPhoneCompLabel = new JLabel("Телефон");
            directorPhoneCompLabel.setBounds(445, 130, 65, 20);

            // Поле Телефон директора
            directorPhoneCompText = new JTextField();
            directorPhoneCompText.setFocusable(false);
            directorPhoneCompText.setBackground(Color.lightGray);
            directorPhoneCompText.setBounds(520, 130, 140, 20);

            // Указатель Контактное лицо 1
            JLabel contact1CompLabel = new JLabel("Контактное лицо");
            contact1CompLabel.setBounds(20, 160, 150, 20);

            // Поле Контактное лицо 1
            contact1CompText = new JTextField();
            contact1CompText.setFocusable(false);
            contact1CompText.setBackground(Color.lightGray);
            contact1CompText.setBounds(180, 160, 255, 20);

            // Указатель Телефон лица 1
            JLabel contact1PhoneCompLabel = new JLabel("Телефон");
            contact1PhoneCompLabel.setBounds(445, 160, 65, 20);

            // Поле Телефон лица 1
            contact1PhoneCompText = new JTextField();
            contact1PhoneCompText.setFocusable(false);
            contact1PhoneCompText.setBackground(Color.lightGray);
            contact1PhoneCompText.setBounds(520, 160, 140, 20);

            // Указатель Контактное лицо 2
            JLabel contact2CompLabel = new JLabel("Контактное лицо");
            contact2CompLabel.setBounds(20, 190, 150, 20);

            // Поле Контактное лицо 2
            contact2CompText = new JTextField();
            contact2CompText.setFocusable(false);
            contact2CompText.setBackground(Color.lightGray);
            contact2CompText.setBounds(180, 190, 255, 20);

            // Указатель Телефон лица 2
            JLabel contact2PhoneCompLabel = new JLabel("Телефон");
            contact2PhoneCompLabel.setBounds(445, 190, 65, 20);

            // Поле Телефон лица 2
            contact2PhoneCompText = new JTextField();
            contact2PhoneCompText.setFocusable(false);
            contact2PhoneCompText.setBackground(Color.lightGray);
            contact2PhoneCompText.setBounds(520, 190, 140, 20);

            // Кнопки
            // Кнопка изменения данных editData
            editData = new JButton("Изменить данные");
            editData.setBounds(200,215,140, 25);
            editData.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if(ae.getSource() == editData && editMode == false){
                        editMode = true;
                        nameCompText.setFocusable(true);
                        nameCompText.setBackground(Color.white);
                        adressCompText.setFocusable(true);
                        adressCompText.setBackground(Color.white);
                        innCompText.setFocusable(true);
                        innCompText.setBackground(Color.white);
                        phoneCompText.setFocusable(true);
                        phoneCompText.setBackground(Color.white);
                        wwwCompText.setFocusable(true);
                        wwwCompText.setBackground(Color.white);
                        emailCompText.setFocusable(true);
                        emailCompText.setBackground(Color.white);
                        directorCompText.setFocusable(true);
                        directorCompText.setBackground(Color.white);
                        directorPhoneCompText.setFocusable(true);
                        directorPhoneCompText.setBackground(Color.white);
                        contact1CompText.setFocusable(true);
                        contact1CompText.setBackground(Color.white);
                        contact1PhoneCompText.setFocusable(true);
                        contact1PhoneCompText.setBackground(Color.white);
                        contact2CompText.setFocusable(true);
                        contact2CompText.setBackground(Color.white);
                        contact2PhoneCompText.setFocusable(true);
                        contact2PhoneCompText.setBackground(Color.white);
                        editData.setText("Сохранить");
                        sozdanieContacta.setVisible(false);
                    } else if
                        (ae.getSource() == editData && editMode == true){
                        editMode = false;
                        nameCompText.setFocusable(false);
                        nameCompText.setBackground(Color.lightGray);
                        adressCompText.setFocusable(false);
                        adressCompText.setBackground(Color.lightGray);
                        innCompText.setFocusable(false);
                        innCompText.setBackground(Color.lightGray);
                        phoneCompText.setFocusable(false);
                        phoneCompText.setBackground(Color.lightGray);
                        wwwCompText.setFocusable(false);
                        wwwCompText.setBackground(Color.lightGray);
                        emailCompText.setFocusable(false);
                        emailCompText.setBackground(Color.lightGray);
                        directorCompText.setFocusable(false);
                        directorCompText.setBackground(Color.lightGray);
                        directorPhoneCompText.setFocusable(false);
                        directorPhoneCompText.setBackground(Color.lightGray);
                        contact1CompText.setFocusable(false);
                        contact1CompText.setBackground(Color.lightGray);
                        contact1PhoneCompText.setFocusable(false);
                        contact1PhoneCompText.setBackground(Color.lightGray);
                        contact2CompText.setFocusable(false);
                        contact2CompText.setBackground(Color.lightGray);
                        contact2PhoneCompText.setFocusable(false);
                        contact2PhoneCompText.setBackground(Color.lightGray);
                        editData.setText("Изменить данные");
                        sozdanieContacta.setVisible(true);

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
                            bdQuerry ("UPDATE kompany SET name_comp= '" + companyName + "', inn = '" + companyINN + "'," +
                                    "address = '" + companyAdress + "', phone_comp = '" + companyPhone + "', site ='" + companyWWW + "'," +
                                    "`email`='" + companyEmail + "',`director`='" + companyDirector + "',`phone_director`=" +
                                    "'" + directorPhone + "', cont_1 = '" + companyConact1 + "', phone_cont_1 = '" + phoneContact1 + "', cont_2 = '" + companyConact2 + "" +
                                    "', phone_cont_2 = '" + phoneContact2 + "', id_manager = '" + idManager + "' WHERE id = " + idCompany, 3);


                            ProsmotrContact.this.dispose();
                            new ProsmotrContact(idCompany, idManager, post, fio);
                        }
                    }
                }
            });



            // Кнопка создания нового контакта
            sozdanieContacta = new JButton("Создать контакт");
            sozdanieContacta.setBounds(350,215,140, 25);
            sozdanieContacta.setVisible(true);

            sozdanieContacta.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new AddContact(nameCompany, idManager, idCompany, post, fio);
                    ProsmotrContact.this.dispose();
                }
            });

            // Кнопка "Отмена"
            cancel = new JButton("Отмена");
            cancel.setBounds(350,215,140, 25);;
            cancel.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    if (ae.getSource() == cancel && editMode == true) {
                        editMode = false;
                        nameCompText.setFocusable(false);
                        nameCompText.setBackground(Color.lightGray);
                        adressCompText.setFocusable(false);
                        adressCompText.setBackground(Color.lightGray);
                        innCompText.setFocusable(false);
                        innCompText.setBackground(Color.lightGray);
                        phoneCompText.setFocusable(false);
                        phoneCompText.setBackground(Color.lightGray);
                        wwwCompText.setFocusable(false);
                        wwwCompText.setBackground(Color.lightGray);
                        emailCompText.setFocusable(false);
                        emailCompText.setBackground(Color.lightGray);
                        directorCompText.setFocusable(false);
                        directorCompText.setBackground(Color.lightGray);
                        directorPhoneCompText.setFocusable(false);
                        directorPhoneCompText.setBackground(Color.lightGray);
                        contact1CompText.setFocusable(false);
                        contact1CompText.setBackground(Color.lightGray);
                        contact1PhoneCompText.setFocusable(false);
                        contact1PhoneCompText.setBackground(Color.lightGray);
                        contact2CompText.setFocusable(false);
                        contact2CompText.setBackground(Color.lightGray);
                        contact2PhoneCompText.setFocusable(false);
                        contact2PhoneCompText.setBackground(Color.lightGray);
                        editData.setText("Изменить данные");
                        sozdanieContacta.setVisible(true);

                        ProsmotrContact.this.dispose();
                        new ProsmotrContact(idCompany, idManager, post, fio);
                    }
                }
            });

            // Кнопка печати в файл
            printButton = new JButton("Печать в файл");
            printButton.setBounds(500,215,140, 25);
            printButton.setVisible(true);

            printButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    PrintFile();
                }
            });

            // Кнопка Назначить менеджера
            changeManagerButton = new JButton("Назначить менеджера");
            changeManagerButton.setBounds(20,215,170, 25);

            if (post.equals("Начальник")) {
                changeManagerButton.setVisible(true);
            } else {
                changeManagerButton.setVisible(false);
            }

            changeManagerButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    new ChangeManager(nameCompany, fio, idCompany);
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
            companyPanel.add(editData);
            companyPanel.add(cancel); // кнопка "отмена"
            companyPanel.add(printButton); // кнопка "Печать в файл"
            companyPanel.add(changeManagerButton); // кнопка "Назначить менеджера"

            // Запрос к базе данных
            int line = bdQuerry ("select * from kompany WHERE id = " + idCompany,
                    2);
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
        }

        public class TableLabel extends JLabel {
            public TableLabel(String text) {
                this.setText(text);
                this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                this.setBorder(new CompoundBorder( // задаёт 2 бордера
                        BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                        BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                this.setFont(new Font("", Font.PLAIN, 17));
            }
        }

        // создаём класс обработчика событий, реализующий интерфейс MouseListener
        public static class CommentViewCompany extends JLabel implements MouseListener {
            String commentText;
            public CommentViewCompany(String text) {
                this.setText(text);
                commentText = text;
                this.setBorder(new CompoundBorder( // задаёт 2 бордера
                        BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                        BorderFactory.createEmptyBorder(0, 5, 0, 0)));
                this.setFont(new Font("", Font.PLAIN, 17));
                this.addMouseListener(this);
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                new ViewCommentText(commentText);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }

        @Override
        public int fillValue(ResultSet rs){

            try {
                for (int i = 1; rs.next(); i++) {

                    // Название компании
                    nameCompany = rs.getString("name_comp");
                    nameCompText.setText(nameCompany);

                    // ИНН компании
                    innCompany = rs.getString("inn");
                    innCompText.setText(innCompany);

                    // Поле Адрес компании
                    addressCompany = rs.getString("address");
                    adressCompText.setText(addressCompany);

                    // Поле Телефон компании
                    phoneCompany = rs.getString("phone_comp");
                    phoneCompText.setText(phoneCompany);

                    // Поле Сайт компании
                    siteCompany = rs.getString("site");
                    wwwCompText.setText(siteCompany);

                    // Поле Email компании
                    emailCompany = rs.getString("email");
                    emailCompText.setText(emailCompany);

                    // Поле Генеральный директор компании
                    directorCompany = rs.getString("director");
                    directorCompText.setText(emailCompany);

                    // Поле Телефон директора
                    phoneDirectorCompany = rs.getString("phone_director");
                    directorPhoneCompText.setText(phoneDirectorCompany);

                    // Поле Контактное лицо 1
                    cont1Company = rs.getString("cont_1");
                    contact1CompText.setText(cont1Company);

                    // Поле Телефон контактного лица 1
                    phoneCont1Company = rs.getString("phone_cont_1");
                    contact1PhoneCompText.setText(phoneCont1Company);

                    // Поле Контактное лицо 2
                    cont2Company = rs.getString("cont_2");
                    contact2CompText.setText(cont2Company);

                    // Поле Телефон контактного лица 2
                    phoneCont2Company = rs.getString("phone_cont_2");
                    contact2PhoneCompText.setText(phoneCont2Company);

                    // В эту переменную записываем id енеджера из БД
                    idManagerFromBD = rs.getInt("id_manager");
                    if (idManagerFromBD != idManager) {
                        sozdanieContacta.setVisible(false);
                        editData.setVisible(false);
                        cancel.setVisible(false);
                    } else {
                        sozdanieContacta.setVisible(true);
                        editData.setVisible(true);
                        cancel.setVisible(true);
                    }
                }

            } catch (SQLException sqlEx) {
                sqlEx.printStackTrace();
            }

            return  0;
        }

        // создаем таблицу
        public void createTable() {
            tablePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
            tablePanel.setLayout(new java.awt.GridBagLayout());

            constraints = new GridBagConstraints();
            constraints.fill = GridBagConstraints.HORIZONTAL;

            constraints.gridy = 0; // нулевая строка по вертикали
            constraints.weightx = 0.5;

            constraints.gridx = 0; // нулевая ячейка по горизонтали
            tablePanel.add(new JButton("Дата"), constraints);

            constraints.gridx = 1;  // 1-ая ячейка по горизонтали
            tablePanel.add(new JButton("Тип контакта"), constraints);

            constraints.gridx = 2;  // 2-ая ячейка по горизонтали
            tablePanel.add(new JButton("Комментарий"), constraints);

            constraints.gridx = 3;  // 3-ая ячейка по горизонтали
            tablePanel.add(new JButton("Менеджер"), constraints);

            // Запрос к базе данных
            int line = bdQuerry("Select C.*, M.fio From contacts C Join managers M ON C.id_managers=M.id Where id_comp = " + idCompany + " ORDER BY date DESC", 1);

            // кнопка внизу таблицы
            constraints.gridy = line+1; // 2 строка по вертикали
            constraints.gridx = 3; // нулевая строка по горизонтали
            constraints.weighty = 1.0;  // установить отступ
            constraints.anchor = GridBagConstraints.PAGE_END;
            JButton endButton = new JButton("Всего строк "+line);
            //endButton.setVisible(false);

            tablePanel.add(endButton, constraints);

            scroll = new JScrollPane(tablePanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        }

    @Override
    public int fillTable(ResultSet rs){
        int lines = 0;

        try {
            for (int i = 1; rs.next(); i++) {
                constraints.gridy = i; // добавляем строку по вертикали

                // Дата контакта из таблицы contacts
                constraints.gridx = 0;
                Date dateContact;
                dateContact = rs.getDate("date");
                String s = formatter.format(dateContact);
                tablePanel.add(new TableLabel(s), constraints);

                // Тип контакта из таблицы contacts
                constraints.gridx = 1;
                String contact = rs.getString("type_cont");
                tablePanel.add(new TableLabel(contact), constraints);

                constraints.gridx = 2;

                contactComment = rs.getString("comment");
                if (contactComment.equals("")) {
                    contactComment = "-";
                }
                tablePanel.add(new CommentViewCompany(contactComment),constraints);

                constraints.gridx = 3;
                String contactManager = rs.getString("fio");
                tablePanel.add(new TableLabel(contactManager), constraints);

                lines = i;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return  lines;
    }

    // Функция печати в файл
    public void PrintFile(){
        PrintWriter pw;
        File file = new File("Компания_" + idCompany + ".txt");
        try {
            pw = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
         pw.println("Название компании: " + nameCompany);
         pw.println("Адрес компании: " + addressCompany);
         pw.println("ИНН: " + innCompany);
         pw.println("Телефон компании: " + phoneCompany);
         pw.println("Сайт компании: " + siteCompany);
         pw.println("Email компании: " + emailCompany);
         pw.println("Генеральный директор: " + directorCompany);
         pw.println("Телефон директора: " + phoneDirectorCompany);
         pw.println("Контактное лицо 1: " + cont1Company);
         pw.println("Телефон контактного лица 1: " + phoneCont1Company);
        pw.println("Контактное лицо 2: " + cont2Company);
        pw.println("Телефон контактного лица 2: " + phoneCont2Company);

        pw.close();

    }
}