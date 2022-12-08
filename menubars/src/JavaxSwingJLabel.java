import javax.swing.*;
import java.awt.*;

public class JavaxSwingJLabel extends JFrame {

    public JavaxSwingJLabel() {
        // Создаем объект класса JLabel

        this.setTitle("JLabel");
        this.setVisible(true);
        this.setSize(500,500);
        Container c= this.getContentPane();
        JLabel label=new JLabel();
        label.setText("myLabel"); // второй способ дать название
        label.setBounds(200,100,100,100);//задает расположение метки в виде текста на экране, а также его ширину и выосту

        this.add(label); // добавляет объект label на панель содержимого
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
