package miaad.sgrh.servicemessagerie.attachement;

import org.bson.BsonBinarySubType;
import org.bson.types.Binary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class AttachementService {
    @Autowired
    private AttachementRepository attachementRepository;
    public String addAttachement(String originalFilename, MultipartFile attach) throws IOException {
        Attachement attachement = new Attachement();
        attachement.setTitle(originalFilename);
        attachement.setAttachement(new Binary(BsonBinarySubType.BINARY, attach.getBytes()));
        return attachementRepository.save(attachement).getId();
    }

    public String getAttachementName(String id){
        Attachement attachement = attachementRepository.findById(id).get();
        String name = attachement.getTitle();
        return name;
    }

    public Attachement getAttachement(String id) {
        return attachementRepository.findById(id).get();
    }

    public void deleteAttachement(String id) {
        if (attachementRepository.existsById(id)) {
            attachementRepository.deleteById(id);
        } else {
            throw new NoSuchElementException("Pas d'attachement avec cet ID : " + id);
        }
    }
}
