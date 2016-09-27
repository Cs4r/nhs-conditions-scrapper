package caguilera.assessment.nhs.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Unit tests for {@link NhsPageBuilder}
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class NhsPageBuilderTest {
	private static final String TEST_DATA_PATH = "caguilera/assessment/nhs/testdata/";
	private NhsPageBuilder pageBuilder;
	private static final String DUMMY_URL = "DUMMY URL";

	@Before
	public void setUp() {
		pageBuilder = new NhsPageBuilder();
	}

	@Test
	public void buildReturnsEmptyOptionalWhenItCannotParseTheContent() throws IOException {
		Optional<NhsWebPage> optionalWebPage = pageBuilder.build("https//google.es");

		assertEquals(Optional.empty(), optionalWebPage);
	}
	
	@Test
	public void buildReturnsEmptyOptionalWhenItCannotAccessToTheURL() throws IOException {
		Optional<NhsWebPage> optionalWebPage = pageBuilder.build(DUMMY_URL);

		assertEquals(Optional.empty(), optionalWebPage);
	}

	@Test
	public void buildReturnsCorrectPageForAbdominalAorticAneurysm_withMockedData() throws IOException {
		pageBuilder.testMode = true;
		mockDocument(TEST_DATA_PATH + "AbdominalAorticAneurysm.html");

		Optional<NhsWebPage> optionalWebPage = pageBuilder.build(DUMMY_URL);

		assertTrue(optionalWebPage.isPresent());
		NhsWebPage webPage = optionalWebPage.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "AbdominalAorticAneurysm.title"), webPage.getTitle());
		assertEquals(getFileContent(TEST_DATA_PATH + "AbdominalAorticAneurysm.content"), webPage.getContent());
		assertEquals("", webPage.getUrl());
	}

	@Test
	@Ignore
	public void buildReturnsCorrectPageForAbdominalAorticAneurysm_withRealData() throws IOException {
		String url = "http://www.nhs.uk/conditions/Repairofabdominalaneurysm/Pages/Introduction.aspx";
		Optional<NhsWebPage> optionalWebPage = pageBuilder.build(url);
		
		NhsWebPage webPage = optionalWebPage.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "AbdominalAorticAneurysm.title"), webPage.getTitle());
		assertEquals(getFileContent(TEST_DATA_PATH + "AbdominalAorticAneurysm.content"), webPage.getContent());
		assertEquals(url, webPage.getUrl());
	}
	
	
	@Test
	public void buildReturnsCorrectPageForZikaVirus_withMockedData() throws IOException {
		pageBuilder.testMode = true;
		mockDocument(TEST_DATA_PATH + "ZikaVirus.html");

		Optional<NhsWebPage> optionalWebPage = pageBuilder.build(DUMMY_URL);

		assertTrue(optionalWebPage.isPresent());
		NhsWebPage webPage = optionalWebPage.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "ZikaVirus.title"), webPage.getTitle());
		assertEquals(getFileContent(TEST_DATA_PATH + "ZikaVirus.content"), webPage.getContent());
		assertEquals("", webPage.getUrl());
	}

	@Test
	@Ignore
	public void buildReturnsCorrectPageForZikaVirus_withRealData() throws IOException {
		String url = "http://www.nhs.uk/conditions/zika-virus/Pages/Introduction.aspx";
		Optional<NhsWebPage> optionalWebPage = pageBuilder.build(url);
		
		NhsWebPage webPage = optionalWebPage.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "ZikaVirus.title"), webPage.getTitle());
		assertEquals(getFileContent(TEST_DATA_PATH + "ZikaVirus.content"), webPage.getContent());
		assertEquals(url, webPage.getUrl());
	}
	
	
	@Test
	public void buildReturnsCorrectPageForLabialFusion_withMockedData() throws IOException {
		pageBuilder.testMode = true;
		mockDocument(TEST_DATA_PATH + "LabialFusion.html");

		Optional<NhsWebPage> optionalWebPage = pageBuilder.build(DUMMY_URL);

		assertTrue(optionalWebPage.isPresent());
		NhsWebPage webPage = optionalWebPage.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "LabialFusion.title"), webPage.getTitle());
		assertEquals(getFileContent(TEST_DATA_PATH + "LabialFusion.content"), webPage.getContent());
		assertEquals("", webPage.getUrl());
	}

	@Test
	@Ignore
	public void buildReturnsCorrectPageForLabialFusion_withRealData() throws IOException {
		String url = "http://www.nhs.uk/conditions/labial-fusion/Pages/Introduction.aspx";
		Optional<NhsWebPage> optionalWebPage = pageBuilder.build(url);
		
		NhsWebPage webPage = optionalWebPage.get();
		assertEquals(getFileContent(TEST_DATA_PATH + "LabialFusion.title"), webPage.getTitle());
		assertEquals(getFileContent(TEST_DATA_PATH + "LabialFusion.content"), webPage.getContent());
		assertEquals(url, webPage.getUrl());
	}
	

	private void mockDocument(String url) {
		String fileContent = getFileContent(url);
		pageBuilder.document = Jsoup.parse(fileContent);
	}

	private String getFileContent(String fileName) {
		String result = "";

		ClassLoader classLoader = getClass().getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.substring(0, result.length() - 1);

	}

}
