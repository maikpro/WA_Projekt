package de.hsos.swa.artikelverwaltung.control;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.artikelverwaltung.entity.Artikel;

/**
 * Die Klasse ArtikelService
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */
@RequestScoped
public interface ArtikelService {
    public Optional<Artikel> getById(Long id);

    public Optional<Artikel> createArtikel(Artikel artikel);

    public Collection<Artikel> getLatestArtikel();

    public Collection<Artikel> getYourArtikel(String username);

    public Collection<Artikel> getVerkaufteArtikel(String username);

    public Optional<Artikel> updateArtikel(Artikel artikel);
}
