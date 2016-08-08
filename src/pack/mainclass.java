package pack;

class mainclass {
	private static mainform form1 = new mainform();
	public static void main(String[] args) {
	}
	public static mainform getForm1() {
		return form1;
	}
	public static void setForm1(mainform form1) {
		mainclass.form1 = form1;
	}
}