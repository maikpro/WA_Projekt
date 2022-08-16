package de.hsos.swa.accountverwaltung.gateway;

import java.util.List;

import javax.enterprise.inject.Vetoed;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import de.hsos.swa.accountverwaltung.entity.Account;
import de.hsos.swa.accountverwaltung.entity.Beobachtungsartikel;

/**
 * Die Klasse AccountDTODB wird für den Transport der Daten aus der Account
 * Entity zur Datenbank genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 19-07-2022
 */

@Entity
@Vetoed
@Table(name = "account")
public class AccountDTODB {
    @Id
    @GeneratedValue
    public Long id;
    public String vorname;
    public String nachname;
    public String email;
    public String username;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "adresse_id")
    public AdresseDTODB adresseDTODB;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    public List<BeobachtungsartikelDTODB> beobachtungsliste;

    public AccountDTODB() {
    }

    public AccountDTODB(Long id, String vorname, String nachname, String email, String username,
            AdresseDTODB adresseDTODB, List<BeobachtungsartikelDTODB> beobachtungsliste) {
        this.id = id;
        this.vorname = vorname;
        this.nachname = nachname;
        this.email = email;
        this.username = username;
        this.adresseDTODB = adresseDTODB;
        this.beobachtungsliste = beobachtungsliste;
    }

    /**
     * Beim Account-Update können die Felder vorname, nachname, adresse geändert
     * werden.
     * Der Username und die E-Mail können NICHT geändert werden!
     */
    public void aktualisieren(Account geaenderterAccount) {
        this.vorname = geaenderterAccount.getVorname();
        this.nachname = geaenderterAccount.getNachname();
        this.adresseDTODB.aktualisieren(geaenderterAccount.getAdresse());
    }

    public static class Converter {
        public static AccountDTODB toDTODB(Account account) {
            List<BeobachtungsartikelDTODB> beobachtungsartikelDTODBs = account.getBeobachtungsliste().stream()
                    .map(bdtodb -> BeobachtungsartikelDTODB.Converter.toDTODB(bdtodb)).toList();
            return new AccountDTODB(account.getId(), account.getVorname(), account.getNachname(), account.getEmail(),
                    account.getUsername(), AdresseDTODB.Converter.toDTODB(account.getAdresse()),
                    beobachtungsartikelDTODBs);
        }

        public static Account toAccount(AccountDTODB accountDTODB) {
            List<Beobachtungsartikel> beobachtungsliste = accountDTODB.beobachtungsliste.stream()
                    .map(bdtodb -> BeobachtungsartikelDTODB.Converter.toBeobachtungsartikel(bdtodb)).toList();

            return new Account(accountDTODB.id, accountDTODB.vorname, accountDTODB.nachname, accountDTODB.email,
                    accountDTODB.username, AdresseDTODB.Converter.toAdresse(accountDTODB.adresseDTODB),
                    beobachtungsliste);
        }
    }

}
