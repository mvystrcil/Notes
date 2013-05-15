/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author pokus
 */
public class TrayListener implements MouseListener {
    private JPopupMenu pop;
    private Window window;
    
    public TrayListener(Window window){
        this.window = window;
        this.pop = new JPopupMenu();
        JMenuItem item = new JMenuItem("Exit");
        item.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        pop.add(item);
        pop.pack();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point p = e.getLocationOnScreen();
        switch(e.getButton()){
            case 1:
                window.toogleVisibility();
                window.setActualCalendar();
                pop.setVisible(false);
                break;
            default:
                pop.setVisible(true);
                pop.setLocation(p.x, p.y);
                break;
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
}
