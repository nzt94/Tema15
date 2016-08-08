package pack;
import java.io.*;

public class table {
	private String filePath="";
	public String[] titles;
	public String[][] rows=new String[0][];
	public table(String tbl) {
		filePath=".\\"+tbl+".csv";
		File file=new File(filePath);
		if(file.exists()){
			try{
				BufferedReader in=new BufferedReader(new FileReader(file.getAbsoluteFile()));
				try{
					String s;
					boolean b=false;
					while(s=in.readLine()!=null){
						if(b==false){
							titles=s.split(";");
							b=true;
						}
						String[][] r=new String[rows.length+1][];
						for(int i=0;i<rows.length;i++)
							r[i]=rows[i];
						r[rows.length]=s.split(";");
						rows=r;
					}
				}
		}
	}

}
