package pack;

import java.io.*;
import java.text.NumberFormat;

import javax.swing.*;

public class export {
	private File templateFile = null;
	public boolean tmpLoaded = false;
	private String template = "";
	private String header = "{\\rtf1\\deflang1049{\\fonttbl{\\f0{Times New Roman}}}\r\n";
	private String footer = "\r\n}\r\n";
	private static dbase dbo = new dbase();

	public void save(int row) {
		if (tmpLoaded){
			String filetxt = "";
			for (int i = 0; i < dbo.cards.size(); i++) {
				if (dbo.cards.select(i, "sid").equals(dbo.subjects.select(row, "sid"))) {
					int k = 1;
					String quest = "";
					for (int j = 0; j < dbo.questions.size(); j++)
						if (dbo.questions.select(j, "cid").equals(dbo.cards.select(i, "cid")))
							quest += "\\\\pard " + encode_rtf((k++) + ". " + dbo.questions.select(j, "qtext"))
									+ "\\\\par\r\n";
					filetxt += append(dbo.cards.select(i, "cnum"), dbo.subjects.select(row, "sname"), quest);
				}
			}
			JFileChooser savedialoge = new JFileChooser();
			savedialoge.setDialogTitle("Экспорт файла");
			savedialoge.setSelectedFile(new File(dbo.subjects.select(row, "sname") + ".rtf"));
			int ret = savedialoge.showSaveDialog(null);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File file = savedialoge.getSelectedFile();
				try {
					if (!file.exists())
						file.createNewFile();
					FileWriter out = new FileWriter(file.getAbsoluteFile(), false);
					try {
						out.write(header + filetxt + footer);
						out.flush();
					} finally {
						out.close();
						JOptionPane.showMessageDialog(null,"Файл сохранен как \""+savedialoge.getSelectedFile().getAbsolutePath()+'"');
					}
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null,"При сохранении возникла ошибка: "+e.getMessage());
				}
			}
		}
		else
			JOptionPane.showMessageDialog(null,"Укажите шаблон для формирования файла");
	}
	public void fileSize(int row) {
		if (tmpLoaded){
			String filetxt = "";
			for (int i = 0; i < dbo.cards.size(); i++) {
				if (dbo.cards.select(i, "sid").equals(dbo.subjects.select(row, "sid"))) {
					int k = 1;
					String quest = "";
					for (int j = 0; j < dbo.questions.size(); j++)
						if (dbo.questions.select(j, "cid").equals(dbo.cards.select(i, "cid")))
							quest += "\\\\pard " + encode_rtf((k++) + ". " + dbo.questions.select(j, "qtext"))
									+ "\\\\par\r\n";
					filetxt += append(dbo.cards.select(i, "cnum"), dbo.subjects.select(row, "sname"), quest);
				}
			}
			NumberFormat nf = NumberFormat.getInstance();
	        nf.setMaximumFractionDigits(2);
			String size=nf.format(((float)(header + filetxt + footer).length())/1024);
			JOptionPane.showMessageDialog(null,"Предполагаемый размер файла: "+size+" КБ.");
		}
		else
			JOptionPane.showMessageDialog(null,"Укажите шаблон для формирования файла");
	}

	private String append(String cnum, String sname, String questions) {
		String str = template.replaceAll("sssSNAMEeee", encode_rtf(sname));
		str = str.replaceAll("sssQUESTIONSeee", questions);
		return str.replaceAll("sssCNUMeee", encode_rtf(cnum));
	}

	private String encode_rtf(String s) {
		String s3 = "";
		try {
			byte[] arr = s.getBytes();
			for (int i = 0; i < arr.length; i++) {
				int j = arr[i] > 0 ? arr[i] : 256 + arr[i];
				s3 += ((j < 16) ? "\\\\'0" : "\\\\'") + Integer.toString(j, 16);
			}
		} catch (Exception e) {
			s3 = s;
		}
		return s3;
	}

	public void addTemplate() {
		JFileChooser opendialoge = new JFileChooser();
		opendialoge.setDialogTitle("Укажите файл шаблона");
		int ret = opendialoge.showOpenDialog(null);
		if (ret == JFileChooser.APPROVE_OPTION)
			templateFile = opendialoge.getSelectedFile();
		if (templateFile.exists()) {
			try {
				BufferedReader in = new BufferedReader(new FileReader(templateFile.getAbsoluteFile()));
				try {
					template = "";
					String s = "", sl = "";
					int i = 0;
					while ((sl = in.readLine()) != null)
						s += sl + "\r\n";
					while (s.indexOf("\\par", i) >= 0)
						i = s.indexOf("\\par", i) + 4;
					template = s.substring(s.indexOf("\\pard"), i);
					header = s.substring(0,s.indexOf("\\pard"));
					footer = s.substring(i,s.length());
					tmpLoaded = true;
				} finally {
					in.close();
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}
