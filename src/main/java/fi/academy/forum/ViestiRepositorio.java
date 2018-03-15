package fi.academy.forum;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ViestiRepositorio extends CrudRepository<Viesti, Integer> {
    @Query("select v from Viesti v order by v.id asc")
    List<Viesti> etsiKaikkiAikajarjestyksessa();
}
