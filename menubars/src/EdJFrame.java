import javax.swing.*;
import java.awt.*;

public class EdJFrame extends JFrame {

    // Конструктор
    public EdJFrame(){
        // Этот метод удаляет программу из памяти после закрытия крестика слева вверху
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Этот метод объединяет два: setDefaultCloseOperation и setLocation. Принимает 4 аргумента: по оси x, y, по ширине и высоте
        this.setBounds(450, 200, 400, 400);

        // Этот метод устанавливает строку заголовка окна
        this.setTitle("Введите логин и пароль");

        // Задаем цвет фона окну
        // Сначала создаем контейнер
        Container c = this.getContentPane();
        // Затем вызываем этот метод и задаем цвет
        c.setBackground(Color.lightGray);
        // Этот метод запрещает пользователю изменять размер окна false
        this.setResizable(false);

        // Метод делает видимым на экране элементов данного класса
        this.setVisible(true);
    }
    // Конец конструктора
}
