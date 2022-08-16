package de.hsos.swa.bestellung.control;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.hsos.swa.bestellung.entity.Bestellaccount;
import de.hsos.swa.bestellung.entity.Bestellposten;
import de.hsos.swa.bestellung.entity.Bestellung;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 31-07-2022
 */

@RequestScoped
public class EmailService {
    private final static Logger LOG = Logger.getLogger(EmailService.class);

    // Reactive Mailer zum asynchronen versenden der Mails an den Käufer und den
    // Verkäufer
    @Inject
    ReactiveMailer reactiveMailer;

    public void mailsVersenden(Bestellung bestellung) {
        for (Bestellaccount verkaeufer : bestellung.getVerkaeufer()) {
            reactiveMailer.send(Mail.withText(verkaeufer.getEmail(), "MyBay Artikelverkauf",
                    "Ein Artikel von Ihnen wurde bei MyBay gekauft."));
        }
        String bestelluebersicht = "Ihre Bestellung: \n";
        for (Bestellposten bestellposten : bestellung.getBestellposten()) {
            bestelluebersicht += "Bezeichnung: " + bestellposten.getBestellartikel().getName() + ", Preis: "
                    + bestellposten.getBestellartikel().getPreis() + "€ \n";
        }
        bestelluebersicht += "Gesamtsumme: " + bestellung.getGesamtSumme() + "€ \n";
        reactiveMailer
                .send(Mail.withText(bestellung.getKaeufer().getEmail(), "Ihre MyBay Bestellung", bestelluebersicht));
        LOG.debug("Mails wurden versendet!");
    }
}
