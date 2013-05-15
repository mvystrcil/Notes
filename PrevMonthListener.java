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
public class PrevMonthListener implements ActionListener{

    private Window w;
    
    public PrevMonthListener(Window w){
        this.w = w;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        w.clearTask();
        w.prevCalendar();
    }
    
}
