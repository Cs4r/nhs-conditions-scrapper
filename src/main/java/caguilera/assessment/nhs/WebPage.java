package caguilera.assessment.nhs;

/**
 * Holds basic information about a web page of a {@link Website}
 * 
 * @author Cesar Aguilera
 * 
 * @param <T>
 *            any {@link Website}
 *
 */
public interface WebPage<T extends Website<?>> {

	String getUrl();

	String getTitle();

	String getContent();
}
