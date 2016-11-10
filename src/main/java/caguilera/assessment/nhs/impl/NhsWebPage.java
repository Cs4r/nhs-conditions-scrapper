package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.ParametersValidator.throwIfAnyIsNull;

import caguilera.assessment.nhs.WebPage;

/**
 * Holds information about a web page of the {@link NhsWebsite}
 * 
 * @author Cesar Aguilera
 *
 */
public class NhsWebPage implements WebPage<NhsWebsite> {

	private final String url;
	private final String title;
	private final String content;

	/**
	 * Creates instances of {@link NhsWebPage}
	 * 
	 * @param title
	 *            the page's title
	 * @param url
	 *            the page's url
	 * @param content
	 *            the page's content
	 * 
	 * @throws IllegalArgumentException
	 *             if any parameter is null
	 * @return an instance of {@link NhsWebPage}
	 */
	public static NhsWebPage of(String title, String url, String content) {
		throwIfAnyIsNull(url, title, content);
		return new NhsWebPage(url, title, content);
	}

	private NhsWebPage(String url, String title, String content) {
		this.url = url;
		this.title = title;
		this.content = content;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getContent() {
		return content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
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
		NhsWebPage other = (NhsWebPage) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
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
