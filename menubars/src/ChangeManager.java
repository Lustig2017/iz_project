import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChangeManager extends SuperFrame implements ActionListener {
    JPanel changePanel = new JPanel();

    // Объявляем объекты области замены менджера
    JTextField nameCompText;
    JTextField fioManagerText;
    JComboBox comboboxManagers;
    List<Integer> arrayIdManagers = new ArrayList<>();

    // Создаём конструктор
    public ChangeManager(String nameComp,String fioManager, int idComp) {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // HIDE_ON_CLOSE
        this.setSize(420, 200);
        this.setLayout(new BorderLayout(10, 10));

        // Этот метод устанавливает строку заголовка окна
        this.setTitle("Назначить менеджера");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());
        this.setResizable(false);
        this.fio = fioManager;
        this.nameCompany = nameComp;
        this.idCompany = idComp;

        ChangeManagerArea();

        this.add(changePanel, BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Создаём область назначения нового менеджера
    public void ChangeManagerArea(){
        changePanel.setBackground(Color.white);
        changePanel.setPreferredSize(new Dimension(420, 200));
        changePanel.setLayout(null);

        // Указатель Название компании
        JLabel nameCompLabel = new JLabel("Название компании");
        nameCompLabel.setBounds(20, 10, 150, 20);

        // Поле Название компании
        nameCompText = new JTextField(nameCompany); // здесь д.б. переменная с названием компании
        nameCompText.setBounds(180, 10, 200, 20);
        nameCompText.setBackground(Color.white);
        nameCompText.setFocusable(false);

        // Указатель ФИО менеджера
        JLabel currentManagerLabel = new JLabel("Текущий менеджер");
        currentManagerLabel.setBounds(20, 40, 150, 20);

        // Поле Название компании
        fioManagerText = new JTextField(fio); // здесь д.б. переменная с ФИО менеджера
        fioManagerText.setBounds(180, 40, 200, 20);
        fioManagerText.setBackground(Color.white);
        fioManagerText.setFocusable(false);

        // Указатель выбора ФИО менеджера
        JLabel changeManagerLable = new JLabel("Назначаемый менеджер");
        changeManagerLable.setBounds(20, 70, 150, 20);

        // Поле выбора ФИО менеджера
        String[] managers = getManagerFromBD();
        comboboxManagers = new JComboBox(managers);
        comboboxManagers.setBounds(180,70,200, 20);
        comboboxManagers.setSelectedIndex(indexIdManager);

        // Кнопки
        // Кнопка Назначить
        JButton changeManager = new JButton("Назначить");
        changeManager.setBounds(30,100,160, 25);;
        changeManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int indexManagers = comboboxManagers.getSelectedIndex();
                bdQuerry("UPDATE kompany SET id_manager='" + arrayIdManagers.get(indexManagers) + "' WHERE id = '" + idCompany + "'", 3);

                ChangeManager.this.dispose();
            }
        });

        // Кнопка Отменить
        JButton cancelManager = new JButton("Отменить");
        cancelManager.setBounds(210,100,160, 25);;
        cancelManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                ChangeManager.this.dispose();
            }
        });



        // Запуск полей
        changePanel.add(nameCompLabel);
        changePanel.add(nameCompText); // Запуск поля Название компании

        changePanel.add(currentManagerLabel);
        changePanel.add(fioManagerText); // Запуск поля Название компании

        changePanel.add(changeManagerLable);
        changePanel.add(comboboxManagers); // Запуск поля Название компании

        changePanel.add(changeManager);
        changePanel.add(cancelManager);
    }

    public String[] getManagerFromBD(){
        int lentghArray = bdQuerry("SELECT id, fio FROM managers WHERE isActive = 1", 4);
        String[] managers = new String[lentghArray];
        for (int i = 0; i < lentghArray; i++) {
            managers[i] = arrayManagers.get(i);
        }
        return managers;
    }

    @Override
    public int fillManagers(ResultSet rs){
        int lines = 0;

        try {
            for (int i = 0; rs.next(); i++) {
                String fio = rs.getString("fio");
                arrayManagers.add(fio);

                int index = rs.getInt("id");
                arrayIdManagers.add(index);

                lines++;
            }

        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
        return  lines;
    }
}
