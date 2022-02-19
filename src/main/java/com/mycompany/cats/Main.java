package com.mycompany.cats;

import java.io.IOException;
import javax.swing.JOptionPane;
/**
 *
 * @author jose
 */
public class Main {
    public static void main(String[] args) {
        String[] options = {"See cat", "Exit"};
        int option = JOptionPane.showOptionDialog(
            null,
            "Select an option",
            "",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.INFORMATION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (option == 0) {
            try {
                CatService.getCat();
            } catch(IOException e) {
                System.out.println(e);
            }
        }

        if (option == 1) {
            System.out.println("Good bye");
        }
    }
}
