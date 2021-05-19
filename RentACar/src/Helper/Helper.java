package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	
	public static void optionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "�ptal");
		UIManager.put("OptionPane.noButtonText", "Hay�r");
		UIManager.put("OptionPane.okButtonText", "Tamam");
		UIManager.put("OptionPane.yesButtonText", "Evet");
	}
	
	public static void showMsg(String str) {
		String msg;
		optionPaneChangeButtonText();
		switch(str) {
		case "fill":
			msg = "L�tfen t�m alanlar� doldurunuz!";
			break;
		case "success":
			msg  = "��lem ba�ar�l�.";
			break;
		case "error":
			msg  = "Hatal� ��lem!";
			break;
		default:
			msg = str;
		}
		JOptionPane.showMessageDialog(null, msg, "Mesaj", JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static boolean confirm(String str) {
		String msg;
		optionPaneChangeButtonText();
		switch (str) {
		case "sure":
			msg = "Bu i�lemi ger�ekle�tirmek istiyor musunuz?";
			break;
		case "sure for exit":
			msg = "��k�� yapmak istedi�inize emin misiniz?";
			break;
		case "sure for cancel":
			msg = "�ptal etmek istedi�inize emin misiniz?";
			break;
		default:
			msg = str;
			break;
		}
		
		int rslt = JOptionPane.showConfirmDialog(null, msg, "Dikkat!", JOptionPane.YES_NO_OPTION);
		if(rslt == 0) {
			return true;
		}else {
			return false;
		}
	}
}