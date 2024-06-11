package br.com.mateus.utils;

public class Util {

    public static double ConvertToDouble(String number) {
        if(number == null) return 0D;

        String internationalizedNumber = number.replaceAll(",", ".");

        if(IsNumeric(internationalizedNumber)) return Double.parseDouble(internationalizedNumber);
        return 0D;
    }

    public static boolean IsNumeric(String number) {
        if(number == null) return false;

        String internationalizedNumber = number.replaceAll(",", ".");

        return number.matches("[-+]?[0-9]*\\.?[0-9]+");
    }
}
