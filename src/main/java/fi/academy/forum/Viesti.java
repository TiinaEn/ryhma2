package fi.academy.forum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
public class Viesti {
    @Id @GeneratedValue
    private Integer id;
    private String teksti;
    private LocalDate aikaleima;
    private String nimimerkki;
    private Integer laskuri;
    private String otsikko;

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

    public LocalDate getAikaleima() {
        return aikaleima;
    }

    public void setAikaleima(LocalDate aikaleima) {
        this.aikaleima = aikaleima;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
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
}
