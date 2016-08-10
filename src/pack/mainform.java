package pack;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class mainform extends JFrame{
	private static final long serialVersionUID = 1L;
	private export rtf=new export();
	private static dbase dbo=new dbase();

	public Label label1 = new Label("Список дисциплин");
	public Button button1 = new Button("Добавить");
	public Choice choice1 = new Choice();//Список дисциплин
	private int[] hiddenchoice1=new int[0];
	public Button button6 = new Button("Экспорт");
	public Button button7 = new Button("Удалить");

	public Label label2 = new Label("Список билетов");
	public Button button2 = new Button("Добавить");
	public Choice choice2 = new Choice();//список билетов
	private int[] hiddenchoice2=new int[0];
	public Button button8 = new Button("Удалить");

	public Label label3 = new Label("Список вопросов");
	public Button button3 = new Button("Добавить");
	public Choice choice3 = new Choice();//список вопросов
	private int[] hiddenchoice3=new int[0];
	public Button button9 = new Button("Удалить");

	public Label label4 = new Label("Текст вопроса");
	public TextArea textArea1 = new TextArea();
	public Button button4 = new Button("Сохранить");
	public Button button5 = new Button("Отмена");

	public mainform() {
		super("Главная форма");
		getContentPane().setLayout(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setBounds(50, 50, 800, 480);
		setExtendedState(MAXIMIZED_BOTH);

		label1.setBounds(10, 10, 102, 24);
		getContentPane().add(label1);

		button1.setBounds(433, 10, 80, 24);
		button1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){//Создание дисциплины
				String name=""+JOptionPane.showInputDialog("Название дисциплины");
				if(name.length()>0){
					dbo.subjects.insert(new String[]{""+(dbo.subjects.size()+1),name});
					dbo.subjects.save();
					fillchoice1();
				}
			}
		});
		getContentPane().add(button1);

		choice1.setBounds(118, 12, 307, 22);
		fillchoice1();
		choice1.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				//выбор дисциплины, загрузка списка билетов
				boolean access=choice1.getSelectedIndex()>0;
				if(access)
					fillchoice2();
				button2.setEnabled(access);
				button6.setEnabled(access);
				button7.setEnabled(access);
			}
		});
		getContentPane().add(choice1);
		button7.setBounds(519, 10, 80, 24);
		button7.setEnabled(false);
		button7.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){//Удаление дисциплины
				int res=JOptionPane.showConfirmDialog(null,"Уверены, что хотите удалить дисциплину,\r\nвместе с ней удалятся все вопросы и билеты");
				if(res==JOptionPane.YES_OPTION){
					String sid=dbo.subjects.select(hiddenchoice1[choice1.getSelectedIndex()],"sid");
					for(int i=0;i<dbo.cards.size();i++)
						if(dbo.cards.select(i,"sid").equals(sid)){
							for(int j=0;j<dbo.questions.size();j++)
								if(dbo.questions.select(j,"cid").equals(dbo.cards.select(i,"cid")))
									dbo.questions.delete(j);
							dbo.cards.delete(i);
						}
					dbo.subjects.delete(hiddenchoice1[choice1.getSelectedIndex()]);
					dbo.questions.save();
					dbo.cards.save();
					dbo.subjects.save();
					fillchoice1();
				}
			}
		});
		getContentPane().add(button7);

		label2.setBounds(10, 40, 102, 24);
		getContentPane().add(label2);

		button2.setBounds(433, 40, 80, 24);
		button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Создание билета, скорее всего, тоже, запрос названия билета
				String name=JOptionPane.showInputDialog("Название билета");
				if(name.length()>0){
					dbo.cards.insert(new String[]{""+(dbo.cards.size()+1),""+choice1.getSelectedIndex(),name});
					dbo.cards.save();
					fillchoice2();
				}
			}
		});
		button2.setEnabled(false);//пока не выбрана дициплина, неактивен
		getContentPane().add(button2);

		choice2.setBounds(118, 42, 307, 22);
		choice2.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				//выбор дисциплины и закрузка списка вопросов в билете
				textArea1.setText("");
				textArea1.setEnabled(false);
				button4.setEnabled(false);
				button5.setEnabled(false);
				if(choice2.getSelectedIndex()>0){
					button3.setEnabled(true);
					button8.setEnabled(true);
					fillchoice3();
				}
			}
		});
		choice2.setEnabled(false);//пока не выбрана дициплина, неактивен
		getContentPane().add(choice2);
		button8.setEnabled(false);
		button8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Удаление билета
				int res=JOptionPane.showConfirmDialog(null,"Уверены, что хотите удалить билет,\r\nвместе с ним удалятся все вопросы");
				if(res==JOptionPane.YES_OPTION){
					String сid=dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()],"сid");
					for(int i=0;i<dbo.questions.size();i++)
						if(dbo.questions.select(i,"cid").equals(сid))
							dbo.questions.delete(i);
					dbo.cards.delete(hiddenchoice2[choice2.getSelectedIndex()]);
					dbo.questions.save();
					dbo.cards.save();
					fillchoice2();
				}
			}
		});
		button8.setBounds(519, 40, 80, 24);
		getContentPane().add(button8);

		label3.setBounds(10, 70, 102, 24);
		getContentPane().add(label3);

		button3.setBounds(433, 70, 80, 24);
		button3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/*Диалог создания вопроса*/
				textArea1.setText("");
				textArea1.setEnabled(true);
				button4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						//Сохранение при создании
						dbo.questions.insert(new String[]{
							""+(dbo.questions.size()+1),
							dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()],"cid"),
							textArea1.getText()
						});
						dbo.questions.save();
						fillchoice3();
					}
				});
				button4.setEnabled(true);
				button5.setEnabled(true);
			}
		});
		button3.setEnabled(false);//пока не выбран билет, неактивен
		getContentPane().add(button3);

		choice3.setBounds(118, 72, 307, 22);
		choice3.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e) {
				//выбор вопроса и отображение его в поле редактирования
				if(choice3.getSelectedIndex()>0){
					textArea1.setText(dbo.questions.select(hiddenchoice3[choice3.getSelectedIndex()],"qtext"));
					textArea1.setEnabled(true);
					button4.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent arg0) {
							//Сохранение при измнении
							String qid=dbo.questions.select(hiddenchoice3[choice3.getSelectedIndex()],"qid");
							dbo.questions.update(qid,"qid",new String[]{
								qid,
								dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()],"cid"),
								textArea1.getText()
							});
							dbo.questions.save();
							fillchoice3();
						}
					});
					button4.setEnabled(true);
					button5.setEnabled(true);
					button9.setEnabled(true);
				}
			}
		});
		choice3.setEnabled(false);//пока не выбран билет, неактивен
		getContentPane().add(choice3);
		button9.setEnabled(false);
		button9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Удаление вопроса
				int res=JOptionPane.showConfirmDialog(null,"Уверены, что хотите удалить вопрос");
				if(res==JOptionPane.YES_OPTION){
					dbo.questions.delete(hiddenchoice3[choice3.getSelectedIndex()]);
					dbo.questions.save();
					fillchoice3();
				}
			}
		});
		button9.setBounds(519, 70, 80, 24);
		getContentPane().add(button9);

		label4.setBounds(10, 100, 102, 24);
		getContentPane().add(label4);
		textArea1.setBounds(118, 102, 567, 180);
		textArea1.setEnabled(false);//пока не выбран вопрос, неактивен
		getContentPane().add(textArea1);

		button4.setBounds(519, 288, 80, 24);
		button4.setEnabled(false);//пока не выбран вопрос, неактивен
		getContentPane().add(button4);
		
		button5.setBounds(605, 288, 80, 24);
		button5.setEnabled(false);//пока не выбран вопрос, неактивен
		button5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Отменить и загрузить данные до изменения
				textArea1.setText("");
				textArea1.setEnabled(false);
				button4.setEnabled(false);
				button5.setEnabled(false);
			}
		});
		getContentPane().add(button5);

		button6.setBounds(605, 10, 80, 24);
		button6.setEnabled(false);
		button6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Отменить и загрузить данные до изменения
				rtf.save(hiddenchoice1[choice1.getSelectedIndex()]);
			}
		});
		getContentPane().add(button6);
		

	}
	private void fillchoice1(){
		choice1.removeAll();
		hiddenchoice1=new int[dbo.subjects.size()+1];
		choice1.addItem("Выберите дисциплину");
		hiddenchoice1[0]=-1;
		int j=1;
		for(int i=0;i<dbo.subjects.size();i++){
			choice1.addItem(dbo.subjects.select(i,"sname"));
			hiddenchoice1[j++]=i;
		}
	}
	private void fillchoice2(){
		choice2.removeAll();
		choice2.setEnabled(false);
		choice2.addItem("Выберите билет");
		String sid=dbo.subjects.select(hiddenchoice1[choice1.getSelectedIndex()],"sid");
		for(int i=0;i<dbo.cards.size();i++)
			if(dbo.cards.select(i,"sid").equals(sid))
				choice2.addItem(dbo.cards.select(i,"cnum"));
		hiddenchoice2=new int[choice2.getItemCount()];
		hiddenchoice2[0]=-1;
		if(choice2.getItemCount()>1){
			int j=1;
			for(int i=0;i<dbo.cards.size();i++)
				if(dbo.cards.select(i,"sid").equals(sid))
					hiddenchoice2[j++]=i;
			choice2.setEnabled(true);
		}
		choice3.removeAll();
		choice3.setEnabled(false);
		choice3.addItem("Выберите вопрос");
	}
	private void fillchoice3(){
		choice3.removeAll();
		choice3.setEnabled(false);
		choice3.addItem("Выберите вопрос");
		String cid=dbo.cards.select(hiddenchoice2[choice2.getSelectedIndex()],"cid");
		for(int i=0;i<dbo.questions.size();i++)
			if(dbo.questions.select(i,"cid").equals(cid))
				choice3.addItem(dbo.questions.select(i,"qtext"));
		hiddenchoice3=new int[choice3.getItemCount()];
		hiddenchoice3[0]=-1;
		if(choice3.getItemCount()>1){
			int j=1;
			for(int i=0;i<dbo.questions.size();i++)
				if(dbo.questions.select(i,"cid").equals(cid))
					hiddenchoice3[j++]=i;
			choice3.setEnabled(true);
		}
	}
}
