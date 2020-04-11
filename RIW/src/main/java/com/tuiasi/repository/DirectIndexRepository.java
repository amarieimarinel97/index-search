package com.tuiasi.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.tuiasi.model.DirectIndexEntry;
import com.tuiasi.model.utils.WordAppearancesPair;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Component
public class DirectIndexRepository {

    public List<DirectIndexEntry> getAll() {
        List<DirectIndexEntry> result = new ArrayList<>();
        MongoCursor cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document obj = (Document) cursor.next();
            result.add(createModel(obj));
        }
        return result;
    }

    public void delete(String document) {
        collection.deleteOne(Filters.eq("document", document));
    }

    public void deleteAll() {
        collection.deleteMany(new BasicDBObject());
    }

    public void add(DirectIndexEntry di) {
        collection.insertOne(createDBObject(di));
    }

    public DirectIndexEntry get(String document) {
        return createModel(Objects.requireNonNull(db.getCollection(COLLECTION_NAME)
                .find(Filters.eq("document", document)).first()));
    }


    private Document createDBObject(DirectIndexEntry di) {
        Document doc = new Document();
        doc.put("document", di.getDocument());
        doc.put("words", di.getWords());
        return doc;
    }

    private DirectIndexEntry createModel(Document doc) {
        List<WordAppearancesPair> words = new ArrayList<>();
        for (Object w : doc.get("words", docClazz))
            words.add(
                    WordAppearancesPair.builder()
                            .word(((Document) w).get("word").toString())
                            .counter(Double.parseDouble(((Document) w).get("counter").toString()))
                            .build()
            );

        return DirectIndexEntry.builder()
                ._id(doc.get("_id").toString())
                .words(words)
                .document((String) doc.get("document"))
                .build();
    }


    public  final String COLLECTION_NAME = "directIndex";
    private  CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();
    private MongoClient mongoClient = MongoClients.create(settings);
    private final MongoDatabase db = mongoClient.getDatabase("RIW");
    private final MongoCollection collection = db.getCollection(COLLECTION_NAME);
    private final Class<? extends List> docClazz = new ArrayList<Document>().getClass();
}
