/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
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
public class AddNote extends JDialog {

    private JTextField note;
    private JButton ok;

    public AddNote(Window window, Core core) {
        this.setPreferredSize(new Dimension(350, 75));
        this.setLayout(new FlowLayout());
        this.note = new JTextField(20);
        this.ok = new JButton("OK");
        this.ok.addActionListener(new OKActionListener(window, core, this));
        this.note.addActionListener(new OKActionListener(window, core, this));

        this.add(note);
        this.add(ok);
        this.setTitle("Zadejte název nové poznámky");
        this.pack();
        Point p = getMousePosition();
        this.setLocationRelativeTo(window);
    }

    public String getNoteName() {
        return note.getText();
    }

    private static class OKActionListener implements ActionListener {

        private Window window;
        private Core core;
        private AddNote an;

        public OKActionListener(Window window, Core core, AddNote an) {
            this.window = window;
            this.core = core;
            this.an = an;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String newNote = an.getNoteName();
            System.out.println(newNote);
            try {
                String[] path = window.getActualPath();
                core.addNewNote(path[0], newNote, (newNote + "\n"));
            } catch (FileAlreadyExistsException ex) {
                Logger.getLogger(PlusListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            window.setFiles(core.getFiles(window.getActualPath()[0]));
            window.setSelectedFile(newNote);
            
            an.dispose();
        }
    }
}
