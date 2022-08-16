package de.hsos.swa.bestellung.gateway;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import de.hsos.swa.bestellung.control.BestellartikelService;
import de.hsos.swa.bestellung.entity.Bestellartikel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 29-07-2022
 */

@RequestScoped
@Transactional
public class BestellartikelRepository implements PanacheRepository<BestellartikelDTODB>, BestellartikelService {

    @Override
    public Optional<Bestellartikel> createBestellartikel(Bestellartikel bestellartikel) {
        BestellartikelDTODB bestellartikelDTODB = BestellartikelDTODB.Converter.toBestellartikelDTODB(bestellartikel);
        persist(bestellartikelDTODB);
        return Optional.of(BestellartikelDTODB.Converter.toBestellartikel(bestellartikelDTODB));
    }

}
