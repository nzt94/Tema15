package pack;

public class dbase {
	public table cards=new table("cards");
	public table subjects=new table("subjects");
	public table questions=new table("questions");
	private String[][] append(String[][] arr2,String[] arr){
		String[][] r=new String[arr2.length+1][];
		for(int j=0;j<arr2.length;j++)
			r[j]=arr2[j];
		r[arr2.length]=arr;
		return r;
	}
	public String[][] getQuestions(String cid){
		String[][] r=new String[0][];
		for(int i=0;i<questions.size();i++)
			if(questions.select(i,"cid").equals(cid))
				r=append(r,questions.getRow(i));
		return r;
	}
}
