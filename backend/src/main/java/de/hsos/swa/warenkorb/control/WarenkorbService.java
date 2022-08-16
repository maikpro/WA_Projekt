package de.hsos.swa.warenkorb.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.warenkorb.entity.Warenkorb;
import de.hsos.swa.warenkorb.entity.Warenkorbartikel;

/*
 * @author Thorben Fabrewitz
 * @version 1.0
 * @since 19-07-2022
 */

@RequestScoped
public interface WarenkorbService {
    public Optional<Warenkorb> warenkorbAnzeigen(Long id);

    public Optional<Warenkorb> warenkorbAnlegen();

    public Optional<Warenkorb> warenkorbArtikelEntfernen(Long warenkorbId, Long artikelId);

    public boolean warenkorbLoeschen(Long id);

    public Optional<Warenkorb> artikelInWarenkorbLegen(Long warenkorbId, Warenkorbartikel warenkorbartikel);

}
