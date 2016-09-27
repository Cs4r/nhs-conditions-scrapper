package caguilera.assessment.nhs;

import java.util.Optional;

/**
 * Builds {@link WebPage}s of a {@link Website}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 * 
 * @param <T>
 *            a page of a {@link Website}
 */
public interface PageBuilder<T extends WebPage<?>> {

	/**
	 * Given the URL of a web page, constructs a {@link WebPage}
	 * 
	 * @param pageUrl
	 *            the link of the page
	 * @return an optional containing the {@link WebPage} if pageUrl can be
	 *         accessed and read properly, {@link Optional#empty()} otherwise
	 */
	Optional<T> build(String pageUrl);
}
