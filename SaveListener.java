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
public class SaveListener implements ActionListener {
    private Core core;
    private Window window;
    
    public SaveListener(Window window, Core core){
        this.core = core;
        this.window = window;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String[] path = window.getActualPath();
        try {
            core.addNewNote(path[0], path[1], window.getText());
            core.addTasks(window.getTaskPath(), window.getTask());
        } catch (FileAlreadyExistsException ex) {
            Logger.getLogger(SaveListener.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(!window.isFrameVisible()){
            window.autoSaveStop();
        }
    }
    
}
