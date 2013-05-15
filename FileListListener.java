/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author pokus
 */
public class FileListListener implements ListSelectionListener {

    private Window window;
    private Core core;
    private JList<String> list;
    int i = 0;

    public FileListListener(Window w, Core core) {
        this.window = w;
        this.core = core;
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            window.setFrameTitle();
            String[] path = window.getActualPath();
            window.setFiles(core.getFiles(path[0]));
            window.requestFocusInWindow();
        }
    }
}
