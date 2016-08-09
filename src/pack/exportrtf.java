package pack;

import java.awt.*;
import java.io.*;

import javax.swing.JOptionPane;

public class exportrtf {
	private String templatePath="";
	private String template="";
	private String header="{\\rtf1\\ansi\\ansicpg1251\\deff0\\nouicompat\\deflang1049{\\fonttbl{\\f0\\fnil\\fcharset204{\\*\\fname Times New Roman;}Times New Roman CYR;}{\\f1\\fnil\\fcharset1 Segoe UI Symbol;}{\\f2\\fnil\\fcharset0 Times New Roman;}}\\viewkind4\\uc1\r\n";
	private String footer="\r\n}\r\n";
	private static dbase dbo=new dbase();
	public exportrtf(String tmpName){
		templatePath=".\\template\\"+tmpName+".rtf";
		File file=new File(templatePath);
		if(file.exists()){
			try{
				BufferedReader in=new BufferedReader(new FileReader(file.getAbsoluteFile()));
				try{
					String s="",sl="";
					int i=0;
					while((sl=in.readLine())!=null)
						s+=sl+"\r\n";
					while(s.indexOf("\\par",i)>=0)
						i=s.indexOf("\\par",i)+4;
					template=s.substring(s.indexOf("\\pard"),i);
				}
				finally{
					in.close();
				}
			}
			catch(IOException e){
				throw new RuntimeException(e);
			}
		}
	}
	public void alert(Object o){
		String a=JOptionPane.showInputDialog(""+o);
	}
	public void export(String sid){
		String filetxt="",sname="";
		String date=JOptionPane.showInputDialog("Укажите дату подписи");
		String signer=JOptionPane.showInputDialog("Укажите должность и имя подписанта");
		for(int i=0;i<dbo.subjects.size();i++)
			sname=(dbo.subjects.select(i,"sid").equals(sid))?dbo.subjects.select(i,"sname"):sname;
		for(int i=0;i<dbo.cards.size();i++)
			if(dbo.cards.select(i,"sid").equals(sid)){
				String[][] q=dbo.getQuestions(dbo.cards.select(i,"cid"));
				String quest="";
				for(int j=0;j<q.length;j++)
					quest+="\\\\pard "+(j+1)+". "+q[j][3]+"\\\\par\r\n";
				filetxt+=append(dbo.cards.select(i,"cnum"),sname,quest,date,signer);
			}
		File file=new File(".\\export\\export.rtf");
		try{
			if(!file.exists())
				file.createNewFile();
			OutputStreamWriter out=new OutputStreamWriter(new FileOutputStream(file.getAbsoluteFile()),"cp1251");
			try{
				out.write(header+filetxt+footer);
				out.flush();
			}
			finally{
				out.close();
			}
		}
		catch(IOException e){
			throw new RuntimeException(e);
		}
	}
	private String append(String cnum,String sname,String questions,String date, String signer){
		String str=template.replaceAll("sssDATEeee",date);
		str=str.replaceAll("sssSNAMEeee",sname);
		str=str.replaceAll("sssQUESTIONSeee",questions);
		str=str.replaceAll("sssCIDeee",cnum);
		return str.replaceAll("sssSIGNEReee",signer);
	}
}
