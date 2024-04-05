package org.example.util;

import java.util.Scanner;

// Util class'indan obje olusturulmasinin onune gecmek icin de abstract class olarak tanimladim.
public abstract class Util {
    public static Scanner scanner = new Scanner(System.in);
    public static String stringScanner(String input){
        System.out.print(input);
        return scanner.nextLine();
    }

    public static int intScanner(String input){
        System.out.print(input);
        int inp = scanner.nextInt();
        scanner.nextLine();
        return inp;
    }

    public static Long longScanner(String input){
        System.out.print(input);
        Long inp = scanner.nextLong();
        scanner.nextLine();
        return inp;
    }

    public static double doubleScanner(String input){
        System.out.print(input);
        double inp = scanner.nextDouble();
        scanner.nextLine();
        return inp;
    }

    public static int printMenuHeader(String header, int headerWidth){
        headerWidth = (headerWidth<header.length()) ? header.length() : headerWidth - header.length();
        String styledHeader = "%s %s %s\n".formatted("-".repeat(headerWidth/2), header, "-".repeat(headerWidth/2));
        System.out.print(styledHeader);
        return styledHeader.length()%2 != 0 ? styledHeader.length() : styledHeader.length()-1 ;
    }


}
