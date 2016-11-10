package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.ParametersValidator.throwIfAnyIsNull;

import java.util.HashSet;
import java.util.Set;

import caguilera.assessment.nhs.WebPage;
import caguilera.assessment.nhs.WebSection;

/**
 * Holds information of a section of the {@link NhsWebsite}
 * 
 * @author Cesar Aguilera
 *
 */
public class NhsWebSection implements WebSection<NhsWebsite> {

	private final Set<WebPage<NhsWebsite>> pages;
	private final String url;
	private final String title;

	/**
	 * Creates instances of {@link NhsWebPage}
	 * 
	 * @param title
	 *            the page's title
	 * @param url
	 *            the page's url
	 * @param pages
	 *            the section's pages
	 * @throws IllegalArgumentException
	 *             if any parameter is null
	 * @return an instance of {@link NhsWebSection}
	 */
	public static NhsWebSection of(String title, String url, Set<WebPage<NhsWebsite>> pages) {
		throwIfAnyIsNull(url, title, pages);
		return new NhsWebSection(title, url, pages);
	}

	private NhsWebSection(String title, String url, Set<WebPage<NhsWebsite>> pages) {
		this.title = title;
		this.url = url;
		this.pages = new HashSet<>(pages);
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public Set<WebPage<NhsWebsite>> getPages() {
		return new HashSet<>(pages);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((pages == null) ? 0 : pages.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		NhsWebSection other = (NhsWebSection) obj;
		if (pages == null) {
			if (other.pages != null)
				return false;
		} else if (!pages.equals(other.pages))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
}
