package com.mycompany.muayene.Forms.MainMenu.DenetimMenu;

import com.mycompany.muayene.Forms.IFrame;
import com.mycompany.muayene.Forms.PreLoadedFrame;
import com.mycompany.muayene.Model.Hasta;
import com.mycompany.muayene.Model.Ilac;
import com.mycompany.muayene.Model.Recete;
import com.mycompany.muayene.Forms.MainMenu.DenetimMenu.Receteler.IslenmisIlac;
import com.mycompany.muayene.Forms.MainMenu.DenetimMenu.Receteler.IslenmisRecete;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Mustafa
 */
public class RecetelerForm extends JPanel implements IFrame {

    
    private List<IslenmisRecete> islenmisReceteList;
    /**
     * Creates new form RecetelerForm
     */
    public RecetelerForm() {
        initComponents();
        islenmisReceteList = new ArrayList<>();
        geriButton.addActionListener(this::geriButtonActionPerformed);
        onaylaButton.addActionListener(this::onaylaButtonActionPerformed);
        silButton.addActionListener(this::silButtonActionPerformed);
        receteJList.addListSelectionListener(this::receteJListValueChanged);
    }
    
    
    @Override
    public void update() {
        islenmisReceteList.clear();
        for (Hasta hasta : Hasta.read()) {
            islenmisReceteList.add(new IslenmisRecete(hasta.getTani(), hasta.getRecete()));
        }
        IslenmisRecete[] islenmisRecete = new IslenmisRecete[islenmisReceteList.size()];
        islenmisReceteList.toArray(islenmisRecete);
        receteJList.setListData(islenmisRecete);

    }

    @Override
    public String getTitle(){ return "Reçete Kontrol Menüsü"; }

    private void geriButtonActionPerformed(ActionEvent actionEvent) {
        PreLoadedFrame.RECETELER_FRAME.setVisibility(false);
        PreLoadedFrame.DENETIM_FRAME.setVisibility(true);
    }

    private void onaylaButtonActionPerformed(ActionEvent actionEvent) {
        IslenmisRecete islenmisRecete = receteJList.getSelectedValue();
        if (islenmisRecete == null) return;
        Recete recete = islenmisRecete.getRecete();
        recete.setOnaylandi(!recete.onaylandi);
        Recete.update(recete);

        taniField.setText("");
        ilaclarComboBox.removeAllItems();
        update();
    }

    private void silButtonActionPerformed(ActionEvent actionEvent) {
        IslenmisRecete islenmisRecete = receteJList.getSelectedValue();
        if (islenmisRecete == null) return;
        Recete recete = islenmisRecete.getRecete();
        for (Hasta hasta : Hasta.read()) {
            if (Recete.readReceteNo(hasta.getRecete()).equals(Recete.readReceteNo(recete))) {
                Hasta.delete(hasta);
                islenmisReceteList.remove(islenmisRecete);
                break;
            }
        }
        taniField.setText("");
        ilaclarComboBox.removeAllItems();
        update();
    }

    private void receteJListValueChanged(ListSelectionEvent evt) {
        IslenmisRecete islenmisRecete = receteJList.getSelectedValue();
        if (islenmisRecete == null) return;
        taniField.setText(islenmisRecete.getTani());
        ilaclarComboBox.removeAllItems();
        for (Ilac ilac : islenmisRecete.getRecete().getIlaclar()) {
            ilaclarComboBox.addItem(new IslenmisIlac(ilac));
        }
        if (islenmisRecete.getRecete().onaylandi) {
            onaylaButton.setText("Onayı Kaldır");
        } else {
            onaylaButton.setText("Onayla");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        taniField = new javax.swing.JTextField();
        onaylaButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        silButton = new javax.swing.JButton();
        ilaclarComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        receteJList = new javax.swing.JList<>();
        geriButton = new javax.swing.JButton();

        jLabel1.setText("Tanı");

        onaylaButton.setText("Onayla");

        jLabel2.setText("İlaçlar");

        silButton.setText("Sil");

        jScrollPane1.setViewportView(receteJList);

        geriButton.setText("Geri");

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(ilaclarComboBox, 0, 397, Short.MAX_VALUE)
                            .addComponent(taniField))
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(onaylaButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panelLayout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(silButton, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE))))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(geriButton)))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(taniField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(onaylaButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(silButton)
                    .addComponent(ilaclarComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(geriButton)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    public JPanel getPanel() {
        return this.panel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton geriButton;
    private javax.swing.JComboBox<IslenmisIlac> ilaclarComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton onaylaButton;
    private javax.swing.JPanel panel;
    private javax.swing.JList<IslenmisRecete> receteJList;
    private javax.swing.JButton silButton;
    private javax.swing.JTextField taniField;
    // End of variables declaration//GEN-END:variables
}
