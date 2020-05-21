package app;


import menu.Menu;

import java.text.ParseException;

public class Application {
    public static void main(String[] args) throws ParseException {
        Menu menu = new Menu();
        menu.start();
    }
}
