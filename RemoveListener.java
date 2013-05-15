/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author pokus
 */
public class RemoveListener implements ActionListener{
    Window window;
    Core core;

    public RemoveListener(Window window, Core core) {
        this.window = window;
        this.core = core;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String[] path = window.getActualPath();
        String[] files = core.getFiles(path[0]);
        core.removeNote(path[0], path[1]);
        System.out.println("Remove: " + path[0] + "/" + path[1]);
        window.setFiles(core.getFiles(path[0]));
        window.setFocus();
        if(files.length == 0){
            window.setFolders(core.getFolders());
        }
    }
    
}
