package Helper;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Helper {
	
	public static void optionPaneChangeButtonText() {
		UIManager.put("OptionPane.cancelButtonText", "Ýptal");
		UIManager.put("OptionPane.noButtonText", "Hayýr");
		UIManager.put("OptionPane.okButtonText", "Tamam");
		UIManager.put("OptionPane.yesButtonText", "Evet");
	}
	
	public static void showMsg(String str) {
		String msg;
		optionPaneChangeButtonText();
		switch(str) {
		case "fill":
			msg = "Lütfen tüm alanlarý doldurunuz!";
			break;
		case "success":
			msg  = "Ýþlem baþarýlý.";
			break;
		case "error":
			msg  = "Hatalý Ýþlem!";
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
			msg = "Bu iþlemi gerçekleþtirmek istiyor musunuz?";
			break;
		case "sure for exit":
			msg = "Çýkýþ yapmak istediðinize emin misiniz?";
			break;
		case "sure for cancel":
			msg = "Ýptal etmek istediðinize emin misiniz?";
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