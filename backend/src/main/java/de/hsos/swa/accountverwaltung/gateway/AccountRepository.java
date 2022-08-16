package de.hsos.swa.accountverwaltung.gateway;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.accountverwaltung.control.AccountService;
import de.hsos.swa.accountverwaltung.entity.Account;
import de.hsos.swa.accountverwaltung.entity.Beobachtungsartikel;
import io.quarkus.hibernate.orm.panache.PanacheRepository;

/**
 * Die Klasse AccountRepository wird von der Accountverwaltung genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 25-07-2022
 */

@RequestScoped
public class AccountRepository implements PanacheRepository<AccountDTODB>, AccountService {

    @Override
    public Optional<Account> getAccountByUsername(String username) {
        Optional<AccountDTODB> nullableAccountDTODB = Optional.ofNullable(find("username", username).firstResult());
        if (nullableAccountDTODB.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(AccountDTODB.Converter.toAccount(nullableAccountDTODB.get()));
    }

    @Override
    public Optional<Account> createAccount(Account account) {
        AccountDTODB accountDTODB = AccountDTODB.Converter.toDTODB(account);
        accountDTODB.beobachtungsliste = new ArrayList<>();
        persist(accountDTODB);
        Optional<AccountDTODB> nullableAccountDTODB = Optional
                .ofNullable(find("username", account.getUsername()).firstResult());
        if (nullableAccountDTODB.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(AccountDTODB.Converter.toAccount(nullableAccountDTODB.get()));
    }

    @Override
    public Optional<Account> updateAccount(Account account) {
        Optional<AccountDTODB> nullableAccountDTODB = Optional
                .ofNullable(find("username", account.getUsername()).firstResult());
        if (nullableAccountDTODB.isEmpty()) {
            return Optional.empty();
        }

        // Update
        nullableAccountDTODB.get().aktualisieren(account);

        return Optional.ofNullable(AccountDTODB.Converter.toAccount(nullableAccountDTODB.get()));
    }

    @Override
    public boolean deleteAccount(String username) {
        Optional<AccountDTODB> nullableAccountDTODB = Optional.ofNullable(find("username", username).firstResult());
        if (nullableAccountDTODB.isEmpty()) {
            return false;
        }
        return deleteById(nullableAccountDTODB.get().id);
    }

    @Override
    public Optional<Account> artikelZurBeobachtungsliste(String currentUsername,
            Beobachtungsartikel beobachtungsartikel) {
        Optional<AccountDTODB> nullableAccountDTODB = Optional
                .ofNullable(find("username", currentUsername).firstResult());
        if (nullableAccountDTODB.isEmpty()) {
            return Optional.empty();
        }
        BeobachtungsartikelDTODB beobachtungsartikelDTODB = BeobachtungsartikelDTODB.Converter
                .toDTODB(beobachtungsartikel);

        // Check, ob Artikel bereits im Warenkorb vorhanden ist...
        boolean isArtikelInDB = nullableAccountDTODB.get().beobachtungsliste.stream()
                .anyMatch(b -> b.artikelIdReference.equals(beobachtungsartikelDTODB.artikelIdReference));
        if (isArtikelInDB) {
            return Optional.empty();
        }

        nullableAccountDTODB.get().beobachtungsliste.add(beobachtungsartikelDTODB);
        return Optional.of(AccountDTODB.Converter.toAccount(nullableAccountDTODB.get()));
    }

    @Override
    public Optional<Account> deleteBeobachtungsartikel(Long id, String username) {
        Optional<AccountDTODB> nullableAccountDTODB = Optional.ofNullable(find("username", username).firstResult());
        if (nullableAccountDTODB.isEmpty()) {
            return Optional.empty();
        }

        // Hier das Problem mit: A collection with cascade="all-delete-orphan" was no
        // longer referenced by the owning entity instance
        // Da eine neue Reference auf die Liste gesetzt wird...
        // Lösung mit clear() und addAll()
        /*
         * nullableAccountDTODB.get().beobachtungsliste =
         * nullableAccountDTODB.get().beobachtungsliste.stream()
         * .filter(beobachtungsartikel -> beobachtungsartikel.id.equals(id)).toList();
         */
        List<BeobachtungsartikelDTODB> newList = nullableAccountDTODB.get().beobachtungsliste.stream()
                .filter(beobachtungsartikel -> !beobachtungsartikel.id.equals(id)).toList();

        nullableAccountDTODB.get().beobachtungsliste.clear();
        nullableAccountDTODB.get().beobachtungsliste.addAll(newList);

        return Optional.of(AccountDTODB.Converter.toAccount(nullableAccountDTODB.get()));
    }

    @Override
    public Optional<Long> getReferenceArtikelIdFromBeobachtungsartikel(Long beobachtungsartikelId, String username) {
        Optional<AccountDTODB> nullableAccountDTODB = Optional.ofNullable(find("username", username).firstResult());
        if (nullableAccountDTODB.isEmpty()) {
            return Optional.empty();
        }
        Optional<BeobachtungsartikelDTODB> nullableBeobachtungsartikelDTODB = nullableAccountDTODB
                .get().beobachtungsliste.stream().filter(b -> b.id.equals(beobachtungsartikelId)).findFirst();
        if (nullableBeobachtungsartikelDTODB.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(nullableBeobachtungsartikelDTODB.get().artikelIdReference);
    }

}
