import java.awt.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import javax.swing.*;

public class DatePicker  {
    int month = java.util.Calendar.getInstance().get(java.util.Calendar.MONTH);
    int year = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR);;
    JLabel l = new JLabel("", JLabel.CENTER);
    String day = "";
    JDialog d;
    JButton[] button = new JButton[49];

    public DatePicker(JPanel parent) {
        d = new JDialog();
        d.setModal(true);
        String[] header = { "Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс" };
        JPanel p1 = new JPanel(new GridLayout(7,7));
        p1.setPreferredSize(new Dimension(430, 120));

        for (int x = 0; x < button.length; x++) {
            final int selection = x;
            button[x] = new JButton();
            button[x].setFocusPainted(false);
            button[x].setBackground(Color.white);
            if (x > 6)
                if (x == 12 || x == 13 || x == 19 || x == 20 || x == 26 || x == 27 || x == 33 || x == 34 || x == 40 || x == 41 ) {
                    button[x].setForeground(Color.red);
                }

            button[x].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    day = button[selection].getActionCommand();
                    d.dispose();
                }
            });
            if (x < 7) {
                button[x].setText(header[x]);
                if (x == 5 || x == 6) {
                    button[x].setForeground(Color.red);
                }
            }
            p1.add(button[x]);
        }
        JPanel p2 = new JPanel(new GridLayout(1, 3));
        JButton previous = new JButton("<<");
        previous.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month--;
                displayDate();
            }
        });
        p2.add(previous);
        p2.add(l);
        JButton next = new JButton(">>");
        next.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                month++;
                displayDate();
            }
        });
        p2.add(next);
        d.add(p1, BorderLayout.CENTER);
        d.add(p2, BorderLayout.SOUTH);
        d.pack();
        d.setLocationRelativeTo(parent);
        displayDate();
        d.setVisible(true);
    }

    public void displayDate() {

        for (int x = 7; x < button.length; x++)
            button[x].setText("");
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("MMMM yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, 1);
        int dayOfWeek = cal.get(java.util.Calendar.DAY_OF_WEEK);
        int daysInMonth = cal.getActualMaximum(java.util.Calendar.DAY_OF_MONTH);

        if ( (5 + dayOfWeek) == 6) {
            for (int x = 12 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++) // было int x = 6 + dayOfWeek
                button[x].setText("" + day);
        } else {
            for (int x = 5 + dayOfWeek, day = 1; day <= daysInMonth; x++, day++) // было int x = 6 + dayOfWeek
                button[x].setText("" + day);
        }


        l.setText(sdf.format(cal.getTime()));
        d.setTitle("Календарь");
    }

    public String setPickedDate() {
        if (day.equals(""))
            return day;
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
                "dd.MM.yyyy");
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.set(year, month, Integer.parseInt(day));
        return sdf.format(cal.getTime());
    }
}
