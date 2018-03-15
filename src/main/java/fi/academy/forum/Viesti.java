package fi.academy.forum;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Viesti {
    @Id @GeneratedValue
    private Integer id;
    @Lob        //muuttaa String tyypin tietokannassa varchar:sta text-tyypiksi
    private String teksti;
    @Transient
    private Viesti vastattuviesti;
    private Integer viestiketjunAloittaja=0;
    private Integer viestiketju;
    private Timestamp aikaleima = new Timestamp(new Date().getTime());

    @ManyToOne
    @JoinColumn(name="kayttaja")
    public Kayttaja kayttaja;
    private Integer laskuri;
    private String otsikko;

    public Viesti(String teksti, Kayttaja kayttaja) {
        this.teksti = teksti;
        this.kayttaja = kayttaja;
        this.kayttaja.setViestienMaara(this.kayttaja.getViestienMaara()+1);
    }

    public Viesti(String teksti, Kayttaja kayttaja, Integer viestiketju) {
        this.teksti = teksti;
        this.kayttaja = kayttaja;
        this.kayttaja.setViestienMaara(this.kayttaja.getViestienMaara()+1);
        this.viestiketju = viestiketju;
    }

    public Viesti(String teksti, Kayttaja kayttaja, Integer viestiketju, Integer viestiketjunAloittaja, String otsikko) {
        this.teksti = teksti;
        this.kayttaja = kayttaja;
        this.kayttaja.setViestienMaara(this.kayttaja.getViestienMaara()+1);
        this.viestiketju = viestiketju;
        this.otsikko = otsikko;
        this.viestiketjunAloittaja = viestiketjunAloittaja;
    }

    public Viesti(String teksti, Kayttaja kayttaja, Integer viestiketju, Integer viestiketjunAloittaja) {
        this.viestiketjunAloittaja = viestiketjunAloittaja;
        this.teksti = teksti;
        this.kayttaja = kayttaja;
        this.kayttaja.setViestienMaara(this.kayttaja.getViestienMaara()+1);
        this.viestiketju = viestiketju;
    }

    public Viesti(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
        this.kayttaja.setViestienMaara(this.kayttaja.getViestienMaara()+1);
    }

    public Viesti() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTeksti() {
        return teksti;
    }

    public void setTeksti(String teksti) {
        this.teksti = teksti;
    }

    // Haetaan aikaleimasta päiväys ja kellonaika sekuntien tarkkuudella
    public String getAikaleima() {
        return aikaleima.toString().substring(0,19);
    }

    public void setAikaleima(Timestamp aikaleima) {
        this.aikaleima = aikaleima;
    }

    public Integer getLaskuri() {
        return laskuri;
    }

    public void setLaskuri(Integer laskuri) {
        this.laskuri = laskuri;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public Kayttaja getKayttaja() {
        return kayttaja;
    }

    public void setKayttaja(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
    }

    public Viesti getVastattuviesti() {
        return vastattuviesti;
    }

    public void setVastattuviesti(Viesti vastattuviesti) {
        this.vastattuviesti = vastattuviesti;
    }

    public Integer getViestiketju() {
        return viestiketju;
    }

    public void setViestiketju(Integer viestiketju) {
        this.viestiketju = viestiketju;
    }

    public Integer getViestiketjunAloittaja() {
        return viestiketjunAloittaja;
    }

    public void setViestiketjunAloittaja(Integer viestiketjunAloittaja) {
        this.viestiketjunAloittaja = viestiketjunAloittaja;
    }
}
