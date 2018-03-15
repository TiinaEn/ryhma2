package fi.academy.forum;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface KayttajaRepositorio extends CrudRepository<Kayttaja, Integer> {

    Optional<Kayttaja> findByNimimerkki(String nimimerkki);

    Optional<Kayttaja> findByAdminoikeus(Integer i);
}
