package caguilera.assessment.nhs.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;

/**
 * Unit tests for {@link NhsPagesRepository}
 * 
 * @author Cesar Aguilera
 *
 */
public class NhsPagesRepositoryTest {

	private MongoCollection<Document> collection;
	private NhsPagesRepository repository;
	private NhsWebPage nhsWebPage;
	private MongoClient mongoClient;

	@Before
	public void setUp() {
		mongoClient = mock(MongoClient.class);
		collection = mock(MongoCollection.class);
		repository = new NhsPagesRepository(mongoClient, collection);
		nhsWebPage = mockWebPage();
	}

	@Test(expected = IllegalArgumentException.class)
	public void insertThrowsIllegalArgumentExceptionWhenANullPageIsGiven() {
		repository.insert(null);
	}

	@Test
	public void insertDelegatesToMongoCollection() {
		repository.insert(nhsWebPage);
		verify(collection).insertOne(any());
	}

	@Test(expected = IllegalArgumentException.class)
	public void bulkInsertThrowsIllegalArgumentExceptionWhenANullPageIsGiven() {
		repository.bulkInsert(null);
	}

	@Test
	public void bulkInsertDelegatesToMongoCollection() {
		repository.bulkInsert(Sets.newHashSet(nhsWebPage, nhsWebPage));
		verify(collection).insertMany(any());
	}

	@Test(expected = IllegalArgumentException.class)
	public void retrieveRelatedPagesThrowsIllegalArgumentExceptionWhenANullPageIsGiven() {
		repository.retrieveRelatedPages(null);
	}

	@Test
	public void retrieveRelatedPagesToMongoCollection() {
		repository.retrieveRelatedPages("a query");
		FindIterable<Document> value = mock(FindIterable.class);
		when(collection.find(any(Bson.class))).thenReturn(value);
		verify(collection).find(any(Bson.class));
	}

	private NhsWebPage mockWebPage() {
		NhsWebPage webpage = mock(NhsWebPage.class);

		when(webpage.getTitle()).thenReturn("title");
		when(webpage.getContent()).thenReturn("content");
		when(webpage.getUrl()).thenReturn("content");

		return webpage;
	}

}
