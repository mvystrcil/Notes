/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;

/**
 *
 * @author pokus
 */
public class AddSheet extends JDialog{
    private JTextField sheet;
    private JButton ok;
    
    public AddSheet(Window window, Core core){
        this.setPreferredSize(new Dimension(350, 75));
        this.setLayout(new FlowLayout());
        
        this.sheet = new JTextField(20);
        this.ok = new JButton("OK");
        this.ok.addActionListener(new SheetOKActionListener(window, core, this));
        this.sheet.addActionListener(new SheetOKActionListener(window, core, this));
        
        this.add(sheet);
        this.add(ok);
        this.pack();
        this.setTitle("Zadejte název nové skupiny");
        this.setLocationRelativeTo(window);
    }
    
    public String getSheetName(){
        return sheet.getText();
    }

    private static class SheetOKActionListener implements ActionListener {
        private Window window;
        private Core core;
        private AddSheet as;
        
        public SheetOKActionListener(Window window, Core core, AddSheet as) {
            this.window = window;
            this.core = core;
            this.as = as;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String newSheet = as.getSheetName();
            System.out.println(newSheet);
            try {
                core.addNewSheet(newSheet);
            } catch (FileAlreadyExistsException ex) {
                Logger.getLogger(PlusListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.setFolders(core.getFolders());
            window.setSelectedFolder(newSheet);
            as.dispose();
        }
    }
}
