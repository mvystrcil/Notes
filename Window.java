/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author pokus
 */
public class Window extends JFrame {

    private JList<String> folders, files;
    JTextArea text, task;
    private JFrame frame;
    private static final String tit = "Poznámky: ";
    private static final String[] months = {"leden", "únor", "březen", "duben",
        "květen", "červen", "červenec", "srpen", "září", "říjen", "listopad", "prosinec"};
    private JPanel menu, bottom;
    private JButton save, add, remove, addSheet, next, prev;
    private JScrollPane scroll, scrollTask;
    private Core core;
    private DefaultListModel model, folderModel;
    private FileListListener listener;
    private JSplitPane split, splitText;
    private JFormattedTextField formatText;
    private Timer timer;
    private JPopupMenu popUp;
    private JPanel jp, buttons;
    private TableCalendar tc;
    private JLabel month;
    private Window w;

    public Window() {
        this.w = this;
        this.core = new Core();
        this.model = new DefaultListModel();
        this.setTitle("Poznámky");
        this.frame = new JFrame();
        this.frame.setTitle(tit);
        folderModel = new DefaultListModel();
        listOfFolders();
        this.folders = new JList<>(folderModel);
        this.folders.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.folders.setSize(50, 100);
        this.folders.setPrototypeCellValue("Může kratší.");
        this.folders.setSelectedIndex(0);
        this.folders.setFixedCellHeight(25);
        
        listOfFiles();
        this.files = new JList<>(model);
        this.files.updateUI();
        this.files.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        this.files.setSelectedIndex(0);
        this.files.setPrototypeCellValue("Alespoň 20 sloupečků-");
        this.files.setFixedCellHeight(20);
        listener = new FileListListener(this, core);
        this.folders.addListSelectionListener(listener);
        this.files.addListSelectionListener(new AddNoteListListener(this, core));
        this.split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, folders, files);
        //this.split.setOneTouchExpandable(true);
        Dimension minimumSize = new Dimension(0, 0);
        split.setMinimumSize(minimumSize);
        split.setMinimumSize(minimumSize);

//        this.jtp = new JTextPane();
//        this.jtp.setText("AAAA");

        this.text = new JTextArea(25, 35);
        this.text.setEditable(true);
        this.text.setEnabled(true);
        this.text.setWrapStyleWord(true);
        this.text.setLineWrap(true);
        this.text.setMinimumSize(minimumSize);
        this.text.setFont(new Font("Helvetica", 4, 12));

        this.popUp = new JPopupMenu("Pop-up");
        this.popUp.setLabel("Menu");
        this.popUp.add("Nová poznámka");
        this.popUp.add("Smazat poznámku");
        this.popUp.addSeparator();
        this.popUp.add("Přejmenovat");

        this.popUp.pack();
        this.files.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (e.getButton()) {
                    case 1:
                        System.out.println("Levé tlačítko");
                        popUp.setVisible(false);
                        break;

                    case 3:
                        Point p = e.getLocationOnScreen();
                        popUp.setLocation(p.x, p.y);
                        popUp.setVisible(true);
                        break;

                    default:
                        System.out.println("Něco jiného");
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
        });
        this.scroll = new JScrollPane();
        this.scroll.add(text);
        this.scroll.getViewport().add(text, null);

        this.splitText = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, split, scroll);
        splitText.setMinimumSize(minimumSize);
        splitText.setMinimumSize(minimumSize);

        this.menu = new JPanel(new FlowLayout(FlowLayout.LEFT));
        this.add = new JButton("+");
        this.add.addActionListener(new PlusListener(core, this));
        this.remove = new JButton("-");
        this.remove.addActionListener(new RemoveListener(this, core));
        this.save = new JButton("Uložit");
        final SaveListener sl = new SaveListener(this, core);
        this.save.addActionListener(sl);

        Action ack = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sl.actionPerformed(new ActionEvent(this, 10, "Save"));
            }
        };

        text.getInputMap().put(KeyStroke.getKeyStroke("control S"), "doSth");
        text.getActionMap().put("doSth", ack);

        this.addSheet = new JButton("Nová skupina");
        this.addSheet.addActionListener(new AddSheetListener(this, core));
        menu.add(addSheet);
        menu.add(add);
        menu.add(remove);
        menu.add(save);


        this.bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        this.buttons = new JPanel(new BorderLayout());
        this.next = new JButton(">");
        this.prev = new JButton("<");
        this.next.addActionListener(new NextMonthListener(this));
        this.prev.addActionListener(new PrevMonthListener(this));
        this.jp = new JPanel();
        tc = new TableCalendar(this);
        this.month = new JLabel(months[tc.getMonth()] + " " + tc.getYear());
        this.month.setHorizontalAlignment(JLabel.CENTER);
        
        
        try {
            UIManager.setLookAndFeel("com.easynth.lookandfeel.EaSynthLookAndFeel");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        this.month.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("Go home");
                w.setActualCalendar();
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
        });
        this.buttons.add(prev, BorderLayout.LINE_START);
        this.buttons.add(next, BorderLayout.LINE_END);
        this.buttons.add(month, BorderLayout.CENTER);
        
        
        this.jp.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.jp.setPreferredSize(new Dimension(300, 100));
        this.buttons.setPreferredSize(new Dimension(300, 25));
        this.jp.add(tc.getCalendar());
        this.jp.add(buttons);
        
        this.task = new JTextArea(14, 26); 
        task.setText(core.getTasks(tc.getSelectedDate()));
        this.scrollTask = new JScrollPane(task);
        
        this.jp.add(scrollTask);
        
        
        frame.getContentPane().setLayout(new BorderLayout(5, 2));
        frame.add(menu, BorderLayout.PAGE_START);
        frame.add(split, BorderLayout.LINE_START);
        //frame.add(files, BorderLayout.CENTER);
        frame.add(splitText, BorderLayout.CENTER);
        frame.add(jp, BorderLayout.LINE_END);
        frame.add(bottom, BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(HIDE_ON_CLOSE);
        
        this.setLocationRelativeTo(null);
        frame.setLocationRelativeTo(null);
        this.setFrameTitle();


        TrayIcon trayIcon = null;
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image im = Toolkit.getDefaultToolkit().getImage("/home/pokus/Notes/Ikona/ikona.png");
            trayIcon = new TrayIcon(im);
            trayIcon.addMouseListener(new TrayListener(this));
            try {
                tray.add(trayIcon);
            } catch (AWTException ex) {
                Logger.getLogger(Window.class.getName()).log(Level.SEVERE, null, ex);
            }

            tray.addPropertyChangeListener("Ahoj", null);
        }

        String[] path = getActualPath();
        String content = core.getNote(path[0], path[1]);
        text.setText(content);

        timer = new Timer(2000, sl);
        timer.setRepeats(true);
        timer.start();
    }

    private void listOfFiles() {
        String[] val = core.getFiles(null);
        model.clear();
        for (int i = 0; i < val.length; i++) {
            model.addElement(val[i]);
        }
        System.out.println("files");
    }

    private void listOfFolders() {
        String[] val = core.getFolders();
        folderModel.clear();
        for (int i = 0; i < val.length; i++) {
            folderModel.addElement(val[i]);
        }
    }

    public int getSelectedItem() {
        return folders.getSelectedIndex();
    }

    public void setFrameTitle() {
        frame.setTitle(tit + folders.getSelectedValue() + " - " + files.getSelectedValue());
    }

    public String[] getActualPath() {
        String folder = null, file;
        if (folders.getSelectedIndex() != 0) {
            folder = folders.getSelectedValue();
        }
        file = files.getSelectedValue();

        return new String[]{folder, file};
    }

    public String getText() {
        return text.getText();
    }

    public void setFiles(String[] filesArray) {
        model.clear();
        for (int i = 0; i < filesArray.length; i++) {
            model.add(i, filesArray[i]);
        }
        files.setSelectedIndex(0);
        //print(filesArray);
    }

    public void setFolders(String[] getFolders) {
        folderModel.clear();
        for (int i = 0; i < getFolders.length; i++) {
            folderModel.addElement(getFolders[i]);
        }
        folders.setSelectedIndex(0);
    }

    public void setTextArea(String loadText) {
        text.setText(loadText);
    }

    private void print(String[] str) {
        for (int i = 0; i < str.length; i++) {
            System.out.println(str[i]);
        }
    }

    public void setSelectedFolder(String select) {
        folders.setSelectedValue(select, true);
    }

    public void setSelectedFile(String select) {
        files.setSelectedValue(select, true);
    }

    public void setFocus() {
        this.text.requestFocusInWindow();
    }

    public void toogleVisibility() {
        if (this.frame.isVisible()) {
            this.frame.setVisible(false);
        } else {
            this.frame.setVisible(true);
            autoSaveStart();
        }
    }

    public void autoSaveStop() {
        timer.stop();
    }

    public void autoSaveStart() {
        timer.restart();
    }

    public boolean isFrameVisible() {
        return frame.isVisible() ? true : false;
    }
    
    public void nextCalendar(){
        jp.removeAll();
        jp.add(tc.getNextMonthCalendar());
        month.setText(months[tc.getMonth()] + " " + tc.getYear());
        jp.add(buttons);
        task.setText(core.getTasks(tc.getSelectedDate()));
        jp.add(scrollTask);
        jp.updateUI();
    }
    
    public void prevCalendar(){
        jp.removeAll();
        jp.add(tc.getPrevMonthCalendar());
        System.out.println("PrevCalendar " + tc.getMonth());
        month.setText(months[tc.getMonth()] + " " + tc.getYear());
        jp.add(buttons);
        task.setText(core.getTasks(tc.getSelectedDate()));
        jp.add(scrollTask);
        jp.updateUI();
    }
    
    public void setActualCalendar(){
        jp.removeAll();
        tc = new TableCalendar(this);
        jp.add(tc.getCalendar());
        System.out.println("ActualCalendar " + tc.getMonth());
        month.setText(months[tc.getMonth()] + " " + tc.getYear());
        jp.add(buttons);
        task.setText(core.getTasks(tc.getSelectedDate()));
        jp.add(scrollTask);
        jp.updateUI();        
    }
    
    public void setTaskText(){
        task.setText(core.getTasks(tc.getSelectedDate()));
    }
    
    public String getTaskPath(){
        return tc.getSelectedDate();
    }
    
    public String getTask(){
        return task.getText();
    }
    
    public void clearTask(){
        task.setText("");
    }
}
