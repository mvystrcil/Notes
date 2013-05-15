/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author pokus
 */
public class AddNoteListListener implements ListSelectionListener{
    private Window window;
    private Core core;
    
    public AddNoteListListener(Window window, Core core){
        this.window = window;
        this.core = core;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        String[] path = window.getActualPath();
        window.setFrameTitle();
        window.setTextArea(core.getNote(path[0], path[1]));
        window.setFocus();
    }
    
}
