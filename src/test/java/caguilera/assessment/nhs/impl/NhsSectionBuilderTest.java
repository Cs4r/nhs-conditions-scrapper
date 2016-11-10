package caguilera.assessment.nhs.impl;

import static caguilera.assessment.nhs.impl.FilesHelper.getFileContent;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * Unit tests for {@link NhsSectionBuilder}
 * 
 * @author Cesar Aguilera
 *
 */
public class NhsSectionBuilderTest {

	private static final String TEST_DATA_PATH = "caguilera/assessment/nhs/testdata/section/";

	private static final String DUMMY_URL = "A DUMMY URL";
	private NhsSectionBuilder sectionBuilder;
	private NhsPageBuilder pageBuilder;

	@Before
	public void setUp() {
		pageBuilder = mock(NhsPageBuilder.class);
		sectionBuilder = new NhsSectionBuilder(pageBuilder);
		when(pageBuilder.build(anyString())).thenAnswer(new Answer<Optional<NhsWebSection>>() {

			@Override
			public Optional<NhsWebSection> answer(InvocationOnMock invocation) throws Throwable {
				String pageUrl = (String) invocation.getArguments()[0];
				return Optional.of(NhsWebSection.of(pageUrl, pageUrl, Collections.emptySet()));
			}
		});
	}

	@Test
	public void buildReturnsEmptyOptionalWhenNullUrlIsGiven() {
		sectionBuilder.testMode = true;
		assertEquals(Optional.empty(), sectionBuilder.build(null));
	}

	@Test
	public void buildReturnsEmptyOptionalWhenItCannotParseTheContent() throws IOException {
		Optional<NhsWebSection> optionalSection = sectionBuilder.build("https//google.es");

		assertEquals(Optional.empty(), optionalSection);
	}

	@Test
	public void buildReturnsEmptyOptionalWhenItCannotAccessToTheURL() throws IOException {
		Optional<NhsWebSection> optionalSection = sectionBuilder.build(DUMMY_URL);

		assertEquals(Optional.empty(), optionalSection);
	}

	@Test
	public void buildReturnsCorrectSectionForLetterC_withMockedData() {
		sectionBuilder.testMode = true;
		mockDocument(TEST_DATA_PATH + "SectionC.html");

		Optional<NhsWebSection> optionalSection = sectionBuilder.build(DUMMY_URL);

		assertTrue(optionalSection.isPresent());
		NhsWebSection section = optionalSection.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "SectionC.title"), section.getTitle());
		assertEquals(225, section.getPages().size());
		assertEquals("", section.getUrl());
	}

	@Test
	@Ignore
	public void buildReturnsCorrectSectionForLetterC_withRealData() {
		String sectionUrl = "http://www.nhs.uk/Conditions/Pages/BodyMap.aspx?Index=C";
		Optional<NhsWebSection> optionalSection = sectionBuilder.build(sectionUrl);

		assertTrue(optionalSection.isPresent());
		NhsWebSection section = optionalSection.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "SectionC.title"), section.getTitle());
		assertEquals(225, section.getPages().size());
		assertEquals(sectionUrl, section.getUrl());
	}

	@Test
	public void buildReturnsCorrectSectionForLetterW_withMockedData() {
		sectionBuilder.testMode = true;
		mockDocument(TEST_DATA_PATH + "SectionW.html");

		Optional<NhsWebSection> optionalSection = sectionBuilder.build(DUMMY_URL);

		assertTrue(optionalSection.isPresent());
		NhsWebSection section = optionalSection.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "SectionW.title"), section.getTitle());
		assertEquals(25, section.getPages().size());
		assertEquals("", section.getUrl());
	}

	@Test
	@Ignore
	public void buildReturnsCorrectSectionForLetterW_withRealData() {
		String sectionUrl = "http://www.nhs.uk/Conditions/Pages/BodyMap.aspx?Index=W";
		Optional<NhsWebSection> optionalSection = sectionBuilder.build(sectionUrl);

		assertTrue(optionalSection.isPresent());
		NhsWebSection section = optionalSection.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "SectionW.title"), section.getTitle());
		assertEquals(25, section.getPages().size());
		assertEquals(sectionUrl, section.getUrl());
	}

	@Test
	public void buildReturnsCorrectSectionForLetterF_withMockedData() {
		sectionBuilder.testMode = true;
		mockDocument(TEST_DATA_PATH + "SectionF.html");

		Optional<NhsWebSection> optionalSection = sectionBuilder.build(DUMMY_URL);

		assertTrue(optionalSection.isPresent());
		NhsWebSection section = optionalSection.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "SectionF.title"), section.getTitle());
		assertEquals(63, section.getPages().size());
		assertEquals("", section.getUrl());
	}

	@Test
	@Ignore
	public void buildReturnsCorrectSectionForLetterF_withRealData() {
		String sectionUrl = "http://www.nhs.uk/Conditions/Pages/BodyMap.aspx?Index=F";
		Optional<NhsWebSection> optionalSection = sectionBuilder.build(sectionUrl);

		assertTrue(optionalSection.isPresent());
		NhsWebSection section = optionalSection.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "SectionF.title"), section.getTitle());
		assertEquals(63, section.getPages().size());
		assertEquals(sectionUrl, section.getUrl());
	}

	private void mockDocument(String url) {
		String fileContent = getFileContent(url);
		sectionBuilder.document = Jsoup.parse(fileContent);
	}

}
