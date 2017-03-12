package documentManipulating;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

public class DocManipulating {

	public void fileExtract(LocalDate date, String address, String phone, String sender, String receiverAddress, String receiverPhone, String receiver) {
		Path pattern = Paths.get("C:\\Users\\alibaev\\workspace\\FirstHibernateAID2\\src\\main\\java\\documentManipulating\\Шаблон для AERP.docx");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File((pattern.toString())));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			XWPFDocument xwpfDocument = new XWPFDocument();
			FileOutputStream fos = new FileOutputStream((Paths.get("C:\\Users\\alibaev\\workspace\\FirstHibernateAID2\\src\\main\\java\\documentManipulating\\Шаблон для AERP1.docx").toString()));

			XWPFDocument xwpfDocument2 = new XWPFDocument(fis);
			for (XWPFParagraph paragraph : xwpfDocument2.getParagraphs()) {
				if (paragraph.getText().contains("Курьера направить date")) {
					for (int i = 0; i < paragraph.getRuns().size(); i++) {
						paragraph.removeRun(i);
					}
					XWPFRun runText = paragraph.createRun();
					runText.setText("Курьера направить ");
					runText.setFontSize(13);
					XWPFRun runDate = paragraph.createRun();
					runDate.setBold(true);
					runDate.setText(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
					runDate.setFontSize(13);
				}
			}
			ArrayList<String> list = new ArrayList<String>();
			for (XWPFTable table : xwpfDocument2.getTables()) {
				for (XWPFTableRow row : table.getRows()) {
					for (XWPFTableCell cell : row.getTableCells()) {
						for (int i = 0; i < cell.getParagraphs().size(); i++) {
							System.out.println(i + ": " + cell.getParagraphArray(i).getText());
							if (cell.getParagraphArray(i).getText().equals("address")) {
								cell.getParagraphArray(i).removeRun(0);
								cell.getParagraphArray(i).createRun().setText(address);
								cell.getParagraphArray(i).setAlignment(ParagraphAlignment.LEFT);
							}

							if (cell.getParagraphArray(i).getText().equals("phone")) {
								cell.getParagraphArray(i).removeRun(0);
								cell.getParagraphArray(i).createRun().setText(phone);
							}

							if (cell.getParagraphArray(i).getText().equals("sender")) {
								cell.getParagraphArray(i).removeRun(0);
								cell.getParagraphArray(i).createRun().setText(sender);
							}

							if (cell.getParagraphArray(i).getText().equals("receiverAddress")) {
								cell.getParagraphArray(i).removeRun(0);
								cell.getParagraphArray(i).createRun().setText(receiverAddress);
								cell.getParagraphArray(i).setAlignment(ParagraphAlignment.LEFT);
							}

							if (cell.getParagraphArray(i).getText().equals("receiverPhone")) {
								cell.getParagraphArray(i).removeRun(0);
								cell.getParagraphArray(i).createRun().setText(receiverPhone);
							}

							if (cell.getParagraphArray(i).getText().equals("receiver")) {
								cell.getParagraphArray(i).removeRun(0);
								cell.getParagraphArray(i).createRun().setText(receiver);
							}
						}
					}
				}
			}

			xwpfDocument2.write(fos);

			fos.close();
			// Desktop desktop = Desktop.getDesktop();
			// desktop.open((Paths.get(("D:\\Ринат\\РАБОТА\\! ПОЧТА\\EMS
			// почта\\Шаблон для AERP1.docx")).toFile()));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
