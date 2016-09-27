package caguilera.assessment.nhs.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Unit tests for {@link NhsWebSection}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class NhsWebSectionTest {

	private static final String url = "URL";
	private static final String title = "TITLE";
	private Set<NhsWebPage> pages;
	private NhsWebSection nhsWebSection;

	@Before
	public void setUp() {
		pages = Sets.newHashSet(mock(NhsWebPage.class), mock(NhsWebPage.class));
		nhsWebSection = NhsWebSection.of(title, url, pages);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfUrlisNull() {
		NhsWebSection.of(title, null, pages);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfTitleisNull() {
		NhsWebSection.of(null, url, pages);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfPagesisNull() {
		NhsWebSection.of(title, url, null);
	}

	@Test
	public void getUrlReturnsUrl() {
		assertEquals(url, nhsWebSection.getUrl());
	}

	@Test
	public void getTitleReturnsTitle() {
		assertEquals(title, nhsWebSection.getTitle());
	}

	@Test
	public void getPagesReturnsPages() {
		assertEquals(pages, nhsWebSection.getPages());
	}

}
