package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.FilesHelper.getFileContent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Unit tests for {@link NhsWebsiteBuilder}
 * 
 * @author Cesar Aguilera
 *
 */
public class NhsWebsiteBuilderTest {

	private static final String TEST_DATA_PATH = "caguilera/assessment/nhs/testdata/website/";

	private static final String DUMMY_URL = "A DUMMY URL";
	private NhsWebsiteBuilder siteBuilder;
	private NhsSectionBuilder sectionBuilder;

	@Before
	public void setUp() {
		sectionBuilder = mock(NhsSectionBuilder.class);
		siteBuilder = new NhsWebsiteBuilder(sectionBuilder);
		when(sectionBuilder.build(anyString())).thenAnswer(new Answer<Optional<NhsWebSection>>() {

			@Override
			public Optional<NhsWebSection> answer(InvocationOnMock invocation) throws Throwable {
				String pageUrl = (String) invocation.getArguments()[0];
				return Optional.of(NhsWebSection.of(pageUrl, pageUrl, Collections.emptySet()));
			}
		});
	}

	@Test
	public void buildReturnsCorrectWebsite_withMockedData() {
		siteBuilder.testMode = true;
		mockDocument(TEST_DATA_PATH + "conditions.html");

		Optional<NhsWebsite> optinalWebSite = siteBuilder.build();

		assertTrue(optinalWebSite.isPresent());
		NhsWebsite webPage = optinalWebSite.get();
		assertEquals(27, webPage.getSections().size());
	}

	@Test
	@Ignore
	public void buildReturnsCorrectWebsite_withRealData() {
		String pageUrl = "http://www.nhs.uk/conditions/Pages/hub.aspx";
		Optional<NhsWebsite> optinalWebsite = siteBuilder.build();

		assertTrue(optinalWebsite.isPresent());
		NhsWebsite webPage = optinalWebsite.get();
		assertEquals(27, webPage.getSections().size());
		assertEquals(pageUrl, webPage.getUrl());
	}

	private void mockDocument(String url) {
		String fileContent = getFileContent(url);
		siteBuilder.document = Jsoup.parse(fileContent);
	}

}
