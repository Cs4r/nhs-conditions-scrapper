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

	private static String url = "URL";
	private static String title = "TITLE";
	private static String content = "CONTENT";
	private NhsWebPage nhswebpage;

	@Before
	public void setUp() {
		nhswebpage = NhsWebPage.of(url, title, content);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfUrlisNull() {
		NhsWebPage.of(null, title, content);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfTitleisNull() {
		NhsWebPage.of(url, null, content);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfContentisNull() {
		NhsWebPage.of(url, title, null);
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
