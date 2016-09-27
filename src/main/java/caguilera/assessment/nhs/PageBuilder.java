package caguilera.assessment.nhs;

import java.util.Optional;

/**
 * Builds {@link WebPage}s of a {@link Website}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 * 
 * @param <R>
 *            any {@link Website}
 *
 * @param <R>
 *            a page of the {@link Website}
 */
public interface PageBuilder<R extends Website, T extends WebPage<R>> {

	/**
	 * Given the URL of a web page, constructs a {@link WebPage}
	 * 
	 * @param pageUrl
	 *            the link of the page
	 * @return a {@link WebPage} if pageUrl can be accessed and read properly,
	 *         {@link Optional#empty()} otherwise
	 */
	Optional<T> build(String pageUrl);
}
