
package com.jeanneboyarsky.records;

public class BookReader {

    public static void main(String[] args) {
        var book = new Book("Breaking and entering", 289);

        System.out.println(book.title());
        System.out.println(book.toString());

    }
}


