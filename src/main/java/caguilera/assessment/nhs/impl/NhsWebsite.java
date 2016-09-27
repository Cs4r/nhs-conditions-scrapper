package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.ParametersValidator.throwIfAnyIsNull;

import java.util.Set;

import caguilera.assessment.nhs.WebSection;
import caguilera.assessment.nhs.Website;

/**
 * The http://www.nhs.uk/Conditions/Pages/ site
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class NhsWebsite implements Website<NhsWebsite> {

	private Set<WebSection<NhsWebsite>> sections;
	private String url;

	/**
	 * Creates instances of {@link NhsWebsite}
	 * 
	 * @param url
	 *            the page's url
	 * @param sections
	 *            the page's sections
	 * @return an instance of {@link NhsWebsite}
	 */
	static NhsWebsite of(String url, Set<WebSection<NhsWebsite>> sections) {
		throwIfAnyIsNull(sections, url);
		return new NhsWebsite(sections, url);
	}

	private NhsWebsite(Set<WebSection<NhsWebsite>> sections, String url) {
		super();
		this.sections = sections;
		this.url = url;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public Set<WebSection<NhsWebsite>> getSections() {
		return sections;
	}
}
