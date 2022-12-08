import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Help extends JFrame implements ActionListener {
    JPanel helpPanel = new JPanel();

    // Создаём конструктор
    public Help() {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // HIDE_ON_CLOSE
        this.setSize(500, 300);
        this.setLayout(new BorderLayout(10, 10));
        // Этот метод устанавливает строку заголовка окна
        this.setTitle("О программе");
        this.setResizable(false);
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        HelpInfo();

        this.add(helpPanel, BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Создаём область информация о программе
    public void HelpInfo(){
        helpPanel.setBackground(Color.white);
        helpPanel.setPreferredSize(new Dimension(500, 300));
        helpPanel.setLayout(null);

        JLabel nameProgram = new JLabel("Программа «Автоматизированная CRM-система для учёта клиентов»");
        nameProgram.setBounds(20,20,460, 20);
        nameProgram.setFocusable(false);

        JTextArea text2Program = new JTextArea("Программа будет полезна для сотрудников отделов первичных продаж. " +
                "Как правило, сотрудники нуждаются в систематизированном учёте данных о клиентах: контактах, " +
                "адресах и прочей важной информации. Программа решает эту задачу, предоставляя возможность заводить " +
                "клиентов под разными сотрудниками, передавать компании между сотрудниками, вводить данные о компаниях, " +
                "редактировать и актуализировать эту информацию.");
        text2Program.setBounds(20,50,460, 150);
        text2Program.setFocusable(false);
        text2Program.setLineWrap(true);
        text2Program.setWrapStyleWord(true);

        JTextArea text1Program = new JTextArea("Настоящая программа является дипломной работой на курсе обучения " +
                "инженер-программист Частного образовательного учреждения дополнительного профессионального образования «Институт прикладной автоматизации и программирования» (ЧОУ ДПО ИПАП).");
        text1Program.setBounds(20,160,460, 80);
        text1Program.setFocusable(false);
        text1Program.setFocusable(false);
        text1Program.setLineWrap(true);
        text1Program.setWrapStyleWord(true);

    // Запуск полей
        helpPanel.add(nameProgram);
        helpPanel.add(text1Program);
        helpPanel.add(text2Program);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
