package caguilera.assessment.nhs.impl;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Unit tests for {@link NhsWebsite}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class NhsWebsiteTest {

	private static final String URL = "URL";
	private Set<NhsWebSection> sections;
	private NhsWebsite website;

	@Before
	public void setUp() {
		sections = Sets.newHashSet(mock(NhsWebSection.class), mock(NhsWebSection.class));
		website = NhsWebsite.of(URL, sections);
	}

	@Test(expected = IllegalArgumentException.class)
	public void ofThrowsIllegalArgumentExceptionIfUrlisNull() {
		NhsWebsite.of(null, sections);
	}

	public void ofThrowsIllegalArgumentExceptionIfSectionsisNull() {
		NhsWebsite.of(URL, null);
	}

	@Test
	public void getUrlReturnsUrl() {
		assertEquals(URL, website.getUrl());
	}

	@Test
	public void getSectionsReturnsSections() {
		assertEquals(sections, website.getSections());
	}
}
