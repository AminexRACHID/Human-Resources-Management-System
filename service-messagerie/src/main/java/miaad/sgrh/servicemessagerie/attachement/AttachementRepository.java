package miaad.sgrh.servicemessagerie.attachement;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachementRepository extends MongoRepository<Attachement, String> {
}
