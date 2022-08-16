package de.hsos.swa.accountverwaltung.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.accountverwaltung.entity.Account;
import de.hsos.swa.accountverwaltung.entity.Beobachtungsartikel;

/**
 * Die Klasse AccountService wird für die Accountverwaltung verwendet.
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

@RequestScoped
public interface AccountService {
    public Optional<Account> getAccountByUsername(String username);

    public Optional<Account> createAccount(Account account);

    public Optional<Account> updateAccount(Account account);

    public boolean deleteAccount(String username);

    public Optional<Account> artikelZurBeobachtungsliste(String currentUsername,
            Beobachtungsartikel beobachtungsartikel);

    public Optional<Account> deleteBeobachtungsartikel(Long id, String username);

    public Optional<Long> getReferenceArtikelIdFromBeobachtungsartikel(Long beobachtungsartikelId, String username);
}
