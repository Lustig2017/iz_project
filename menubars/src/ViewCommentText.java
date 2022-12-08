import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ViewCommentText extends JFrame implements ActionListener {

    JPanel viewPanel = new JPanel();
    JButton close;
    String comment;
    ViewCommentText(String text) {
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        this.setSize(700,330);
        this.setLayout(new BorderLayout(10,10));
        this.setResizable(false);
        this.comment = text;
        this.setTitle("Просмотр комментария");
        this.setIconImage(new ImageIcon("img/logo.jpg").getImage());

        createView();

        this.add(viewPanel,BorderLayout.NORTH);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // Создаем область просмотра
    public void createView() {
        viewPanel.setBackground(Color.lightGray);
        viewPanel.setPreferredSize(new Dimension(700, 330));
        viewPanel.setLayout(null);

        // Третья строка
        JLabel commentCompLabel = new JLabel("Комментарий менеджера:");
        commentCompLabel.setBounds(20,20,200, 20);

        // текстовое поле просмотра
        JTextArea commentCompText = new JTextArea(comment);
        commentCompText.setBounds(20, 50, 640, 190);

        // Создаем кнопку закрытия окна
        close = new JButton("Закрыть");
        close.setBounds(270, 250, 120, 25);
        close.addActionListener(this);

        viewPanel.add(commentCompLabel);
        viewPanel.add(commentCompText);
        viewPanel.add(close);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()== close){
            ViewCommentText.this.dispose();
        }
    }
}
