package de.hsos.swa.bestellung.control;

import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.bestellung.entity.Bestellaccount;
import de.hsos.swa.bestellung.entity.Bestellstatus;
import de.hsos.swa.bestellung.entity.Bestellung;
import de.hsos.swa.bestellung.entity.Bezahlmethode;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

@RequestScoped
public interface BestellungService {

    public Optional<Bestellung> bestellungAnzeigen(Long id);

    public Collection<Bestellung> kaeufeAnzeigen(Collection<Bestellaccount> bestellaccounts);

    public Optional<Bestellung> bestellungAnlegen(Bestellung bestellung);

    public boolean bestellungLoeschen(Long id);

    public boolean bezahlmethodeFestlegen(Long id, Bezahlmethode bezahlmethode);

    public boolean bestellstatusAendern(Long id, Bestellstatus bestellstatus);

    public Optional<Bestellung> bestellungAbschliessen(Long id);
}
