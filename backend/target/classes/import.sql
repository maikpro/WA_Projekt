-- admin Account auch in der myBay Datenbank anlegen
INSERT INTO adresse(id, hausnr, ort, plz, strasse)
VALUES (nextval('hibernate_sequence'), '11', 'Osnabrück', '49078', 'adminstr.');

INSERT INTO adresse(id, hausnr, ort, plz, strasse)
VALUES (nextval('hibernate_sequence'), '22', 'Testabrück', '49076', 'teststr.');

INSERT INTO account(id, email, nachname, username, vorname, adresse_id)
VALUES (nextval('hibernate_sequence'), 'maik.proba@gmail.com', 'admin', 'admin', 'admin', 1);

INSERT INTO account(id, email, nachname, username, vorname, adresse_id)
VALUES (nextval('hibernate_sequence'), 'maik.proba@hs-osnabrueck.de', 'testuser', 'testuser', 'testuser', 2);

--Artikel erstellen
INSERT INTO versand(id, kosten, laendercode, lieferant)
VALUES (nextval('hibernate_sequence'), 4.95, 'CHE', 'HERMES');

INSERT INTO versand(id, kosten, laendercode, lieferant)
VALUES (nextval('hibernate_sequence'), 4.95, 'CHE', 'HERMES');

INSERT INTO versand(id, kosten, laendercode, lieferant)
VALUES (nextval('hibernate_sequence'), 9.95, 'AUT', 'DPD');

INSERT INTO versand(id, kosten, laendercode, lieferant)
VALUES (nextval('hibernate_sequence'), 3.95, 'AUT', 'DHL');

--Ab ID 9 startet ArtikelID

INSERT INTO artikel(id, artikelzustand, artikelstatus, aufrufe, beobachter, erstellt, geaendert, name, beschreibung, preis, transaktionam, username, versand_id)
VALUES (nextval('hibernate_sequence'), 'GEBRAUCHT', 'ACTIVE', 0, 0, '2011-01-01 16:12:53.53841', '2021-01-01 16:12:53.53841', 'T-Shirt Adidas weiß', 'beschreibung tshirt adidas weiß', 11.99, '2015-09-13 16:12:53.53841', 'admin', 5);

INSERT INTO artikel(id, artikelzustand, artikelstatus, aufrufe, beobachter, erstellt, geaendert, name, beschreibung, preis, transaktionam, username, versand_id)
VALUES (nextval('hibernate_sequence'), 'GEBRAUCHT', 'ACTIVE', 0, 0, '2011-01-01 16:12:53.53841', '2021-01-01 16:12:53.53841', 'T-Shirt Nike schwarz', 'beschreibung t-shirt nike schwarz', 11.99, '2015-09-13 16:12:53.53841', 'admin', 6);

INSERT INTO artikel(id, artikelzustand, artikelstatus, aufrufe, beobachter, erstellt, geaendert, name, beschreibung, preis, transaktionam, username, versand_id)
VALUES (nextval('hibernate_sequence'), 'NEU', 'ACTIVE', 30, 10, '2012-01-03 16:12:53.53841', '2021-01-01 16:12:53.53841', 'Tolle Schuhe', 'beschreibung tolle schuhe', 21.99, '2016-02-05 16:12:53.53841', 'admin', 7);

INSERT INTO artikel(id, artikelzustand, artikelstatus, aufrufe, beobachter, erstellt, geaendert, name, beschreibung, preis, transaktionam, username, versand_id)
VALUES (nextval('hibernate_sequence'), 'NEU', 'ACTIVE', 20, 0, '2011-04-18 16:12:53.53841', '2021-12-12 16:12:53.53841', 'Stuhl', 'beschreibung stuhl', 33.99, '2017-02-16 16:12:53.53841', 'admin', 8);

