package de.hsos.swa.artikelverwaltung.control.acl;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;
import de.hsos.swa.warenkorb.boundary.rest.WarenkorbDTO;

@RequestScoped
public interface IWarenkorbZurSuche {
    public Optional<WarenkorbDTO> artikelHinzufuegen(Long warenkorbId, ArtikelDTO artikelDTO);
}
