package ghc;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
	public static void main(String[] args) {
		System.out.println(new SimpleDateFormat("dd-MM-yyyy").format(new Date())); 
	}
}
