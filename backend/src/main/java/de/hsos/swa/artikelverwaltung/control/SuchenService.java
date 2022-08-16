package de.hsos.swa.artikelverwaltung.control;

import java.util.Collection;

import de.hsos.swa.artikelverwaltung.entity.Artikel;

/**
 * Die Klasse SuchenService
 *
 * @author Maik Proba
 * @version 1.0
 * @since 14-07-2022
 */
public interface SuchenService {
    public Collection<Artikel> getAll();

    public Collection<Artikel> getAllByLikeSearch(String suchwort);
}
