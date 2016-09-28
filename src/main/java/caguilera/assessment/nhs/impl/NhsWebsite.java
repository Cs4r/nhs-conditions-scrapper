package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.ParametersValidator.throwIfAnyIsNull;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
	public static NhsWebsite of(String url, Set<NhsWebSection> sections) {
		throwIfAnyIsNull(sections, url);
		return new NhsWebsite(sections, url);
	}

	private NhsWebsite(Set<NhsWebSection> sections, String url) {
		super();
		this.sections = sections.stream().map(i -> (WebSection<NhsWebsite>) i).collect(Collectors.toSet());
		this.url = url;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public Set<WebSection<NhsWebsite>> getSections() {
		return new HashSet<>(sections);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((sections == null) ? 0 : sections.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhsWebsite other = (NhsWebsite) obj;
		if (sections == null) {
			if (other.sections != null)
				return false;
		} else if (!sections.equals(other.sections))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
