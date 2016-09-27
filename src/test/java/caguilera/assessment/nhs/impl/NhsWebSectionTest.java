package caguilera.assessment.nhs.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

import caguilera.assessment.nhs.WebPage;

/**
 * Unit tests for {@link NhsWebSection}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class NhsWebSectionTest {

	private static final String URL = "URL";
	private static final String TITLE = "TITLE";
	private Set<WebPage<NhsWebsite>> pages;
	private NhsWebSection nhsWebSection;

	@SuppressWarnings("unchecked")
	@Before
	public void setUp() {
		pages = Sets.newHashSet(mock(NhsWebPage.class), mock(NhsWebPage.class));
		nhsWebSection = NhsWebSection.of(TITLE, URL, pages);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfUrlisNull() {
		NhsWebSection.of(TITLE, null, pages);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfTitleisNull() {
		NhsWebSection.of(null, URL, pages);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfPagesisNull() {
		NhsWebSection.of(TITLE, URL, null);
	}

	@Test
	public void getUrlReturnsUrl() {
		assertEquals(URL, nhsWebSection.getUrl());
	}

	@Test
	public void getTitleReturnsTitle() {
		assertEquals(TITLE, nhsWebSection.getTitle());
	}

	@Test
	public void getPagesReturnsPages() {
		assertEquals(pages, nhsWebSection.getPages());
	}

}
