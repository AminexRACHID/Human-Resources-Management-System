package miaad.sgrh.servicemessagerie.attachement;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Attachement {
    @Id
    private String id;
    private String title;
    private Binary attachement;
}
