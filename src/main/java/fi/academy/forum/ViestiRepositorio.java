package fi.academy.forum;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ViestiRepositorio extends CrudRepository<Viesti, Integer> {
    @Query("select v from Viesti v order by v.id asc")
    List<Viesti> etsiKaikkiAikajarjestyksessa();

    @Query("select v from Viesti v Where v.viestiketjunAloittaja=1 order by v.id asc")
    List<Viesti> etsiKaikkiViestiketjut();

    @Query("select v from Viesti v where v.viestiketju= :id order by v.id asc")
    List<Viesti> etsiKaikkiAikajarjestyksessaID(@Param("id") Integer id);

}
