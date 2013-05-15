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
public class AddSheetListener implements ActionListener{
    private Window window;
    private Core core;

    public AddSheetListener(Window window, Core core) {
        this.window = window;
        this.core = core;
    }    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        AddSheet as = new AddSheet(window, core);
        as.setVisible(true);
    }
    
}
