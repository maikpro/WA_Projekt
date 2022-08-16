package de.hsos.swa.accountverwaltung.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import de.hsos.swa.accountverwaltung.entity.Account;
import de.hsos.swa.accountverwaltung.entity.Artikelzustand;
import de.hsos.swa.accountverwaltung.entity.Beobachtungsartikel;
import de.hsos.swa.artikelverwaltung.boundary.rest.artikel.ArtikelDTO;
import de.hsos.swa.artikelverwaltung.control.acl.IBeobachtungslisteService;
import de.hsos.swa.artikelverwaltung.entity.Artikel;
import io.quarkus.security.identity.SecurityIdentity;

/**
 * Die Klasse BeobachtungslisteService wird für den Transport der Artikel vom
 * Artikelverwaltungsmodul zur Beobachtungsliste über IBeobachtungslisteService
 * verwendet
 *
 * @author Maik Proba
 * @version 1.0
 * @since 28-07-2022
 */

@RequestScoped
public class BeobachtungslisteService implements IBeobachtungslisteService {

    @Inject
    AccountService accountService;

    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public Optional<ArtikelDTO> artikelHinzufuegen(ArtikelDTO artikelDTO) {
        // konvertiere ArtikelDTO in BeobachtungsartikelDTO
        Artikelzustand artikelzustand = Artikelzustand.valueOf(artikelDTO.artikelzustand.toString());
        Beobachtungsartikel beobachtungsartikel = new Beobachtungsartikel(artikelDTO.name, artikelDTO.preis,
                artikelzustand, artikelDTO.id);

        String currentUsername = this.securityIdentity.getPrincipal().getName();
        Optional<Account> nullableAccount = this.accountService.artikelZurBeobachtungsliste(currentUsername,
                beobachtungsartikel);

        if (nullableAccount.isEmpty()) {
            return Optional.empty();
        }

        Artikel artikel = ArtikelDTO.Converter.toArtikel(artikelDTO);
        artikel.countBeobachterUp();
        return Optional.of(ArtikelDTO.Converter.toDTO(artikel));
    }

}
