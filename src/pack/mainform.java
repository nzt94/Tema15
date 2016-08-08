package pack;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class mainform extends JFrame{
	private static final long serialVersionUID = 1L;
	public mainform() {
		super("Главная форма");
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 50, 800, 480);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);

		Label label1 = new Label("Список дисциплин");
		label1.setBounds(10, 10, 125, 24);
		getContentPane().add(label1);

		Choice choice1 = new Choice();//Список дисциплин
		choice1.setBounds(141, 12, 307, 22);
		choice1.addItem("Выберите дисциплину");
		/*Заполнение списка дисциплин из таблицы*/
		choice1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				/*выбор дисциплины и загрузка списка билетов*/
			}
		});
		getContentPane().add(choice1);

		Label label2 = new Label("Список билетов");
		label2.setBounds(10, 40, 125, 24);
		getContentPane().add(label2);

		Choice choice2 = new Choice();//список вопросов
		choice2.setBounds(141, 42, 307, 22);
		choice2.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				/*выбор дисциплины и закрузка списка вопросов в билете*/
			}
		});
		choice2.setEnabled(false);//пока не выбрана дициплина, неактивен
		getContentPane().add(choice2);
		
		Label label3 = new Label("Список вопросов");
		label3.setBounds(10, 70, 125, 24);
		getContentPane().add(label3);

		Choice choice3 = new Choice();
		choice3.setBounds(141, 72, 307, 22);
		choice3.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				/*выбор вопроса и отображение его в поле редактирования*/
			}
		});
		choice3.setEnabled(false);//пока не выбран билет, неактивен
		getContentPane().add(choice3);
	}
}
