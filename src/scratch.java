import java.util.Scanner;

import static java.lang.Character.isDigit;

public class scratch {
    public static void main(String[] args) {
        System.out.println("Type a double:");
        Scanner in = new Scanner(System.in);
        String xdouble = in.next();
        System.out.println(pullOutDouble(xdouble));
        System.out.println(xdouble);

    }
    private static double pullOutDouble(String string) {
        String temp= "";
        for(int i = 0; i<string.length(); i++) {
            if (isDigit(string.charAt(i)) || string.charAt(i)=='.') {
                temp= temp + string.charAt(i);
            }
        }
        return Double.parseDouble(temp);
    }
}
