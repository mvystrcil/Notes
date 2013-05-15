/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Notes;

import java.awt.Dimension;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/**
 *
 * @author pokus
 */
public class TableCalendar {
    
    private JTable table;
    private MyCalendar mc;
    private JScrollPane scroll;
    private int acMonth, acYear;
    private String[] names = {"Po", "Út", "St", "Čt", "Pá", "So", "Ne"};
    private Window w;
    private int prevMonth;
    
    public TableCalendar(final Window w){
        this.w = w;
        mc = new MyCalendar();
        acMonth = mc.getMonth();
        acYear = mc.getYear();  
        
        table = new JTable(mc.getMonth(acYear, acMonth), names);
        prevMonth = acMonth;
        table.setEnabled(true);
        table.setShowGrid(false);
        table.setCellSelectionEnabled(true);
        colorTable(table, mc.getDay());
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(200, 120));
        
        table.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                w.clearTask();
                w.setTaskText();
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
    }
    
    public JScrollPane getCalendar(){
        return scroll;
    }
    
    public JScrollPane getNextMonthCalendar(){
        acMonth = (acMonth + 1) % 12;
        table = new JTable(mc.getMonth(acYear, acMonth), names);
        table.setEnabled(true);
        table.setShowGrid(false);
        table.setCellSelectionEnabled(true);
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(200, 120));
        
        colorTable(table, mc.getDay());
        
        return scroll;
    }
    
    public int getMonth(){
        int tmp = acMonth - 1;
        while(tmp < 0){
            tmp += 12;
        }
        if(prevMonth == 11 && tmp == 0){
            acYear--;
        } else if(prevMonth == 0 && tmp == 11){
            acYear++;
        }
        return (tmp % 12);
    }
    
    public int getYear(){
        return acYear;
    }
    
    public JScrollPane getPrevMonthCalendar(){
        acMonth = (acMonth - 1) % 12;
        table = new JTable(mc.getMonth(acYear, acMonth), names);
        table.setEnabled(true);
        table.setShowGrid(false);
        table.setCellSelectionEnabled(true);
        scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(200, 120));
        
        colorTable(table, mc.getDay());
        
        return scroll;
    }
    
    public final void colorTable(JTable table, int today){
        Object o = new Integer(today);
        for (int i = 0; i < table.getRowCount(); i++) {
            for (int j = 0; j < table.getColumnCount(); j++) {
                if(table.getValueAt(i, j) != null && table.getValueAt(i, j).equals(o)){
                    table.changeSelection(i, j, false, false);
                    return;
                }
            }
        }
    }
    
    public String getSelectedDate(){
        int col = table.getSelectedColumn();
        int row = table.getSelectedRow();
        StringBuilder sb = new StringBuilder();
        sb.append(String.valueOf(table.getValueAt(row, col)));
        sb.append(".");
        sb.append(String.valueOf(getMonth()));
        sb.append(".");
        sb.append(String.valueOf(getYear()));      
        
        return sb.toString();
    }
    
}
