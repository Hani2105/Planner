/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.nner;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.event.MouseInputAdapter;

/**
 *
 * @author krisztian_csekme1
 */
public class TabReorderHandler extends MouseInputAdapter {

    public static void enableReordering(JTabbedPane pane) {
        TabReorderHandler handler = new TabReorderHandler(pane);
        pane.addMouseListener(handler);
        pane.addMouseMotionListener(handler);
    }

    private JTabbedPane tabPane;
    private int draggedTabIndex;

    protected TabReorderHandler(JTabbedPane pane) {
        this.tabPane = pane;
        draggedTabIndex = -1;
    }

    
    public void mousePressed(MouseEvent e) {
        draggedTabIndex = tabPane.getUI().tabForCoordinate(tabPane, 
e.getX(), e.getY()); 
    }
    public void mouseReleased(MouseEvent e) {
        draggedTabIndex = -1;
        
        List<Plan> plans = new ArrayList<>();
        
        for (int i=0; i<MainForm.TOP.getTabCount(); i++){
            String name = MainForm.TOP.getComponentAt(i).getName();
            for (int j=0; j<PlNner.PLANS.size(); j++){
                if (PlNner.PLANS.get(j).getName().equals(name)){
                    plans.add(PlNner.PLANS.get(j));
                }
            }
        }
        
        PlNner.PLANS = plans;
        PlNner.CP.tick();
        
        
        
        
    }

    public void mouseDragged(MouseEvent e) {
        if (draggedTabIndex == -1) {
            return;
        }

        int targetTabIndex = tabPane.getUI().tabForCoordinate(tabPane,
                e.getX(), e.getY());
        
        if (targetTabIndex != -1 && targetTabIndex != draggedTabIndex) {
            boolean isForwardDrag = targetTabIndex > draggedTabIndex;
            tabPane.insertTab(tabPane.getTitleAt(draggedTabIndex),
                    tabPane.getIconAt(draggedTabIndex),
                    tabPane.getComponentAt(draggedTabIndex),
                    tabPane.getToolTipTextAt(draggedTabIndex),
                    isForwardDrag ? targetTabIndex + 1 : targetTabIndex);
            
            
            draggedTabIndex = targetTabIndex;
            
           // tabPane.setSelectedIndex(draggedTabIndex);
            
        }
    }
}
