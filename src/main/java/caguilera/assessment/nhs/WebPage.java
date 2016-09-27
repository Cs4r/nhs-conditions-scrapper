package caguilera.assessment.nhs;

/**
 * Holds basic information about a web page of a {@link Website}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 * 
 * @param <T>
 *            any {@link Website}
 *
 */
public interface WebPage<T extends Website> {

	String getURL();

	String getTitle();

	String getContent();
}
