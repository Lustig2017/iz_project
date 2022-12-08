import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TransferCompany extends SuperFrame implements ActionListener {
    // Переменные
    JPanel changePanel = new JPanel();
    JComboBox comboboxManagers;
    List<Integer> arrayIdManagers = new ArrayList<>();

    // Конструктор
    public TransferCompany(int idManag) {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // HIDE_ON_CLOSE
        this.setSize(400, 200);
        this.setLayout(new BorderLayout(10, 10));
        // Этот метод устанавливает строку заголовка окна
        this.setTitle("Назначить менеджера");
        this.setResizable(false);
        this.idManager = idManag;
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        ChangeManagerArea();

        this.add(changePanel, BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // обработка кнопки закрыть Х
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new EditUser(idManager);
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    // Методы
    // Создаём область перевода клиентов на другого менеджера
    public void ChangeManagerArea(){
        changePanel.setBackground(Color.white);
        changePanel.setPreferredSize(new Dimension(400, 200));
        changePanel.setLayout(null);

        // Указатель Название компании
        JLabel nameCompLabel = new JLabel("У данного менеджера есть компании.");
        nameCompLabel.setBounds(20, 10, 340, 20);

        // Указатель Название компании
        JLabel nameCompLabel2 = new JLabel("Переведите их на другого менеджера.");
        nameCompLabel2.setBounds(20, 40, 340, 20);

        // Указатель выбора ФИО менеджера
        JLabel changeManagerLable = new JLabel("Назначаемый менеджер");
        changeManagerLable.setBounds(20, 70, 150, 20);

        // Поле выбора ФИО менеджера
        String[] managers = getManagerFromBD();
        comboboxManagers = new JComboBox(managers);
        comboboxManagers.setBounds(180,70,180, 20);
        comboboxManagers.setSelectedIndex(indexIdManager);

        // Кнопки
        // Кнопка Назначить
        JButton changeManager = new JButton("Назначить");
        changeManager.setBounds(20,100,160, 25);;
        changeManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                int indexManagers = comboboxManagers.getSelectedIndex();
                bdQuerry("UPDATE kompany SET id_manager = " + arrayIdManagers.get(indexManagers) + " WHERE id_manager = " + idManager, 3);

                TransferCompany.this.dispose();
                new EditUser(idManager);
            }
        });

        // Кнопка Отменить
        JButton cancelManager = new JButton("Отменить");
        cancelManager.setBounds(200,100,160, 25);;
        cancelManager.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                TransferCompany.this.dispose();
                new EditUser(idManager);
            }
        });

        // Запуск полей
        changePanel.add(nameCompLabel);
        changePanel.add(nameCompLabel2);

        changePanel.add(changeManagerLable);
        changePanel.add(comboboxManagers); // Запуск поля Название компании

        changePanel.add(changeManager);
        changePanel.add(cancelManager);
    }

    public String[] getManagerFromBD(){
        int lentghArray = bdQuerry("SELECT id, fio FROM managers WHERE isActive = 1 and id != " + idManager, 4);
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
