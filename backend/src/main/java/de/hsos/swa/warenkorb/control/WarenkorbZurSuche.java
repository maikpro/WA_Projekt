package de.hsos.swa.warenkorb.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;
import de.hsos.swa.artikelverwaltung.control.acl.IWarenkorbZurSuche;
import de.hsos.swa.warenkorb.boundary.rest.WarenkorbDTO;
import de.hsos.swa.warenkorb.entity.LaenderCode;
import de.hsos.swa.warenkorb.entity.Lieferant;
import de.hsos.swa.warenkorb.entity.Warenkorb;
import de.hsos.swa.warenkorb.entity.Warenkorbartikel;
import de.hsos.swa.warenkorb.entity.Warenkorbartikelversand;
import de.hsos.swa.warenkorb.entity.Warenkorbartikelzustand;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

@RequestScoped
public class WarenkorbZurSuche implements IWarenkorbZurSuche {
        private final static Logger LOG = Logger.getLogger(WarenkorbZurSuche.class);

        @Inject
        WarenkorbService warenkorbService;

        @Inject
        WarenkorbartikelService warenkorbartikelService;

        @Override
        public Optional<WarenkorbDTO> artikelHinzufuegen(Long warenkorbId, ArtikelDTO artikelDTO) {
                // Konvertiere artikelDTO in warenkorbartikelDTO
                Warenkorbartikelzustand artikelzustand = Warenkorbartikelzustand
                                .valueOf(artikelDTO.artikelzustand.toString());

                Lieferant lieferant = Lieferant.valueOf(artikelDTO.versandDTO.lieferant.toString());
                LaenderCode laenderCode = LaenderCode.valueOf(artikelDTO.versandDTO.laenderCode.toString());

                Warenkorbartikelversand warenkorbartikelversand = new Warenkorbartikelversand(
                                artikelDTO.versandDTO.kosten, lieferant, laenderCode);

                // Mappen von ArtikelDTO auf Warenkorbartikel
                Warenkorbartikel warenkorbartikel = new Warenkorbartikel(artikelDTO.name, artikelDTO.preis,
                                artikelzustand, warenkorbartikelversand, artikelDTO.username, artikelDTO.id);

                // erstmal in DB abspeichern
                Optional<Warenkorbartikel> nullableWarenkorbartikel = this.warenkorbartikelService
                                .createWarenkorbartikel(warenkorbartikel);

                if (nullableWarenkorbartikel.isEmpty()) {
                        LOG.debug("Fehler beim Speichern des Warenkorbartikels...");
                        return Optional.empty();
                }
                // return this.warenkorbService.artikelInWarenkorbLegen(warenkorbId,
                // nullableWarenkorbartikel.get());
                Optional<Warenkorb> nullableWarenkorb = this.warenkorbService.artikelInWarenkorbLegen(warenkorbId,
                                nullableWarenkorbartikel.get());
                if (nullableWarenkorb.isEmpty()) {
                        LOG.debug("Fehler beim Speichern des Warenkorbs");
                        return Optional.empty();
                }
                return Optional.of(WarenkorbDTO.Converter.toWarenkorbDTO(nullableWarenkorb.get()));
        }

}
