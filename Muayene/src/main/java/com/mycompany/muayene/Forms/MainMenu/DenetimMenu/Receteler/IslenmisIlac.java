/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.muayene.Forms.MainMenu.DenetimMenu.Receteler;

import com.mycompany.muayene.Model.Ilac;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 *
 * @author Mustafa
 */
@Getter
@AllArgsConstructor
public class IslenmisIlac {

    private Ilac ilac;

    @Override
    public String toString() {
        return ilac.getIlacAdi();
    }
}
