import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MyFrame extends SuperFrame implements ActionListener {

    JMenuBar menuBar;
    JMenu createMenu;
    JMenu editMenu;
    JMenu helpMenu;
    JMenuItem createCompany;
    JMenuItem aboutInfo;

    JPanel filterPanel = new JPanel();
    JPanel tablePanel = new JPanel();

    JScrollPane scroll;

    // Глобально объявляем переменную
    GridBagConstraints constraints;

    // Переменные для фильтра Поиск
    String companyName;
    String companyINN;
    String companyPhone;

    Format formatter = new SimpleDateFormat("dd.MM.yyyy");

    JComboBox comboboxManagers;
    JComboBox comboboxTypes;
    LocalDate dateFormat;
    LocalDate dateFormat2;
    String dateFor;
    String DateToo;
    String typesContactFromComboBox;
    String querry;

    int idFromBD; // переменная для передачи ID менеджера в Просмотр контакта


    // Создаём конструктор
    MyFrame(String post, String fio, int idMan){

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,800);
        this.setLayout(new BorderLayout(10,10));
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());


        this.post = post;
        this.fio = fio;
        this.idManager = idMan;
        this.setTitle("CRM-система");

        createMenuBar(); // Создаем меню
        createFilter(); // Создаем фильтр
        createTable("select K.ID, name_comp, inn, phone_comp, type_cont, max(date), fio\n" +
                "from kompany K\n" +
                "LEFT JOIN contacts C\n" +
                "ON K.id = C.id_comp\n" +
                "JOIN managers M\n" +
                "ON K.id_manager = M.id\n" +
                "WHERE M.id = " + idManager + "\n" +
                "GROUP BY K.id\n" +
                "ORDER BY date DESC"); // Создаем таблицу


        this.add(filterPanel,BorderLayout.NORTH);
        this.add(scroll,BorderLayout.CENTER);

        // Добавить меню-бар на экран фрейма
        this.setJMenuBar(menuBar);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Создаем фильтр
    public void createFilter() {
        filterPanel.setBackground(Color.lightGray);
        filterPanel.setPreferredSize(new Dimension(100, 100));
        filterPanel.setLayout(null);

        // Лейблы слева
        JLabel nameCompLabel = new JLabel("Название компании");
        nameCompLabel.setBounds(20,10,130, 20);

        JLabel innLabel = new JLabel("ИНН компании");
        innLabel.setBounds(20,40,130, 20);

        JLabel phoneLabel = new JLabel("Телефон компании");
        phoneLabel.setBounds(20,70,130, 20);

        // текстовые поля слева
        JTextField nameCompText = new JTextField();
        nameCompText.setBounds(150,10,150, 20);

        JTextField innText = new JTextField();
        innText.setBounds(150,40,150, 20);

        JTextField phoneText = new JTextField();
        phoneText.setBounds(150,70,150, 20);

        // лейблы справа
        JLabel dateFromLable = new JLabel("Дата с");
        dateFromLable.setBounds(350,10,50, 20);

        JTextField dateFromText = new JTextField();
        dateFromText.setBounds(420,10,70, 20);

        JButton dateFromButton = new JButton("popup");
        dateFromButton.setBounds(490,10,20, 20);
        dateFromButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateFromText.setText(new DatePicker(filterPanel).setPickedDate());
            }
        });

        JLabel dateToLable = new JLabel("Дата по");
        dateToLable.setBounds(530,10,50, 20);

        JTextField dateToText = new JTextField();
        dateToText.setBounds(600,10,70, 20);

        JButton dateToButton = new JButton("popup");
        dateToButton.setBounds(670,10,20, 20);
        dateToButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateToText.setText(new DatePicker(filterPanel).setPickedDate());
            }
        });

        JLabel nameManagerLable = new JLabel("Менеджер");
        nameManagerLable.setBounds(350,40,80, 20);

        JLabel contactTypeLable = new JLabel("Тип контакта");
        contactTypeLable.setBounds(700,10,80, 20);

        // текстовые поля справа

        //String[] managers = {"Иванов", "Петров", "Сидоров"};
        String[] managers = getManagerFromBD();
        comboboxManagers = new JComboBox(managers);
        comboboxManagers.setBounds(420,40,270, 20);
        comboboxManagers.setSelectedIndex(indexIdManager);

        String[] types = {"Звонок", "Встреча", "Сделка"};
        comboboxTypes = new JComboBox(types);
        comboboxTypes.setBounds(790,10,80, 20);
        comboboxTypes.setSelectedIndex(-1);

        JButton search = new JButton("Поиск");
        search.setBounds(530,70,160, 25);
        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                querry = "select K.ID, name_comp, inn, phone_comp, type_cont, max(date), fio from kompany K LEFT JOIN contacts C ON K.id = C.id_comp JOIN managers M ON K.id_manager = M.id ";

                // Проверка менеджера
                if (comboboxManagers.getSelectedItem().toString() != null){
                    String fioManager = comboboxManagers.getSelectedItem().toString();
                    querry = querry + " WHERE fio LIKE '%" + fioManager + "%' ";
                } else {
                    querry = querry + "WHERE M.id = " + idManager;
                }

                // Проверка названия
                companyName = nameCompText.getText(); // Имя компании
                if (!companyName.equals("")) {
                    querry = querry + " AND name_comp LIKE '%" + companyName + "%' ";
                }

                // Проверка ИНН
                companyINN = innText.getText(); // ИНН компании
                if (!companyINN.equals("")){
                    querry = querry + "AND inn LIKE '%" + companyINN + "%' ";
                }

                // Проверка телефона
                companyPhone = phoneText.getText(); // Телефон компании
                if (!companyPhone.equals("")){
                    querry = querry + "AND phone_comp LIKE '%" + companyPhone + "%' ";
                }

                // Переменные для проверки даты
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                dateFor = dateFromText.getText();
                DateToo = dateToText.getText();

                // Проверка даты с и по
                if (!dateFor.equals("") && !DateToo.equals("")){
                    dateFormat = LocalDate.parse(dateFor, formatter);
                    dateFormat2 = LocalDate.parse(DateToo, formatter);
                    querry = querry + "AND date BETWEEN '" + dateFormat + "' AND '" + dateFormat2 + "' ";
                }

                // Проверка даты по
                if (dateFor.equals("") && !DateToo.equals("")){
                    dateFormat2 = LocalDate.parse(DateToo, formatter);
                    querry = querry + "AND date <= '" + dateFormat2 + "' ";
                }

                // Проверка даты с
                if (!dateFor.equals("") && DateToo.equals("")){
                    dateFormat = LocalDate.parse(dateFor, formatter);
                    querry = querry + "AND date >= '" + dateFormat + "' ";
                }

                // Проверка типа контакта
                try {
                    if (comboboxTypes.getSelectedItem().toString() != null) {
                        typesContactFromComboBox = comboboxTypes.getSelectedItem().toString();
                        querry = querry + "AND type_cont LIKE '%" + typesContactFromComboBox + "%' ";
                    }
                } catch (NullPointerException e){

                }

                querry = querry + "GROUP BY K.id ORDER BY date DESC";

                updateTable(querry);
            }
        });

        JButton clearFilter = new JButton("Сбросить");
        clearFilter.setBounds(350,70,160, 25);;
        clearFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                nameCompText.setText("");
                innText.setText("");
                phoneText.setText("");
                dateFromText.setText("");
                dateToText.setText("");
                comboboxTypes.setSelectedIndex(-1);
                updateTable("select K.ID, name_comp, inn, phone_comp, type_cont, max(date), fio\n" +
                        "from kompany K\n" +
                        "LEFT JOIN contacts C\n" +
                        "ON K.id = C.id_comp\n" +
                        "JOIN managers M\n" +
                        "ON K.id_manager = M.id\n" +
                        "WHERE M.id = " + idManager + "\n" +
                        "GROUP BY K.id\n" +
                        "ORDER BY date DESC");
            }
        });

        filterPanel.add(nameCompLabel);
        filterPanel.add(innLabel);
        filterPanel.add(phoneLabel);

        filterPanel.add(nameCompText);
        filterPanel.add(innText);
        filterPanel.add(phoneText);

        filterPanel.add(dateToLable);
        filterPanel.add(dateFromLable);
        filterPanel.add(nameManagerLable);
        filterPanel.add(contactTypeLable);

        filterPanel.add(comboboxManagers);
        filterPanel.add(comboboxTypes);

        filterPanel.add(dateFromText);
        filterPanel.add(dateFromButton);
        filterPanel.add(dateToText);
        filterPanel.add(dateToButton);

        filterPanel.add(search);
        filterPanel.add(clearFilter);
    }


    // Слушатели - обработчики для меню
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== createCompany){
           new NewCompany(post, fio, idManager);
            MyFrame.this.dispose();
        }

        if(e.getSource()== aboutInfo){
            new Help();
        }
    }

    // Создание меню
    public void createMenuBar () {
        // Инициализация переменных
        menuBar = new JMenuBar();

        createMenu = new JMenu("Создать");

        helpMenu = new JMenu("Помощь");

        createCompany = new JMenuItem("Компания");

        aboutInfo = new JMenuItem("О программе");

        // Добавляем слушателей на пункты меню
        createCompany.addActionListener(this);
        aboutInfo.addActionListener(this);

        // Добавляем пункты в меню
        createMenu.add(createCompany);
        createMenu.add(aboutInfo);

        helpMenu.add(aboutInfo);

        // Добавляем меню в меню-бар
        menuBar.add(createMenu);
        menuBar.add(helpMenu);
    }

    public static class TableLabel extends JLabel {
        public TableLabel (String text) {
            this.setText(text);

            this.setBorder(new CompoundBorder( // задаёт 2 бордера
                    BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                    BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            this.setFont(new Font("", Font.PLAIN, 17));
        }
    }

    // создаём класс обработчика событий, реализующий интерфейс MouseListener
    public class NameCompany extends JLabel implements MouseListener {
        int companyNumber;
        public NameCompany (String text, int number, int idManag) {
            this.setText(text);
            this.companyNumber = number;
            idFromBD = idManag;
            this.setBorder(new CompoundBorder( // задаёт 2 бордера
                    BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                    BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            this.setFont(new Font("", Font.PLAIN, 17));
            this.addMouseListener(this);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            new ProsmotrContact(companyNumber, idFromBD, post, fio);
            MyFrame.this.dispose();
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

    public void createTable(String querry) {
        tablePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        tablePanel.setLayout(new java.awt.GridBagLayout());
        tablePanel.setBorder(BorderFactory.createLineBorder(Color.black, 1));

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy = 0; // нулевая строка по вертикали
        constraints.weightx = 0.5;

        constraints.gridx = 0; // нулевая ячейка по горизонтали

        tablePanel.add(new JButton("Название компании"), constraints);


        constraints.gridx = 1;  // 1-ая ячейка по горизонтали
        tablePanel.add(new JButton("ИНН компании"), constraints);

        constraints.gridx = 2;  // 2-ая ячейка по горизонтали
        tablePanel.add(new JButton("Телефон"), constraints);

        constraints.gridx = 3;  // 3-ая ячейка по горизонтали
        tablePanel.add(new JButton("Тип контакта"), constraints);

        constraints.gridx = 4;  // 4-ая ячейка по горизонтали
        tablePanel.add(new JButton("Дата контакта"), constraints);

        constraints.gridx = 5;  // 5-ая ячейка по горизонтали
        tablePanel.add(new JButton("Менеджер"), constraints);

        int line = bdQuerry (querry, 1);

        // кнопка внизу таблицы
        constraints.gridy = line+1; // 2 строка по вертикали
        constraints.gridx = 5; // нулевая строка по горизонтали
        constraints.weighty   = 1.0;  // установить отступ
        constraints.anchor = GridBagConstraints.PAGE_END;
        JButton endButton = new JButton("Всего строк "+line);

        tablePanel.add(endButton, constraints);

        scroll = new JScrollPane (tablePanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    @Override
    public int fillTable(ResultSet rs){
        int lines = 0;

        try {
            for (int i = 1; rs.next(); i++) {
                constraints.gridy = i; // добавляем строку по вертикали

                constraints.gridx = 0;
                String nameComp = rs.getString("name_comp");
                int idCompany = rs.getInt("ID");
                tablePanel.add(new NameCompany(nameComp, idCompany, idManager),constraints);

                constraints.gridx = 1;
                String innComp = rs.getString("inn");

                if (innComp == null || innComp.equals("")) {
                    innComp = "-";
                }

                tablePanel.add(new TableLabel(innComp),constraints);

                constraints.gridx = 2;
                String phoneComp = rs.getString("phone_comp");

                if (phoneComp == null || phoneComp.equals("")) {
                    phoneComp = "-";
                }
                tablePanel.add(new TableLabel(phoneComp),constraints);

                // Тип контакта из таблицы contacts
                constraints.gridx = 3;
                String contact = rs.getString("type_cont");

                if (contact == null || contact.equals("")) {
                    contact = "-";
                }
                tablePanel.add(new TableLabel(contact),constraints);

                // Дата контакта из таблицы contacts
                constraints.gridx = 4;
                Date dateContact = rs.getDate("max(date)");

                String s;
                if (dateContact == null) {
                    s = "-";
                } else {
                    s = formatter.format(dateContact);}

                tablePanel.add(new TableLabel(s),constraints);

                // ID менеджера из таблицы managers
                constraints.gridx = 5;
                String manager = rs.getString("fio");
                tablePanel.add(new TableLabel(manager),constraints);

                lines = i;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return  lines;
    }
    @Override
    public int fillManagers(ResultSet rs){
        int lines = 0;

        try {
            for (int i = 0; rs.next(); i++) {
                String fio = rs.getString("fio");
                arrayManagers.add(fio);

                int index = rs.getInt("id");
                if (idManager == index) {
                    indexIdManager = i;
                }
                lines++;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return  lines;
    }

    public String[] getManagerFromBD(){
        int lentghArray = bdQuerry("SELECT id, fio FROM managers WHERE isActive = 1", 4);
        String[] managers = new String[lentghArray];
        for (int i = 0; i < lentghArray; i++) {
            managers[i] = arrayManagers.get(i);
        }
        return managers;
    }

    public void updateTable(String querry) {
        tablePanel.removeAll();
        scroll.removeAll();
        this.remove(scroll);

        createTable(querry);
        this.add(scroll,BorderLayout.CENTER);

        validate();
        repaint();
    }
}
