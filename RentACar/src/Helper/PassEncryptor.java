package Helper;

import java.util.Base64;
import java.util.Scanner;

class PassEncryptor {
    public static void main(String[] args) {
    	try (Scanner pass = new Scanner(System.in)) {
			System.out.print("Kriptolamak istediğiniz şifreyi girin: ");

			String password = pass.nextLine();
			
			String cryptedPassword = Base64.getEncoder().encodeToString(password.getBytes());
			System.out.println("Şifrenizin kriptolanmış hali: " + cryptedPassword);
		}
    }
}