package pack;

import java.io.*;

class mainclass {
	private static mainform form1 = new mainform();
	private static exportrtf rtf = new exportrtf("template");
	public static void main(String[] args) {
		rtf.export("1");
	}
}