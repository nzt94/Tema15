package pack;
import java.io.*;

public class table {
	private String filePath="";
	private String[] titles;
	private String[][] rows=new String[0][];

	public int size(){
		return rows.length;
	}
	public String select(int row,String fld){
		return (row>=0 && row<rows.length)?rows[row][getIndex(fld)]:"";
	}
	public String[] getRow(int row){
		return (row>=0 && row<rows.length)?rows[row]:null;
	}
	public void insert(String[] sr){
		String[][] r=new String[rows.length+1][];
		for(int i=0;i<rows.length;i++)
			r[i]=rows[i];
		r[rows.length]=sr;
		rows=r;
	}
	public void update(String val,String fld,String[] sr){
		int j=-1;
		for(int i=0;i<rows.length;i++)
			if(rows[i][getIndex(fld)].equals(val))
				j=i;
		if(j>=0)
			rows[j]=sr;
	}
	public void delete(int row){
		if(row>=0 && row<rows.length){
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
	public void save(){
		File file=new File(filePath);
		if(file.exists()){
			try{
				FileWriter out=new FileWriter(file.getAbsoluteFile(),false);
				try{
					String text="";
					for(int i=0;i<titles.length;i++)
						text+=titles[i]+((i+1<titles.length)?";":"");
					text+="\r\n";
					for(int i=0;i<rows.length;i++){
						for(int j=0;j<rows[i].length;j++)
							text+=rows[i][j]+((j+1<rows[i].length)?";":"");
						text+="\r\n";
					}
					out.write(text);
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
	public int getIndex(String fld){
		int res=0;
		for(int i=0;i<titles.length;i++)
			if(titles[i].toLowerCase().equals(fld.toLowerCase()))
				res=i;
		return res;
	}
}