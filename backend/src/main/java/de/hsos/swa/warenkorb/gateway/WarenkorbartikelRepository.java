package de.hsos.swa.warenkorb.gateway;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.transaction.Transactional;

import de.hsos.swa.warenkorb.control.WarenkorbartikelService;
import de.hsos.swa.warenkorb.entity.Warenkorbartikel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

@RequestScoped
@Transactional
public class WarenkorbartikelRepository implements PanacheRepository<WarenkorbartikelDTODB>, WarenkorbartikelService {

    @Override
    public Optional<Warenkorbartikel> createWarenkorbartikel(Warenkorbartikel warenkorbartikel) {
        WarenkorbartikelDTODB warenkorbartikelDTODB = WarenkorbartikelDTODB.Converter
                .toDTODB(warenkorbartikel);
        persist(warenkorbartikelDTODB);
        return Optional.of(WarenkorbartikelDTODB.Converter.toWarenkorbartikel(warenkorbartikelDTODB));
    }

}
