package caguilera.assessment.nhs.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for {@link NhsWebPage}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class NhsWebPageTest {

	private static final String url = "url";
	private static final String title = "title";
	private static final String content = "content";
	private NhsWebPage nhswebpage;

	@Before
	public void setUp() {
		nhswebpage = NhsWebPage.of(title, url, content);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfUrlisNull() {
		NhsWebPage.of(title, null, content);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfTitleisNull() {
		NhsWebPage.of(null, url, content);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfContentisNull() {
		NhsWebPage.of(title, url, null);
	}
	
	@Test
	public void getUrlReturnsUrl() {
		assertEquals(url, nhswebpage.getUrl());
	}

	@Test
	public void getTitleReturnsTitle() {
		assertEquals(title, nhswebpage.getTitle());
	}
	

	@Test
	public void getContentReturnsContent() {
		assertEquals(content, nhswebpage.getContent());
	}

}
