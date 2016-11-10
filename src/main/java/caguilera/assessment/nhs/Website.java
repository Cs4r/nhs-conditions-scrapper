package caguilera.assessment.nhs;

import java.util.Set;

/**
 * Represents a web site that can be scraped
 * 
 * @author Cesar Aguilera
 *
 */
public interface Website<R extends Website<R>> {

	String getUrl();

	Set<WebSection<R>> getSections();
}