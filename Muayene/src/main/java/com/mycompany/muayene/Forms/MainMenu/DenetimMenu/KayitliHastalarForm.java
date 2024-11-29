package com.mycompany.muayene.Forms.MainMenu.DenetimMenu;

import com.mycompany.muayene.Forms.IFrame;
import com.mycompany.muayene.Forms.PreLoadedFrame;
import com.mycompany.muayene.Model.Hasta;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;

/**
 *
 * @author Mustafa
 */
public class KayitliHastalarForm extends JPanel implements IFrame {

    
    private List<Hasta> hastaList = new ArrayList<>();
    /**
     * Creates new form KayitliHastalarForm
     */
    public KayitliHastalarForm() {
        initComponents();
        hastaJList.addListSelectionListener(this::onKisiJListValueChanged);
        geriButton.addActionListener(this::onGeriButtonActionPerformed);
        filitreleButton.addActionListener(this::onFiltreleButtonActionPerformed);
    }
    

    @Override
    public void update() {
        hastaList = Hasta.read();
        updateFromList();
    }

    private void updateFromList() {
        updateFromList(hastaList);
    }
    private void updateFromList(List<Hasta> filteredList) {
        String[] hastaStringArray = new String[filteredList.size()];
        for (int i = 0; i < filteredList.size(); i++) {
            Hasta hasta = filteredList.get(i);
            boolean isRepeatingValue = false;
            for (int j = i-1; j >= 0; j--) {
                if (hasta.getTcKimlikNo() == filteredList.get(j).getTcKimlikNo()) {
                    isRepeatingValue = true;
                    break;
                }
            }
            if (isRepeatingValue) continue;
            hastaStringArray[i] = hasta.getAd() + " " + hasta.getSoyad() + " TCKN:" + hasta.getTcKimlikNo();
        }
        hastaJList.setListData(hastaStringArray);
    }

    @Override
    public String getTitle() {
        return "Kayıtlı Hastalar";
    }

    private void onKisiJListValueChanged(ListSelectionEvent evt) {
        if (hastaJList.getSelectedValue() == null) return;
        String tcKimlikNo = hastaJList.getSelectedValue().toString().split(" TCKN:")[1];
        Hasta hasta = getHastaFromTcKimlikNo(Long.parseLong(tcKimlikNo));
        if (hasta == null) return;
        adField.setText(hasta.getAd());
        soyadField.setText(hasta.getSoyad());
        tcknField.setText(String.valueOf(hasta.getTcKimlikNo()));
    }

    private Hasta getHastaFromTcKimlikNo(long tcKimlikNo) {
        for (Hasta hasta : hastaList) {
            if (hasta.getTcKimlikNo() == tcKimlikNo) return hasta;
        }
        return null;
    }

    private void onGeriButtonActionPerformed(ActionEvent evt) {
        PreLoadedFrame.HASTALAR_FRAME.setVisibility(false);
        PreLoadedFrame.DENETIM_FRAME.setVisibility(true);
    }

    private void onFiltreleButtonActionPerformed(ActionEvent evt) {
        String ad = adField.getText();
        String soyad = soyadField.getText();
        String tckn = tcknField.getText();
        List<Hasta> filteredList = new ArrayList<>();
        for (Hasta hasta : hastaList) {
            if (ad.length() > 0 && !hasta.getAd().toLowerCase().contains(ad.toLowerCase())) continue;
            if (soyad.length() > 0 && !hasta.getSoyad().toLowerCase().contains(soyad.toLowerCase())) continue;
            if (tckn.length() > 0 && hasta.getTcKimlikNo() != Long.parseLong(tckn)) continue;
            filteredList.add(hasta);
        }
        updateFromList(filteredList);
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
        adField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        soyadField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        tcknField = new javax.swing.JTextField();
        filitreleButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        hastaJList = new javax.swing.JList<>();
        geriButton = new javax.swing.JButton();
        statusField = new javax.swing.JLabel();

        jLabel1.setText("Ad");

        jLabel2.setText("Soyad");

        jLabel3.setText("TCKN");

        filitreleButton.setText("Filitrele");

        jScrollPane1.setViewportView(hastaJList);

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
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(adField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                        .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(soyadField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLayout.createSequentialGroup()
                        .addComponent(statusField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(geriButton))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(panelLayout.createSequentialGroup()
                        .addComponent(tcknField, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(filitreleButton, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(adField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(soyadField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(tcknField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(filitreleButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(geriButton)
                    .addGroup(panelLayout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addComponent(statusField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
    private javax.swing.JTextField adField;
    private javax.swing.JButton filitreleButton;
    private javax.swing.JButton geriButton;
    private javax.swing.JList<String> hastaJList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel panel;
    private javax.swing.JTextField soyadField;
    private javax.swing.JLabel statusField;
    private javax.swing.JTextField tcknField;
    // End of variables declaration//GEN-END:variables
}
