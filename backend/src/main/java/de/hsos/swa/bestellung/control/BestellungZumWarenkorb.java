package de.hsos.swa.bestellung.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.hsos.swa.bestellung.entity.Bestellartikel;
import de.hsos.swa.bestellung.entity.Bestellartikelversand;
import de.hsos.swa.bestellung.entity.Bestellartikelzustand;
import de.hsos.swa.bestellung.entity.Bestellposten;
import de.hsos.swa.bestellung.entity.Bestellstatus;
import de.hsos.swa.bestellung.entity.Bestellung;
import de.hsos.swa.bestellung.entity.LaenderCode;
import de.hsos.swa.bestellung.entity.Lieferant;
import de.hsos.swa.warenkorb.boundary.rest.WarenkorbDTO;
import de.hsos.swa.warenkorb.boundary.rest.WarenkorbpostenDTO;
import de.hsos.swa.warenkorb.control.acl.IBestellungZumWarenkorb;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 23-07-2022
 */

@RequestScoped
public class BestellungZumWarenkorb implements IBestellungZumWarenkorb {
        private final static Logger LOG = Logger.getLogger(BestellungZumWarenkorb.class);

        @Inject
        BestellungService bestellungService;

        @Inject
        BestellartikelService bestellartikelService;

        @Override
        public boolean warenKorbZurKasse(WarenkorbDTO warenkorbDTO) {
                Bestellung bestellung = new Bestellung();
                bestellung.setBestellstatus(Bestellstatus.BEZAHLT);
                for (WarenkorbpostenDTO warenkorbpostenDTO : warenkorbDTO.warenkorbpostenDTO) {
                        Bestellartikelzustand artikelzustand = Bestellartikelzustand
                                        .valueOf(warenkorbpostenDTO.warenkorbartikelDTO.warenkorbartikelzustand
                                                        .toString());
                        Lieferant lieferant = Lieferant
                                        .valueOf(warenkorbpostenDTO.warenkorbartikelDTO.versand.lieferant
                                                        .toString());
                        LaenderCode laenderCode = LaenderCode
                                        .valueOf(warenkorbpostenDTO.warenkorbartikelDTO.versand.laenderCode
                                                        .toString());
                        Bestellartikelversand artikelversand = new Bestellartikelversand(
                                        warenkorbpostenDTO.warenkorbartikelDTO.versand.id,
                                        warenkorbpostenDTO.warenkorbartikelDTO.versand.kosten,
                                        lieferant, laenderCode);

                        Bestellartikel bestellartikel = new Bestellartikel(warenkorbpostenDTO.warenkorbartikelDTO.name,
                                        warenkorbpostenDTO.warenkorbartikelDTO.preis,
                                        artikelzustand,
                                        artikelversand,
                                        warenkorbpostenDTO.warenkorbartikelDTO.username,
                                        warenkorbpostenDTO.warenkorbartikelDTO.artikelIdReference);

                        Optional<Bestellartikel> nullableBestellartikel = this.bestellartikelService
                                        .createBestellartikel(bestellartikel);

                        if (nullableBestellartikel.isEmpty()) {
                                LOG.debug("Fehler beim Speichern des Warenkorbartikels...");
                                return false;
                        }

                        Bestellposten bestellposten = new Bestellposten(warenkorbpostenDTO.postenNr,
                                        warenkorbpostenDTO.menge,
                                        bestellartikel);
                        bestellung.getBestellposten().add(bestellposten);
                }
                this.bestellungService.bestellungAnlegen(bestellung);
                return true;
        }

}
