import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Alert extends JFrame implements ActionListener {
        JPanel alertPanel = new JPanel();
        JLabel textAlert; // переменная
        String message;

    public Alert(String textComing) {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE); // HIDE_ON_CLOSE
        this.setSize(350, 150);
        this.setLayout(new BorderLayout(10, 10));
        // Этот метод устанавливает строку заголовка окна
        this.setTitle("Внимание!");
        this.setResizable(false);
        this.message = textComing;
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        textAlert(); // Выводим текст сообщения и кнопку во Фрейме

        this.add(alertPanel, BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    // Создаем текст сообщения
    private void textAlert() {
        alertPanel.setBackground(Color.white);
        alertPanel.setPreferredSize(new Dimension(350, 150));
        alertPanel.setLayout(null);

        // Текст сообщения
        textAlert = new JLabel(message, SwingConstants.CENTER);
        textAlert.setBounds(20, 20, 300, 50);

        alertPanel.add(textAlert);
        }
    }


