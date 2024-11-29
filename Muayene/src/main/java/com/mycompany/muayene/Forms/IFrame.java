package com.mycompany.muayene.Forms;

import javax.swing.JPanel;

/**
 *
 * @author Mustafa
 */
public interface IFrame {
    
    JPanel getPanel();
    
    default void update(){};

    default String getTitle(){ return "HBYS"; }
}
