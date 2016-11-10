package caguilera.assessment.nhs;

import java.util.Optional;

/**
 * Builds {@link WebSite} objects
 * 
 * @author Cesar Aguilera
 * 
 * @param <T>
 *            a page of a {@link Website}
 */
public interface WebsiteBuilder<T extends Website<T>> {

	/**
	 * Returns a scraped representation of a web site
	 * 
	 * @return an optional containing the {@link WebSite} if the web site can be
	 *         accessed and read properly, {@link Optional#empty()} otherwise
	 */
	Optional<T> build();
}
