package de.hsos.swa.bestellung.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.bestellung.entity.Bestellartikel;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 29-07-2022
 */

@RequestScoped
public interface BestellartikelService {
    public Optional<Bestellartikel> createBestellartikel(Bestellartikel bestellartikel);
}
