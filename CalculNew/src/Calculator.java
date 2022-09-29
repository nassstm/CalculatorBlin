import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator
{
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() //отправляет этот объект в поток диспетчеризации событий, который выполняет метод run ().

        {
            public void run() {
                CalFrame frame = new CalFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
            }
        });
    }
}

class CalFrame extends JFrame {
    public CalFrame() {
        setTitle("Calculator");
        CalPanel panel = new CalPanel();
        add(panel);
        setSize(300,400);
    }
}
class CalPanel extends JPanel //пространство для приложения, которое может присоединить любой другой компонент
         {
             public JButton display;
             public JPanel panel;
             public double result;
             public String lastCommand;
             public boolean start;

    public CalPanel() {
        setLayout(new BorderLayout());

        result = 0;
        lastCommand = "=";
        start=true;

        display = new JButton("0");
        add(display, BorderLayout.NORTH);

        ActionListener insert = new InsertAction(); // деф класс для обработки события действия
        ActionListener command = new CommandAction();
        ActionListener clear = new ClearAction();

        panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4));

        addButton("7", insert);
        addButton("8", insert);
        addButton("9", insert);
        addButton("/", command);

        addButton("4", insert);
        addButton("5", insert);
        addButton("6", insert);
        addButton("*", command);

        addButton("1", insert);
        addButton("2", insert);
        addButton("3", insert);
        addButton("-", command);

        addButton("0", insert);
        addButton(".", insert);
        addButton("=", command);
        addButton("+", command);

        addButton("C", clear);

        add(panel, BorderLayout.CENTER);
    }
    public void addButton(String label, ActionListener listener) {
        JButton button = new JButton(label);
        button.addActionListener(listener);
        panel.add(button);
    }
    public class InsertAction implements ActionListener //нажмали на кнопку
    {
        public void actionPerformed(ActionEvent event)
        {
            String input = event.getActionCommand(); //произошло определенное компонентом действие
            //после выбора числа и выбора действия очищается строка ввода
            if(start) {
                display.setText("");
                start = false;
            }
            display.setText(display.getText() + input);
        }
    }
    public class CommandAction implements ActionListener //работа с выполнением команд
    {
        public void actionPerformed(ActionEvent event)
        {
            String command = event.getActionCommand();
            calculate(Double.parseDouble(display.getText())); //для преобразования из целочисленного в десятичное
            lastCommand = command;
            start=true;
        }
    }

    public class ClearAction implements ActionListener  {
        public void actionPerformed(ActionEvent e) {
            display.setText(" ");
        }
    } //очистка
    public void calculate(double x)
    {
        if(lastCommand.equals("+")) result += x;
        else if(lastCommand.equals("-")) result -= x;
        else if(lastCommand.equals("*")) result *= x;
        else if(lastCommand.equals("/")) result /= x;
        else if(lastCommand.equals("=")) result = x;
        display.setText("" + result);
    }

}