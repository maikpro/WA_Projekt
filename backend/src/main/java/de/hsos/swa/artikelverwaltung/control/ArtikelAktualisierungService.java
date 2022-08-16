package de.hsos.swa.artikelverwaltung.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.hsos.swa.accountverwaltung.control.acl.IArtikelBeobachterAktualisierung;
import de.hsos.swa.artikelverwaltung.entity.Artikel;
import de.hsos.swa.artikelverwaltung.entity.Artikelstatus;
import de.hsos.swa.bestellung.control.acl.IArtikelVerkauft;

/**
 * Die Klasse ArtikelAktualisierungService zum Aktualisieren des Artikels
 * Beobachter reduzieren und artikelstatus
 *
 * @author Maik Proba
 * @version 1.0
 * @since 29-07-2022
 */

@RequestScoped
public class ArtikelAktualisierungService implements IArtikelBeobachterAktualisierung, IArtikelVerkauft {
    private static Logger LOG = Logger.getLogger(ArtikelAktualisierungService.class);

    @Inject
    ArtikelService artikelService;

    @Override
    public void beobachterAnzahlReduzieren(Long artikelId) {
        Optional<Artikel> nullableArtikel = this.artikelService.getById(artikelId);
        if (nullableArtikel.isEmpty()) {
            LOG.debug("Artikel nicht gefunden!");
            return;
        }
        LOG.debugf("Artikel von der Beobachtungsliste entfernt. Beobachteranzahl VORHER: %d",
                nullableArtikel.get().getBeobachter());
        nullableArtikel.get().countBeobachterDown();
        LOG.debugf("Artikel von der Beobachtungsliste entfernt. Beobachteranzahl NACHHER: %d",
                nullableArtikel.get().getBeobachter());

        this.artikelService.updateArtikel(nullableArtikel.get());
    }

    @Override
    public void artikelstatusUpdate(Long artikelId) {
        Optional<Artikel> nullableArtikel = this.artikelService.getById(artikelId);
        if (nullableArtikel.isEmpty()) {
            LOG.debug("Artikel nicht gefunden!");
            return;
        }
        nullableArtikel.get().setArtikelstatus(Artikelstatus.DEACTIVATED);
        this.artikelService.updateArtikel(nullableArtikel.get());
    }

}
