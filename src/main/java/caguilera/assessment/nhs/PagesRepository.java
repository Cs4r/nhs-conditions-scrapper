package caguilera.assessment.nhs;

import java.util.Collection;

/**
 * Provides some specific data operations on {@link WebPage}s of a
 * {@link Website} without exposing the underlying details of the database
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 * @param <T>
 *            a {@link WebPage} of a {@link Website}
 */
public interface PagesRepository<T extends WebPage<?>> {

	void insert(T page);

	void bulkInsert(Collection<T> pages);

	Collection<T> retrieveRelatedPages(String key);
}
