package com.springboot.springbootconnecttomongobd.entity;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "BooksTable")
public class Book {

    @Id
    public ObjectId id;
    @NotNull
    @Getter
    @Setter
    private String title;
    @NotNull
    @Getter
    @Setter
    public String content;

}
