package pack;

import java.io.*;

public class exportrtf {
	private String templatePath="";
	private String template="";
	private String header="{\\rtf1\\ansi\\ansicpg1251\\deff0\\nouicompat\\deflang1049{\\fonttbl{\\f0\\fnil\\fcharset204{\\*\\fname Times New Roman;}Times New Roman CYR;}{\\f1\\fnil\\fcharset1 Segoe UI Symbol;}{\\f2\\fnil\\fcharset0 Times New Roman;}}\\viewkind4\\uc1\r\n";
	private String footer="\r\n}\r\n";
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
}
