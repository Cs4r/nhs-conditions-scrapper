package caguilera.assessment.nhs;

import java.util.Optional;

/**
 * Builds {@link WebPage}s of a {@link Website}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 * 
 * @param <T>
 *            any {@link Website}
 *
 */
public interface PageBuilder<T extends Website> {

	/**
	 * Given the URL of a web page, constructs a {@link WebPage}
	 * 
	 * @param pageUrl
	 *            the link of the page
	 * @return a {@link WebPage} if pageUrl can be accessed and read properly,
	 *         {@link Optional#empty()} otherwise
	 */
	Optional<WebPage<T>> build(String pageUrl);
}
