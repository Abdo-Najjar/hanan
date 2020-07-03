/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import java.awt.Dimension;
import static java.awt.Frame.NORMAL;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author abdo
 */
public class SharedInterfaceMethods {

    public static void addWindowListener(JFrame frame) {
        frame.setDefaultCloseOperation(NORMAL);
        frame.setResizable(false);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                String ObjButtons[] = {"Yes", "No"};
                int PromptResult = JOptionPane.showOptionDialog(null,
                        "Are you sure you want to exit?", "Exit",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        ObjButtons, ObjButtons[1]);
                if (PromptResult == 0) {
                    System.exit(0);
                }
            }
        });
    }

    public static boolean isEmptyFields(JFrame f, JTextField... fields) {
        for (JTextField tf : fields) {
            if (tf.getText().equalsIgnoreCase("")) {
                JOptionPane.showMessageDialog(f, "Empty Fields", "Something is messing", JOptionPane.ERROR_MESSAGE);
                return true;
            }
        }
        return false;
    }

    public static void emptyFields(JTextField... fields) {
        for (JTextField tf : fields) {
            tf.setText("");
        }
    }
}
