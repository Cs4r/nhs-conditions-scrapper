package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.ParametersValidator.throwIfAnyIsNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * Very naive implementation of {@link PagesRepository} for the
 * {@link NhsWebsite}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 */
@Repository
public class NhsPagesRepository implements PagesRepository<NhsWebPage> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NhsPagesRepository.class);

	private static final String DB_URI = "";
	private static final String DATA_BASE_NAME = "";
	private static final String COLLECTION_NAME = "pages";
	private final MongoCollection<Document> collection;
	private MongoClient mongoClient;

	public NhsPagesRepository() {
		MongoClientURI mongoClientURI = new MongoClientURI(DB_URI);
		mongoClient = new MongoClient(mongoClientURI);
		MongoDatabase database = mongoClient.getDatabase(DATA_BASE_NAME);
		collection = database.getCollection(COLLECTION_NAME);
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
		LOGGER.info("Inserted the page: {}", page.getTitle());
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
		LOGGER.info("Inserted {} pages in bulk", documents.size());
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

		LOGGER.info("Returning {} pages for the query '{}'", results.size(), query);

		return results;
	}

	private NhsWebPage getPageFromMongoDocument(Document document) {
		String title = (String) document.get("title");
		String url = (String) document.get("url");
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
