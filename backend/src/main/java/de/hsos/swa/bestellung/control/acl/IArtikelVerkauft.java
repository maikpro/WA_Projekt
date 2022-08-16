package de.hsos.swa.bestellung.control.acl;

/**
 * Die Klasse AccountDTODB wird für den Transport der Daten aus der Account
 * Entity zur Datenbank genutzt
 *
 * @author Maik Proba
 * @version 1.0
 * @since 30-07-2022
 */
public interface IArtikelVerkauft {
    public void artikelstatusUpdate(Long artikelId);
}
