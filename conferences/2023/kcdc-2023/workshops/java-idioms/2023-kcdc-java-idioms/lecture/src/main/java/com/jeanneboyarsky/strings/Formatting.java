package com.jeanneboyarsky.strings;

public class Formatting {

   public void printPi() {
       double pi = 3.14159265359;
       System.out.format("%s is %3.2f to the nearest %d digits",
               "Pi", pi, 2);
   }

    public String justFormat() {
        var pi = 3.14159265359;
        var formatString = "%s is %3.2f to the nearest %d digits";
        return formatString.formatted("Pi", pi, 2);
    }
}
