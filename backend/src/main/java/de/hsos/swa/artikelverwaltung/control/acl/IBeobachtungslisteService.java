package de.hsos.swa.artikelverwaltung.control.acl;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;

/**
 * Das Interface IBeobachtungslisteService für die
 * Beobachtungsliste.
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */
@RequestScoped
public interface IBeobachtungslisteService {
    public Optional<ArtikelDTO> artikelHinzufuegen(ArtikelDTO artikelDTO);
}
