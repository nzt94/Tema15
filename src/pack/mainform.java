package pack;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class mainform extends JFrame{
	public Label label1 = new Label("Список дисциплин");
	public Button button1 = new Button("Добавить дисциплину");
	public Choice choice1 = new Choice();//Список дисциплин

	public Label label2 = new Label("Список билетов");
	public Button button2 = new Button("Добавить билет");
	public Choice choice2 = new Choice();//список билетов

	public Label label3 = new Label("Список вопросов");
	public Button button3 = new Button("Добавить вопрос");
	public Choice choice3 = new Choice();//список вопросов
	
	
	
	
	private static final long serialVersionUID = 1L;
	private static exportrtf rtfo=new exportrtf("template");
	private static dbase dbo=new dbase();
	public mainform() {
		super("Главная форма");
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 50, 800, 480);
		setExtendedState(MAXIMIZED_BOTH);
		setVisible(true);

		label1.setBounds(10, 10, 125, 24);
		getContentPane().add(label1);

		button1.setBounds(467, 10, 158, 24);
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){//Создание дисциплины
				String name=""+JOptionPane.showInputDialog("Название дисциплины");
				if(name.length()>0){
					dbo.subjects.insert(new String[]{""+dbo.subjects.size(),name});
					dbo.subjects.save();
					fillchoice1();
				}
			}
		});
		getContentPane().add(button1);

		choice1.setBounds(141, 12, 307, 22);
		fillchoice1();
		choice1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				//выбор дисциплины, загрузка списка билетов
				if(choice1.getSelectedIndex()>0){
					button2.setEnabled(true);
					fillchoice2();
				}
			}
		});
		getContentPane().add(choice1);

		label2.setBounds(10, 40, 125, 24);
		getContentPane().add(label2);

		button2.setBounds(467, 40, 158, 24);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Создание билета, скорее всего, тоже, запрос названия билета
				String name=JOptionPane.showInputDialog("Название билета");
				if(name.length()>0){
					int qid=Integer.parseInt(choice2.getItem(choice3.getItemCount()-1))+1;
					dbo.cards.insert(new String[]{""+qid,""+dbo.cards.size(),name});
					dbo.cards.save();
					fillchoice2();
				}
			}
		});
		button2.setEnabled(false);//пока не выбрана дициплина, неактивен
		getContentPane().add(button2);

		choice2.setBounds(141, 42, 307, 22);
		choice2.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				/*выбор дисциплины и закрузка списка вопросов в билете*/
			}
		});
		choice2.setEnabled(false);//пока не выбрана дициплина, неактивен
		getContentPane().add(choice2);

		label3.setBounds(10, 70, 125, 24);
		getContentPane().add(label3);

		button3.setBounds(467, 70, 158, 24);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*Диалог создания вопроса*/
			}
		});
		button3.setEnabled(false);//пока не выбран билет, неактивен
		getContentPane().add(button3);

		choice3.setBounds(141, 72, 307, 22);
		choice3.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				/*выбор вопроса и отображение его в поле редактирования*/
			}
		});
		choice3.setEnabled(false);//пока не выбран билет, неактивен
		getContentPane().add(choice3);
	}
	private void fillchoice1(){
		choice1.removeAll();
		choice1.addItem("Выберите дисциплину");
		for(int i=0;i<dbo.subjects.size();i++)
			choice1.addItem(dbo.subjects.select(i,"sname"));
		
	}
	private void fillchoice2(){
		choice2.removeAll();
		choice2.setEnabled(false);
		choice2.addItem("Выберите билет");
		for(int i=0;i<dbo.cards.size();i++){
			label1.setText(""+i);
			if(dbo.cards.select(i,"sid").equals(""+choice1.getSelectedIndex()))
				choice2.addItem(dbo.cards.select(i,"cid"));
		}
		if(choice2.getItemCount()>1)
			choice2.setEnabled(true);
		
	}
}
