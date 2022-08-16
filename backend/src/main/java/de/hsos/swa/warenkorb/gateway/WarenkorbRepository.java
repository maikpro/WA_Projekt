package de.hsos.swa.warenkorb.gateway;

import java.util.ArrayList;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import de.hsos.swa.warenkorb.control.WarenkorbService;
import de.hsos.swa.warenkorb.entity.Warenkorb;
import de.hsos.swa.warenkorb.entity.Warenkorbartikel;
import de.hsos.swa.warenkorb.entity.Warenkorbposten;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

@RequestScoped
@Transactional
public class WarenkorbRepository implements PanacheRepository<WarenkorbDTODB>, WarenkorbService {
    private static final Logger LOG = Logger.getLogger(WarenkorbRepository.class);

    @Override
    public Optional<Warenkorb> warenkorbAnzeigen(Long id) {
        Optional<WarenkorbDTODB> nullableWarenkorbDTODB = findByIdOptional(id);
        if (nullableWarenkorbDTODB.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(WarenkorbDTODB.Converter.toWarenkorb(nullableWarenkorbDTODB.get()));
    }

    @Override
    public Optional<Warenkorb> warenkorbAnlegen() {
        Warenkorb warenkorb = new Warenkorb.Builder()
                .warenkorbpostenList(new ArrayList<Warenkorbposten>())
                .build();

        WarenkorbDTODB warenkorbDTODB = WarenkorbDTODB.Converter.toWarenkorbDTODB(warenkorb);
        persist(warenkorbDTODB);
        return Optional.of(WarenkorbDTODB.Converter.toWarenkorb(warenkorbDTODB));
    }

    @Override
    public boolean warenkorbLoeschen(Long id) {
        Optional<WarenkorbDTODB> nullableWarenkorbDTODB = findByIdOptional(id);
        if (nullableWarenkorbDTODB.isEmpty()) {
            return false;
        }
        delete(nullableWarenkorbDTODB.get());
        return true;
    }

    @Override
    public Optional<Warenkorb> artikelInWarenkorbLegen(Long warenkorbId, Warenkorbartikel warenkorbartikel) {
        // suche warenkorb
        Optional<WarenkorbDTODB> nullableWarenkorbDTODB = findByIdOptional(warenkorbId);
        if (nullableWarenkorbDTODB.isEmpty()) {
            return Optional.empty();
        }

        nullableWarenkorbDTODB.get().warenkorbpostenDTODB.add(
                new WarenkorbpostenDTODB(1, WarenkorbartikelDTODB.Converter.toDTODB(warenkorbartikel)));

        nullableWarenkorbDTODB.get().gesamtSummeBerechnen();

        LOG.debug(nullableWarenkorbDTODB.get());
        return Optional.of(WarenkorbDTODB.Converter.toWarenkorb(nullableWarenkorbDTODB.get()));
    }

    @Override
    public Optional<Warenkorb> warenkorbArtikelEntfernen(Long warenkorbId, Long artikelId) {
        Optional<WarenkorbDTODB> nullableWarenkorbDTODB = findByIdOptional(warenkorbId);
        if (nullableWarenkorbDTODB.isEmpty()) {
            return Optional.empty();
        }
        nullableWarenkorbDTODB.get().warenkorbpostenDTODB = nullableWarenkorbDTODB.get().warenkorbpostenDTODB
                .stream()
                .filter(s -> !s.warenkorbartikelDTODB.id.equals(artikelId))
                .toList();
        nullableWarenkorbDTODB.get().gesamtSummeBerechnen();
        LOG.debug(nullableWarenkorbDTODB.get());
        return Optional.of(WarenkorbDTODB.Converter.toWarenkorb(nullableWarenkorbDTODB.get()));
    }
}
