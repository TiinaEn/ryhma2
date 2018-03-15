package fi.academy.forum;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Kayttaja {
    @Id @GeneratedValue
    private Integer id;
    private String nimi;
    private String nimimerkki;
    private Integer viestienMaara;
    private String salasana;
    private String sahkoposti;
    private Integer adminoikeus;


    public Kayttaja(String nimimerkki, String salasana) {
        this.nimimerkki = nimimerkki;
        this.salasana = salasana;
        this.adminoikeus = 0;
        this.viestienMaara = 0;
    }

    public Kayttaja() {
        this.adminoikeus = 0;
        this.viestienMaara = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNimi() {
        return nimi;
    }

    public void setNimi(String nimi) {
        this.nimi = nimi;
    }

    public String getNimimerkki() {
        return nimimerkki;
    }

    public void setNimimerkki(String nimimerkki) {
        this.nimimerkki = nimimerkki;
    }

    public Integer getViestienMaara() {
        return viestienMaara;
    }

    public void setViestienMaara(Integer viestienMaara) {
        this.viestienMaara = viestienMaara;
    }

    public String getSalasana() {
        return salasana;
    }

    public void setSalasana(String salasana) {
        this.salasana = salasana;
    }

    public String getSahkoposti() {
        return sahkoposti;
    }

    public void setSahkoposti(String sahkoposti) {
        this.sahkoposti = sahkoposti;
    }

    public int getAdminoikeus() {
        return adminoikeus;
    }

    public void setAdminoikeus(int adminoikeus) {
        this.adminoikeus = adminoikeus;
    }
}
