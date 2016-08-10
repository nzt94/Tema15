package pack;

import java.awt.*;
import java.io.*;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class exportrtf {
	private String templatePath="";
	private String template="";
	private String header="{\\rtf1\\deflang1049{\\fonttbl{\\f0{Times New Roman}}}\r\n";
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
	public void export(int row){
		String filetxt="",sname="";
		String date=JOptionPane.showInputDialog("Укажите дату подписи");
		String signer=JOptionPane.showInputDialog("Укажите должность и имя подписанта");
		for(int i=0;i<dbo.subjects.size();i++)
			sname=(dbo.subjects.select(i,"sid").equals(sid))?dbo.subjects.select(i,"sname"):sname;
		for(int i=0;i<dbo.cards.size();i++){
			alert(dbo.cards.select(i,"sid")+"|"+sid);
			if(dbo.cards.select(i,"sid").equals(sid)){
				String[][] q=dbo.getQuestions(dbo.cards.select(i,"cid"));
				String quest="";
				for(int j=0;j<q.length;j++)
					quest+="\\\\pard "+encode_rtf((j+1)+". "+q[j][2])+"\\\\par\r\n";
				filetxt+=append(dbo.cards.select(i,"cnum"),sname,quest,date,signer);
			}
		}
		JFileChooser savedialoge=new JFileChooser();
		savedialoge.setDialogTitle("Экспорт файла");
		savedialoge.setFileFilter(new FileNameExtensionFilter("*.RTF","*.*"));
		savedialoge.setSelectedFile(new File("Билеты.rtf"));
		int ret=savedialoge.showSaveDialog(null);
		if(ret==JFileChooser.APPROVE_OPTION){ 
			File file=savedialoge.getSelectedFile();
			try{
				if(!file.exists())
					file.createNewFile();
				FileWriter out=new FileWriter(file.getAbsoluteFile(),false);
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
	}
	private String append(String cnum,String sname,String questions,String date, String signer){
		String str=template.replaceAll("sssDATEeee",encode_rtf(date));
		str=str.replaceAll("sssSNAMEeee",encode_rtf(sname));
		str=str.replaceAll("sssQUESTIONSeee",questions);
		str=str.replaceAll("sssCIDeee",encode_rtf(cnum));
		return str.replaceAll("sssSIGNEReee",encode_rtf(signer));
	}
	private String encode_rtf(String s){
		String s3="";
		try{
			byte[] arr=s.getBytes("cp1251");
			for(int i=0;i<arr.length;i++){
				int j=arr[i]>0?arr[i]:256+arr[i];
				s3+=((j<15)?"\\\\'0":"\\\\'")+Integer.toString(j, 16);
			}
		}
		catch(UnsupportedEncodingException e){
			s3=s;
		}
		return s3;
	}
}
