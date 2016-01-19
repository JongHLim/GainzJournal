/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package my.gainzjournal;
import javax.swing.*;
import java.awt.FlowLayout;
import javax.swing.UIManager.*;

/**
 *
 * @author jhl2298
 */

public class AppMain {
    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            // If Nimbus is not available, you can set the GUI to another look and feel.
        }
        
        GainzJournalUI ui = new GainzJournalUI();
        ui.setVisible(true);
    }
}