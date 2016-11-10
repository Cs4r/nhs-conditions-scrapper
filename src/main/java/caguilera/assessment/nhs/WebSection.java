package caguilera.assessment.nhs;

import java.util.Set;

/**
 * Holds basic information of a section of a website
 * 
 * @author Cesar Aguilera
 * 
 * @param <T>
 *            any {@link Website}
 */
public interface WebSection<T extends Website<T>> {

	String getTitle();

	String getUrl();

	Set<WebPage<T>> getPages();
}
