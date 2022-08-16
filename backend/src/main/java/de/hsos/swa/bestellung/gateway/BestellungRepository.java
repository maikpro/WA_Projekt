package de.hsos.swa.bestellung.gateway;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hsos.swa.bestellung.control.BestellungService;
import de.hsos.swa.bestellung.entity.Bestellaccount;
import de.hsos.swa.bestellung.entity.Bestellstatus;
import de.hsos.swa.bestellung.entity.Bestellung;
import de.hsos.swa.bestellung.entity.Bezahlmethode;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

@RequestScoped
@Transactional
public class BestellungRepository implements PanacheRepository<BestellungDTODB>, BestellungService {
    private static Logger LOG = Logger.getLogger(BestellungRepository.class);

    @Override
    public Optional<Bestellung> bestellungAnzeigen(Long id) {
        Optional<BestellungDTODB> nullableBestellungDTODB = findByIdOptional(id);
        if (nullableBestellungDTODB.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(BestellungDTODB.Converter.toBestellung(nullableBestellungDTODB.get()));
    }

    @Override
    public Optional<Bestellung> bestellungAnlegen(Bestellung bestellung) {
        BestellungDTODB bestellungDTODB = BestellungDTODB.Converter.toDTODB(bestellung);
        persist(bestellungDTODB);
        return Optional.of(BestellungDTODB.Converter.toBestellung(bestellungDTODB));
    }

    @Override
    public boolean bestellungLoeschen(Long id) {
        Optional<BestellungDTODB> nullableBestellungDTODB = findByIdOptional(id);
        if (nullableBestellungDTODB.isEmpty()) {
            return false;
        }
        delete(nullableBestellungDTODB.get());
        return true;
    }

    @Override
    public boolean bezahlmethodeFestlegen(Long id, Bezahlmethode bezahlmethode) {
        Optional<BestellungDTODB> nullableBestellungDTODB = findByIdOptional(id);
        if (nullableBestellungDTODB.isEmpty()) {
            return false;
        }
        nullableBestellungDTODB.get().bezahlmethode = bezahlmethode;
        return true;
    }

    @Override
    public boolean bestellstatusAendern(Long id, Bestellstatus bestellstatus) {
        Optional<BestellungDTODB> nullableBestellungDTODB = findByIdOptional(id);
        if (nullableBestellungDTODB.isEmpty()) {
            return false;
        }
        nullableBestellungDTODB.get().bestellstatus = bestellstatus;
        return true;
    }

    @Override
    public Optional<Bestellung> bestellungAbschliessen(Long id) {
        Optional<BestellungDTODB> nullableBestellungDTODB = findByIdOptional(id);
        if (nullableBestellungDTODB.isEmpty()) {
            return Optional.empty();
        }
        bestellstatusAendern(id, Bestellstatus.ABGESCHLOSSEN);

        return Optional.of(BestellungDTODB.Converter.toBestellung(nullableBestellungDTODB.get()));
    }

    @Override
    public Collection<Bestellung> kaeufeAnzeigen(Collection<Bestellaccount> bestellaccounts) {
        List<BestellungDTODB> bestellungDTODBs = new ArrayList<>();
        bestellaccounts.stream().forEach(ba -> bestellungDTODBs.add(find("kaeufer_id", ba.getId()).firstResult()));
        Collection<Bestellung> bestellungen = bestellungDTODBs.stream()
                .map(bdtodb -> BestellungDTODB.Converter.toBestellung(bdtodb)).toList();
        return bestellungen;
    }

}
