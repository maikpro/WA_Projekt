package de.hsos.swa.bestellung.gateway;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.bestellung.control.BestellaccountService;
import de.hsos.swa.bestellung.entity.Bestellaccount;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */
@RequestScoped
public class BestellaccountRepository implements PanacheRepository<BestellaccountDTODB>, BestellaccountService {

    @Override
    public Collection<Bestellaccount> getBestellaccountsByUsername(String username) {
        Collection<BestellaccountDTODB> bestellaccountDTODBs = list("username", username);
        Collection<Bestellaccount> bestellaccounts = bestellaccountDTODBs.stream()
                .map(ba -> BestellaccountDTODB.Converter.toBestellaccount(ba)).toList();
        return bestellaccounts;
    }

}
