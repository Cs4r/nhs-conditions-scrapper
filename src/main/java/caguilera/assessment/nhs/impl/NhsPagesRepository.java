package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.ParametersValidator.throwIfAnyIsNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import caguilera.assessment.nhs.PagesRepository;

/**
 * Naive implementation of {@link PagesRepository} for the {@link NhsWebsite}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 */
@Repository
public class NhsPagesRepository implements PagesRepository<NhsWebPage> {

	private static final String DATA_BASE_NAME = "";
	private static final String DB_URI = "";
	private final MongoCollection<Document> collection;
	private MongoClient mongoClient;

	public NhsPagesRepository() {
		MongoClientURI mongoClientURI = new MongoClientURI(DB_URI);
		mongoClient = new MongoClient(mongoClientURI);
		MongoDatabase database = mongoClient.getDatabase(DATA_BASE_NAME);
		collection = database.getCollection("pages");
	}

	// Constructor to be used only in test
	NhsPagesRepository(MongoClient mongoClient, MongoCollection<Document> collection) {
		this.mongoClient = mongoClient;
		this.collection = collection;
	}

	@Override
	public void insert(NhsWebPage page) {
		throwIfAnyIsNull(page);
		collection.insertOne(getMongoDocumentFromPage(page));
		System.out.println("Inserted page: " + page.getTitle());
	}

	private Document getMongoDocumentFromPage(NhsWebPage page) {
		Document document = new Document();
		document.put("title", page.getTitle());
		document.put("url", page.getUrl());
		document.put("content", page.getContent());
		return document;
	}

	@Override
	public void bulkInsert(Collection<NhsWebPage> pages) {
		throwIfAnyIsNull(pages);
		List<Document> documents = pages.parallelStream().map(page -> getMongoDocumentFromPage(page))
				.collect(Collectors.toList());
		collection.insertMany(documents);
	}

	@Override
	public Collection<NhsWebPage> retrieveRelatedPages(String query) {
		throwIfAnyIsNull(query);

		Set<NhsWebPage> results = new HashSet<>();

		FindIterable<Document> documents = collection
				.find(new BasicDBObject("$text", new BasicDBObject("$search", query)));

		if (documents != null) {
			try (MongoCursor<Document> cursor = documents.iterator()) {
				while (cursor.hasNext()) {
					NhsWebPage of = getPageFromMongoDocument(cursor.next());
					results.add(of);
				}
			} catch (Exception e) {
			}
		}

		return results;
	}

	private NhsWebPage getPageFromMongoDocument(Document document) {
		String title = (String) document.get("title");
		String url = (String) document.get("title");
		String content = (String) document.get("content");
		NhsWebPage of = NhsWebPage.of(title, url, content);
		return of;
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		mongoClient.close();
	}
}
