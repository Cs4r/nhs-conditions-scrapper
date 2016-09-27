package caguilera.assessment.nhs.impl;

import java.io.IOException;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import caguilera.assessment.nhs.NhsWebsite;
import caguilera.assessment.nhs.PageBuilder;

/**
 * A {@link PageBuilder} for {@link NhsWebPage}s
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class NhsPageBuilder implements PageBuilder<NhsWebsite, NhsWebPage> {

	// These two fields are only used for testing purposes
	boolean testMode;
	Document document;

	@Override
	public Optional<NhsWebPage> build(String pageUrl) {
		try {
			Document doc = getDocument(pageUrl);
			Elements elements = doc.select(".main-content");

			if (onlyContainsOneMainSection(elements)) {
				Element page = elements.get(0);

				removeWebZoneLeftSection(page);
				removeReviewDatesSection(page);

				String url = doc.location();
				String title = removeHtmlSpacesAndTrim(getPageTitle(doc));
				String content = removeHtmlSpacesAndTrim(page.text());

				return Optional.of(NhsWebPage.of(url, title, content));
			}

		} catch (Exception e) {
			// In case of any exception we return an empty optional
		}

		return Optional.empty();
	}

	private String getPageTitle(Document doc) {
		return doc.title().replaceFirst("- NHS Choices", "");
	}

	private boolean onlyContainsOneMainSection(Elements elements) {
		return elements.size() == 1;
	}

	private void removeWebZoneLeftSection(Element page) {
		Element webZoneLeft = page.getElementById("webZoneLeft");
		
		if (webZoneLeft != null) {
			webZoneLeft.remove();
		}
	}

	private void removeReviewDatesSection(Element page) {
		Elements reviewDates = page.getElementsByClass("review-dates");

		if (reviewDates != null) {
			reviewDates.remove();
		}
	}

	private static String removeHtmlSpacesAndTrim(String text) {
		return text.replace("\u00a0", "").trim();
	}

	private Document getDocument(String pageUrl) throws IOException {
		if (testMode) {
			return document;
		}
		return Jsoup.connect(pageUrl).get();
	}

}
