/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author pokus
 */
public class Core {

    File rootFolder, taskFolder;
    String separator = File.separator;
    String root, taskRoot;

    public Core() {
        File f = FileSystemView.getFileSystemView().getHomeDirectory();
        root = f.getAbsolutePath() + separator + "Notes" + separator + "Notes";
        taskRoot = f.getAbsolutePath() + separator + "Notes" + separator + "Tasks";
        System.out.println("root: " + root);
        rootFolder = new File(root);
        taskFolder = new File(taskRoot);

        if (!rootFolder.exists()) {
            rootFolder.mkdirs();
        }

        if (!taskFolder.exists()) {
            taskFolder.mkdirs();
        }

        if (rootFolder.list().length == 0) {
            try {
                addNewNote(null, "Uvítání", "Vítej\nnový uživateli\n");
            } catch (FileAlreadyExistsException ex) {
                Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean addNewSheet(String sheet) throws FileAlreadyExistsException {
        File newSheet;
        if (sheet == null) {
            newSheet = new File(root + "");
        } else {
            newSheet = new File(root + separator + sheet);
        }

        if (newSheet.exists()) {
            return true;
        }

        newSheet.mkdir();
        return true;
    }

    public boolean addNewNote(String sheet, String note, String text) throws FileAlreadyExistsException {
        File newNote = null;
        //System.out.println("Add new note " + sheet + "/" + note);
        if (note == null) {
            return true;
        } else if (sheet == null) {
            newNote = new File(root + separator + note);
        } else {
            newNote = new File(root + separator + sheet + separator + note);
        }

        if (newNote.exists()) {
            newNote.delete();
        }
        try {
            newNote.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.toString());
        }
        try {
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(newNote)));
            bw.write(text, 0, text.length());
            bw.flush();
            bw.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.gc();
        return true;
    }

    public boolean removeNote(String sheet, String note) {
        File text = null;
        if (sheet == null) {
            text = new File(root + separator + note);
        } else if (note == null) {
            text = new File(root + separator + sheet);
        } else {
            text = new File(root + separator + sheet + separator + note);
        }

        System.out.println("remove: " + text);

        if (text.exists()) {
            return text.delete();
        }
        System.exit(1);
        System.err.println("remove " + text);
        return false;
    }

    public String getNote(String sheet, String note) {
        File text = null;
        if (note == null) {
            return "";
        } else if (sheet == null) {
            text = new File(root + separator + note);
        } else {
            text = new File(root + separator + sheet + separator + note);
        }

        StringBuilder sb = new StringBuilder();
        String s;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(text)));
            while ((s = br.readLine()) != null) {
                sb.append(s);
                sb.append("\n");
            }
            br.close();
        } catch (FileNotFoundException ex) {
            //Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("Note " + ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println(ex.toString());
        }
        System.gc();
        return sb.toString();
    }

    public String[] getFolders() {
        ArrayList<String> folders = new ArrayList<>();
        File[] content = rootFolder.listFiles();
        folders.add("Výchozí");
        for (int i = 0; i < content.length; i++) {
            if (content[i].isDirectory()) {
                folders.add(content[i].getName());
            }
        }
        return listToString(folders);
    }

    public String[] getFiles(String folder) {
        ArrayList<String> files = new ArrayList<>();
        File f = rootFolder;
        if (folder != null) {
            f = new File(root + separator + folder);
        }
        File[] content = f.listFiles();

        if (content != null) {
            for (int i = 0; i < content.length; i++) {
                if (content[i].isFile()) {
                    files.add(content[i].getName());
                }
            }
        }
        return listToString(files);
    }

    public String[] listToString(ArrayList<String> al) {
        String array[] = new String[al.size()];
        return al.toArray(array);
    }

    public String getTasks(String date) {
        File f = new File(taskRoot + separator + date + separator + date);
        if(!f.exists()){
            return null;
        }
        StringBuilder sb = new StringBuilder();
        String line;
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));

            while ((line = br.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("File not found " + ex.toString());
        } catch (IOException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("IO Exception " + ex.toString());
        } finally {
            try {
                br.close();
            } catch (IOException ex) {
                Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return sb.toString();
    }

    public boolean addTasks(String date, String text) {
        if(text.length() == 0){
            removeTasks(date);
            return true;
        }
        File f = new File(taskRoot + separator + date + separator);
        BufferedWriter bw;
        if (!f.exists()) {
            f.mkdirs();
        }
        f = new File(taskRoot + separator + date + separator + date);
        try {
            f.createNewFile();
        } catch (IOException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
            System.err.println("IO Exception " + ex.toString());
            return false;
        }
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
            bw.write(text, 0, text.length());
            bw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Core.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }
    
    /**
     * Pozor pokud není složka prázdná, pak nelze vymazat
     */
    public boolean removeTasks(String date){
        File f = new File(taskRoot + separator + date + separator + date);
        
        if(f.exists()){
            f.delete();
        } else {
            return true;
        }
        
        f = new File(taskRoot + separator + date);
        
        if(f.exists()){
            return f.delete();
        }
        
        return false;
    }
}
