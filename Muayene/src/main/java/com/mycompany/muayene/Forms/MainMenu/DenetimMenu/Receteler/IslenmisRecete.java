/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.muayene.Forms.MainMenu.DenetimMenu.Receteler;

import com.mycompany.muayene.Model.Recete;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Mustafa
 */

@Getter
@AllArgsConstructor
public class IslenmisRecete {

    private String tani;
    private Recete recete;

    @Override
    public String toString() {
        return tani + " " + recete.getIlaclar().size() + " ilaç. "
                + recete.getReceteTarihi().toString() + " tarihinde yazıldı. (" + recete.isOnaylandi() + ")";
    }
}
