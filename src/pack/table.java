package pack;
import java.io.*;

public class table {
	private String filePath="";
	public String[] titles;
	public String[][] rows=new String[0][];
	public void insert(String[] sr){
		String[][] r=new String[rows.length+1][];
		for(int i=0;i<rows.length;i++)
			r[i]=rows[i];
		r[rows.length]=sr;
		rows=r;
	}
	public void delete(int row){
		if(row>0 && row<rows.length){
			String[][] r=new String[rows.length-1][];
			int i,j=0;
			for(i=0;i<rows.length;i++)
				if(i!=row)
					r[j++]=rows[i];
			rows=r;
		}
	}
	public table(String tbl) {
		filePath=".\\dbase\\"+tbl+".csv";
		File file=new File(filePath);
		if(file.exists()){
			try{
				BufferedReader in=new BufferedReader(new FileReader(file.getAbsoluteFile()));
				try{
					String s;
					if((s=in.readLine())!=null)
						titles=s.split(";");
					while((s=in.readLine())!=null)
						insert(s.split(";"));
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
	public int getIndex(String fld){
		int res=0;
		for(int i=0;i<titles.length;i++)
			if(titles[i].toLowerCase().equals(fld.toLowerCase()))
				res=i;
		return res;
	}
}
