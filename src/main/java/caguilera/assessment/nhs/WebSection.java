package caguilera.assessment.nhs;

import java.util.Set;

/**
 * Holds basic information of a section of a website
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 * 
 * @param <T>
 *            any {@link Website}
 * @param <U>
 *            the type of the pages of a {@link WebSection}
 */
public interface WebSection<T extends Website, U extends WebPage<T>> {

	String getTitle();

	String getUrl();

	Set<U> getPages();
}
