package fi.academy.forum;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
public class Viesti {
    @Id @GeneratedValue
    private Integer id;
    private String teksti;
    private Timestamp aikaleima = new Timestamp(new Date().getTime());

    @ManyToOne
    @JoinColumn(name="kayttaja")
    public Kayttaja kayttaja;
    private Integer laskuri;
    private String otsikko;

    public Viesti(String teksti, Kayttaja kayttaja) {
        this.teksti = teksti;
        this.kayttaja = kayttaja;
    }

    public Viesti(Kayttaja kayttaja) {
        this.kayttaja = kayttaja;
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
}
