package de.hsos.swa.warenkorb.control;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import de.hsos.swa.warenkorb.entity.Warenkorbartikel;

@RequestScoped
public interface WarenkorbartikelService {
    Optional<Warenkorbartikel> createWarenkorbartikel(Warenkorbartikel warenkorbartikel);
}
