package de.hsos.swa.artikelverwaltung.gateway;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import de.hsos.swa.artikelverwaltung.control.ArtikelService;
import de.hsos.swa.artikelverwaltung.control.SuchenService;
import de.hsos.swa.artikelverwaltung.entity.Artikel;
import de.hsos.swa.artikelverwaltung.entity.Artikelstatus;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.security.identity.SecurityIdentity;

/**
 * Die Klasse ArtikelRepository
 *
 * @author Maik Proba
 * @version 1.0
 * @since 13-07-2022
 */

@RequestScoped
public class ArtikelRepository implements PanacheRepository<ArtikelDTODB>, ArtikelService, SuchenService {
    private final static Logger LOG = Logger.getLogger(ArtikelRepository.class);

    @Inject
    SecurityIdentity securityIdentity;

    @Override
    public Optional<Artikel> createArtikel(Artikel artikel) {
        // set default values
        artikel.setErstellt(LocalDateTime.now());
        artikel.setAufrufe(0);
        artikel.setBeobachter(0);
        artikel.setArtikelstatus(Artikelstatus.ACTIVE);

        // set Username of creator
        String currentUsername = this.securityIdentity.getPrincipal().getName();
        artikel.setUsername(currentUsername);

        // save artikel to DB
        ArtikelDTODB artikelDTODB = ArtikelDTODB.Converter.toDTODB(artikel);
        persist(artikelDTODB);
        return Optional.ofNullable(ArtikelDTODB.Converter.toArtikel(artikelDTODB));
    }

    @Override
    public Optional<Artikel> getById(Long id) {
        Optional<ArtikelDTODB> nullableArtikelDTODB = findByIdOptional(id);
        if (nullableArtikelDTODB.isEmpty()) {
            return Optional.empty();
        }

        // Aufrufe hoch zählen
        ArtikelDTODB artikelDTODB = countAufrufeUp(nullableArtikelDTODB.get());
        Artikel foundArtikel = ArtikelDTODB.Converter.toArtikel(artikelDTODB);
        return Optional.ofNullable(foundArtikel);
    }

    private ArtikelDTODB countAufrufeUp(ArtikelDTODB artikelDTODB) {
        artikelDTODB.aufrufe++;
        LOG.debugf("AUFRUFE: ", artikelDTODB.aufrufe);
        persist(artikelDTODB);
        return artikelDTODB;
    }

    @Override
    public Collection<Artikel> getAll() {
        Collection<ArtikelDTODB> artikelDTODBs = streamAll().toList();
        return artikelDTODBs.stream().map(artikelDTODB -> ArtikelDTODB.Converter.toArtikel(artikelDTODB)).toList();
    }

    @Override
    public Collection<Artikel> getAllByLikeSearch(String suchwort) {
        Collection<ArtikelDTODB> artikelDTODBs = list(
                "lower(name) like concat('%', lower(?1), '%') and artikelstatus = 'ACTIVE'", suchwort);
        LOG.debug(artikelDTODBs);
        return artikelDTODBs.stream().map(artikelDTODB -> ArtikelDTODB.Converter.toArtikel(artikelDTODB)).toList();
    }

    @Override
    public Collection<Artikel> getLatestArtikel() {
        Collection<ArtikelDTODB> artikelDTODBs = list("artikelstatus = 'ACTIVE' order by erstellt DESC");
        return artikelDTODBs.stream().map(artikelDTODB -> ArtikelDTODB.Converter.toArtikel(artikelDTODB)).toList();
    }

    @Override
    public Optional<Artikel> updateArtikel(Artikel artikel) {
        Optional<ArtikelDTODB> nullableArtikelDTODB = findByIdOptional(artikel.getId());
        if (nullableArtikelDTODB.isEmpty()) {
            return Optional.empty();
        }

        nullableArtikelDTODB.get().update(artikel);
        return Optional.of(ArtikelDTODB.Converter.toArtikel(nullableArtikelDTODB.get()));
    }

    @Override
    public Collection<Artikel> getVerkaufteArtikel(String username) {
        Collection<ArtikelDTODB> artikelDTODBs = list("artikelstatus = 'DEACTIVATED' and username = ?1", username);
        LOG.debug(artikelDTODBs);
        return artikelDTODBs.stream().map(artikelDTODB -> ArtikelDTODB.Converter.toArtikel(artikelDTODB)).toList();
    }

    @Override
    public Collection<Artikel> getYourArtikel(String username) {
        Collection<ArtikelDTODB> artikelDTODBs = list("artikelstatus = 'ACTIVE' and username = ?1", username);
        LOG.debug(artikelDTODBs);
        return artikelDTODBs.stream().map(artikelDTODB -> ArtikelDTODB.Converter.toArtikel(artikelDTODB)).toList();
    }

}
