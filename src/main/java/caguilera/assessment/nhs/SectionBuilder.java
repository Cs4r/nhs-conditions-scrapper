package caguilera.assessment.nhs;

import java.util.Optional;

/**
 * Builds {@link WebSection}s of a {@link Website}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 * 
 */
public interface SectionBuilder<T extends WebSection<?>> {

	/**
	 * Given the URL of a web section, constructs a {@link WebSection}
	 * 
	 * @param sectionUrl
	 *            the link of the section
	 * @return an optional containing the {@link WebSection} if sectionUrl can
	 *         be accessed and read properly, {@link Optional#empty()} otherwise
	 */
	Optional<T> build(String sectionUrl);
}
