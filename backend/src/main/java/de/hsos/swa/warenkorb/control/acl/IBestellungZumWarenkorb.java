package de.hsos.swa.warenkorb.control.acl;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.warenkorb.boundary.rest.WarenkorbDTO;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

@RequestScoped
public interface IBestellungZumWarenkorb {
    public boolean warenKorbZurKasse(WarenkorbDTO warenkorbDTO);
}
