/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.FileAlreadyExistsException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author pokus
 */
public class PlusListener implements ActionListener{
    
    private Core core;
    private Window window;
    
    public PlusListener(Core core, Window window){
        this.core = core;
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        AddNote an = new AddNote(window, core);
        an.setVisible(true);        
    }
    
}
