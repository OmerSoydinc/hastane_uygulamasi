package com.mycompany.muayene;

import com.mycompany.muayene.DBUtils.Mssql;
import com.mycompany.muayene.Forms.PreLoadedFrame;

/**
 *
 * @author Mustafa
 */
public class Hastane {
    public Hastane() {
        Mssql.makeFirstConnection();
        PreLoadedFrame.HBYS_FRAME.setVisibility(true);
    }
}
