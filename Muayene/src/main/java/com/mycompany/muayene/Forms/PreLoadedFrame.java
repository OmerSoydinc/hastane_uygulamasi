package com.mycompany.muayene.Forms;

import com.mycompany.muayene.Forms.MainMenu.DenetimMenu.RecetelerForm;
import com.mycompany.muayene.Forms.MainMenu.DenetimMenu.KayitliHastalarForm;
import com.mycompany.muayene.Forms.MainMenu.IlaclarForm;
import com.mycompany.muayene.Forms.MainMenu.MuayeneMenu.ReceteOlusturForm;
import com.mycompany.muayene.Forms.MainMenu.DenetimForm;
import com.mycompany.muayene.Forms.MainMenu.MuayeneMenu.HastaOlusturForm;
import com.mycompany.muayene.Forms.MainMenu.MuayeneForm;
import com.mycompany.muayene.Forms.MainMenu.DenetimMenu.DoktorlarForm;
import javax.swing.JFrame;

/**
 *
 * @author Mustafa
 */
public enum PreLoadedFrame {
    HBYS_FRAME(new HBYSForm()),
    MUAYENE_FRAME(new MuayeneForm()),
    DENETIM_FRAME(new DenetimForm()),
    ILACLAR_FRAME(new IlaclarForm()),

    HASTA_OLUSTUR_FRAME(new HastaOlusturForm()),
    RECETE_OLUSTUR_FRAME(new ReceteOlusturForm()),

    DOKTORLAR_FRAME(new DoktorlarForm()),
    HASTALAR_FRAME(new KayitliHastalarForm()),
    RECETELER_FRAME(new RecetelerForm()),
    ;

    private final IFrame frame;
    public final JFrame jFrame;

    PreLoadedFrame(IFrame frame) {
        this.frame = frame;
        jFrame = new JFrame(frame.getTitle());
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setVisible(false);
        jFrame.setResizable(false);
        jFrame.setContentPane(frame.getPanel());
        jFrame.setSize(520, 520);
    }

    public void setVisibility(boolean visibility) {
        if (visibility) frame.update();
        jFrame.setVisible(visibility);
    }
}
