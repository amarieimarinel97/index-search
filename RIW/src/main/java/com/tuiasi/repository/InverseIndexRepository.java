package com.tuiasi.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.tuiasi.model.InverseIndexEntry;
import com.tuiasi.model.utils.DocumentAppearancesPair;
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
public class InverseIndexRepository {

    public List<InverseIndexEntry> getAll() {
        List<InverseIndexEntry> result = new ArrayList<>();
        MongoCursor cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document obj = (Document) cursor.next();
            result.add(createModel(obj));
        }
        return result;
    }

    public void delete(String word) {
        collection.deleteOne(Filters.eq("word", word));
    }

    public void deleteAll() {
        collection.deleteMany(new BasicDBObject());
    }

    public void add(InverseIndexEntry ii) {
        collection.insertOne(createDBObject(ii));
    }

    public InverseIndexEntry get(String word) {
        return createModel(Objects.requireNonNull(db.getCollection(COLLECTION_NAME)
                .find(Filters.eq("word", word)).first()));
    }


    private Document createDBObject(InverseIndexEntry ii) {
        Document doc = new Document();
        doc.put("word", ii.getWord());
        doc.put("documents", ii.getDocuments());
        return doc;
    }

    private InverseIndexEntry createModel(Document doc) {
        List<DocumentAppearancesPair> documents = new ArrayList<>();
        for (Object w : doc.get("documents", docClazz))
            documents.add(
                    DocumentAppearancesPair.builder()
                            .document(((Document) w).get("document").toString())
                            .counter(Double.parseDouble(((Document) w).get("counter").toString()))
                            .build()
            );

        return InverseIndexEntry.builder()
                ._id(doc.get("_id").toString())
                .documents(documents)
                .word((String) doc.get("word"))
                .build();
    }


    public final String COLLECTION_NAME = "inverseIndex";
    private CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();
    private MongoClient mongoClient = MongoClients.create(settings);
    private final MongoDatabase db = mongoClient.getDatabase("RIW");
    private final MongoCollection collection = db.getCollection(COLLECTION_NAME);
    private final Class<? extends List> docClazz = new ArrayList<Document>().getClass();

}
