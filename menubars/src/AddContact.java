import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddContact extends SuperFrame implements ActionListener {
    JPanel contactPanel = new JPanel();
    JButton sozdanieContacta;
    JButton clearContact;

    // Переменные карточки
    JTextField nameCompText;
    JComboBox comboboxTypes;
    JTextField dateFromText;
    JTextArea commentCompText;

    AddContact(String nameComp, int idMan, int idComp, String postFromBD, String fioFromBD){

        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setSize(700,400);
        this.setLayout(new BorderLayout(10,10));
        this.setResizable(false);
        this.nameCompany = nameComp;
        this.idManager = idMan;
        this.idCompany = idComp;
        this.post = postFromBD;
        this.fio = fioFromBD;
        this.setTitle("Создать контакт");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        createContact(); // Создаем Контакт

        this.add(contactPanel,BorderLayout.NORTH);

        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // обработка кнопки закрыть Х
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                new ProsmotrContact(idCompany, idManager, post, fio);
            }
        });
    }

    // Создаем Контакт
    public void createContact() {
        contactPanel.setBackground(Color.lightGray);
        contactPanel.setPreferredSize(new Dimension(700, 400));
        contactPanel.setLayout(null);

        // Первая строка
        JLabel nameCompLabel = new JLabel("Название компании");
        nameCompLabel.setBounds(20,10,130, 20);

        // текстовое поле первой стоки
        nameCompText = new JTextField();
        nameCompText.setBounds(160,10,500, 20);
        nameCompText.setText(nameCompany);
        nameCompText.setFocusable(false);



        // лейблы вторая строка
        // тип контакта
        JLabel contactTypeLable = new JLabel("Тип контакта");
        contactTypeLable.setBounds(20,40,80, 20);

        String[] types = {"Звонок", "Встреча", "Сделка"};
        comboboxTypes = new JComboBox(types);
        comboboxTypes.setBounds(110,40,80, 20);
        comboboxTypes.setSelectedIndex(-1);

        // Дата контакта
        JLabel dateFromLable = new JLabel("Дата");
        dateFromLable.setBounds(200,40,50, 20);

        dateFromText = new JTextField();
        dateFromText.setBounds(260,40,70, 20);

        JButton dateFromButton = new JButton("popup");
        dateFromButton.setBounds(330,40,20, 20);
        dateFromButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                dateFromText.setText(new DatePicker(contactPanel).setPickedDate());
            }
        });

        // Текстовая область комментария
        // Третья строка
        JLabel commentCompLabel = new JLabel("Комментарий менеджера:");
        commentCompLabel.setBounds(20,70,200, 20);

        // текстовое поле третьей стоки
        commentCompText = new JTextArea();
        commentCompText.setBounds(20,100,640, 190);

        // Кнопки

        sozdanieContacta = new JButton("Создать");
        sozdanieContacta.setBounds(160,315,160, 25);
        sozdanieContacta.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                bdQuerry(setContact(),3);
                AddContact.this.dispose();
                new ProsmotrContact(idCompany, idManager, post, fio);
            }
        });

        clearContact = new JButton("Отмена");
        clearContact.setBounds(330,315,160, 25);;
        clearContact.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                AddContact.this.dispose();
                new ProsmotrContact(idCompany, idManager, post, fio);
            }
        });

        contactPanel.add(nameCompLabel);
        contactPanel.add(nameCompText);

        contactPanel.add(dateFromLable);
        contactPanel.add(contactTypeLable);

        contactPanel.add(comboboxTypes);

        contactPanel.add(dateFromText);
        contactPanel.add(dateFromButton);

        contactPanel.add(sozdanieContacta);
        contactPanel.add(clearContact);

        contactPanel.add(commentCompLabel);
        contactPanel.add(commentCompText);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== sozdanieContacta){
        }
        if(e.getSource()== clearContact){
        }
    }

    public String setContact() {
        String date = dateFromText.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate dateFormat = LocalDate.parse(date, formatter);

        String text = commentCompText.getText();

        String typeContact = null;
        if (comboboxTypes.equals("") || dateFromText.equals("") || commentCompText.equals("")) {
            new Alert("Заполните все поля");
        } else {
            typeContact = comboboxTypes.getSelectedItem().toString();
        }
        return "INSERT INTO contacts(id_managers, id_comp, date, type_cont, comment) VALUES ('" + idManager + "','" + idCompany + "','" + dateFormat + "','" + typeContact + "','" + text + "')";
    }
}
