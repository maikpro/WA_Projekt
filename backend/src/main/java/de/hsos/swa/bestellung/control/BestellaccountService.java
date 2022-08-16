package de.hsos.swa.bestellung.control;

import java.util.Collection;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.bestellung.entity.Bestellaccount;

/**
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */
@RequestScoped
public interface BestellaccountService {
    public Collection<Bestellaccount> getBestellaccountsByUsername(String username);
}
