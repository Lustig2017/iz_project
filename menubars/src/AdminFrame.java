import javax.swing.*;
import javax.swing.border.CompoundBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminFrame extends SuperFrame implements ActionListener {
    JMenuBar menuBar;
    JMenu createMenu;
    JMenuItem createAccaunt;
    JPanel tablePanel = new JPanel();
    JScrollPane scroll;
    GridBagConstraints constraints; // Глобально объявляем переменную для таблицы


    // Создаём конструктор
    AdminFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000,800);
        this.setLayout(new BorderLayout(10,10));
        this.setTitle("Административная часть");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        createMenuBar(); // Создаем меню
        createTable("SELECT id, fio, post, login, isActive FROM managers");

        this.add(tablePanel,BorderLayout.NORTH);
        this.add(scroll,BorderLayout.CENTER);

        // Добавить меню-бар на экран фрейма
        this.setJMenuBar(menuBar);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Слушатели - обработчики для меню
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== createAccaunt){
            new AddUser();
            AdminFrame.this.dispose();
        }
    }

    // Создание меню
    public void createMenuBar () {
        // Инициализация переменных
        menuBar = new JMenuBar();
        createMenu = new JMenu("Создать");
        createAccaunt = new JMenuItem("Аккаунт");

        // Добавляем слушателей на пункты меню
        createAccaunt.addActionListener(this);

        // Добавляем пункты в меню
        createMenu.add(createAccaunt);

        // Добавляем меню в меню-бар
        menuBar.add(createMenu);
    }

    public static class TableLabel extends JLabel {
        public TableLabel (String text) {
            this.setText(text);

            //this.setBorder(BorderFactory.createLineBorder(Color.black, 1));
            this.setBorder(new CompoundBorder( // задаёт 2 бордера
                    BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                    BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            this.setFont(new Font("", Font.PLAIN, 17));
        }
    }

    // создаём класс обработчика событий, реализующий интерфейс MouseListener при нажатии на ФИО
    public class NameAccaunt extends JLabel implements MouseListener {
        int idNumberFromBD;
        public NameAccaunt(String text, int idManag) {
            this.setText(text);
            this.idNumberFromBD = idManag;
            this.setBorder(new CompoundBorder( // задаёт 2 бордера
                    BorderFactory.createMatteBorder(0, 1, 1, 0, Color.black), // outer border
                    BorderFactory.createEmptyBorder(0, 5, 0, 0)));
            this.setFont(new Font("", Font.PLAIN, 17));
            this.addMouseListener(this);
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            new EditUser(idNumberFromBD);
            AdminFrame.this.dispose();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }

    // Создаём тиблицу
    public void createTable(String query) {
        tablePanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        tablePanel.setLayout(new java.awt.GridBagLayout());

        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        constraints.gridy = 0; // нулевая строка по вертикали
        constraints.weightx = 0.5;

        constraints.gridx = 0; // нулевая ячейка по горизонтали
        tablePanel.add(new JButton("ФИО"), constraints);

        constraints.gridx = 1;  // 1-ая ячейка по горизонтали
        tablePanel.add(new JButton("Логин"), constraints);

        constraints.gridx = 2;  // 2-ая ячейка по горизонтали
        tablePanel.add(new JButton("Должность"), constraints);

        constraints.gridx = 3;  // 3-ая ячейка по горизонтали
        tablePanel.add(new JButton("Статус"), constraints);

        int line = bdQuerry (query, 1); // Запрос к базе данных String querry принимается в параметрах метода createTable()

        // кнопка внизу таблицы
        constraints.gridy = line+1; // 2 строка по вертикали
        constraints.gridx = 3; // нулевая строка по горизонтали
        constraints.weighty   = 3.0;  // установить отступ
        constraints.anchor = GridBagConstraints.PAGE_END;
        JButton endButton = new JButton("Всего строк " + line);

        tablePanel.add(endButton, constraints);

        scroll = new JScrollPane (tablePanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
    }

    // Заполняем таблицу из БД
    @Override
    public int fillTable(ResultSet rs){
        int lines = 0;

        try {
            for (int i = 1; rs.next(); i++) {
                constraints.gridy = i; // добавляем строку по вертикали

                constraints.gridx = 0;
                String fioAccount = rs.getString("fio");
                int idAccount = rs.getInt("id");

                tablePanel.add(new AdminFrame.NameAccaunt(fioAccount, idAccount),constraints); // в параметры конструктора NameAccaunt() должны передаваться параметры

                constraints.gridx = 1;
                String loginAccount = rs.getString("login");
                if (loginAccount == null || loginAccount.equals("")) {
                    loginAccount = "-";
                }
                tablePanel.add(new AdminFrame.TableLabel(loginAccount),constraints);

                constraints.gridx = 2;
                String postAccount = rs.getString("post");
                if (postAccount == null || postAccount.equals("")) {
                    postAccount = "-";
                }
                tablePanel.add(new AdminFrame.TableLabel(postAccount),constraints);

                constraints.gridx = 3;
                String statucAccount = rs.getString("isActive");

                if (statucAccount == null || statucAccount.equals("")) {
                    statucAccount = "-";
                }
                if (statucAccount.equals("1")) {
                    statucAccount = "Активный";
                }
                if (statucAccount.equals("0")) {
                    statucAccount = "Заблокирован";
                }

                tablePanel.add(new AdminFrame.TableLabel(statucAccount),constraints);

                lines = i;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }

        return  lines;
    }
}
