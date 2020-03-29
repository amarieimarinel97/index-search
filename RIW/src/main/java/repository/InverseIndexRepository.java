package repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import model.InverseIndexEntry;
import model.utils.DocumentAppearancesPair;
import model.utils.WordAppearancesPair;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

public class InverseIndexRepository {

    public static List<InverseIndexEntry> getAll() {
        List<InverseIndexEntry> result = new ArrayList<>();
        MongoCursor cursor = collection.find().iterator();
        while (cursor.hasNext()) {
            Document obj = (Document) cursor.next();
            result.add(createModel(obj));
        }
        return result;
    }

    public static void delete(String word) {
        collection.deleteOne(Filters.eq("word", word));
    }

    public static void deleteAll() {
        collection.deleteMany(new BasicDBObject());
    }

    public static void add(InverseIndexEntry ii) {
        collection.insertOne(createDBObject(ii));
    }

    public static InverseIndexEntry get(String word) {
        return createModel(Objects.requireNonNull(db.getCollection(COLLECTION_NAME)
                .find(Filters.eq("word", word)).first()));
    }


    private static Document createDBObject(InverseIndexEntry ii) {
        Document doc = new Document();
        doc.put("word", ii.getWord());
        doc.put("documents", ii.getDocuments());
        return doc;
    }

    private static InverseIndexEntry createModel(Document doc) {
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


    public static final String COLLECTION_NAME = "inverseIndex";
    private static CodecRegistry pojoCodecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
            fromProviders(PojoCodecProvider.builder().automatic(true).build()));
    private static MongoClientSettings settings = MongoClientSettings.builder()
            .codecRegistry(pojoCodecRegistry)
            .build();
    private static MongoClient mongoClient = MongoClients.create(settings);
    private static final MongoDatabase db = mongoClient.getDatabase("RIW");
    private static final MongoCollection collection = db.getCollection(COLLECTION_NAME);
    private final static Class<? extends List> docClazz = new ArrayList<Document>().getClass();

}
