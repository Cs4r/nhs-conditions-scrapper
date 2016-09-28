package caguilera.assessment.nhs.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import caguilera.assessment.nhs.SectionBuilder;
import caguilera.assessment.nhs.WebPage;

/**
 * A {@link SectionBuilder} for {@link NhsWebSection}s
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
@Component
public class NhsSectionBuilder implements SectionBuilder<NhsWebSection> {

	private static final String WEBSITE = "http://www.nhs.uk";
	private static final String MINIMUM_PAGE_PREFIX = "/conditions/";
	private static final String FULL_PAGE_PREFIX = "http://www.nhs.uk/conditions/";

	@Autowired
	private NhsPageBuilder pageBuilder;

	// These two fields are only used for testing purposes
	boolean testMode;
	Document document;

	// Constructor for testing purposes
	NhsSectionBuilder(NhsPageBuilder pageBuilder) {
		super();
		this.pageBuilder = pageBuilder;
	}

	@Override
	public Optional<NhsWebSection> build(String sectionUrl) {
		try {
			Document document = getDocument(sectionUrl);
			String title = getSectionTitle(document);
			String url = getSectionUrl(document);
			Set<String> links = getPageLinks(document);

			Set<WebPage<NhsWebsite>> pages = links.stream().parallel().map(pageBuilder::build)
					.filter(Optional::isPresent).map(Optional::get)
					.collect(Collectors.toCollection(() -> Collections.newSetFromMap(new ConcurrentHashMap<>())));

			return Optional.of(NhsWebSection.of(title, url, pages));

		} catch (Exception exception) {
		}

		return Optional.empty();
	}

	private Document getDocument(String sectionUrl) throws IOException {
		if (testMode) {
			return document;
		}
		return Jsoup.connect(sectionUrl).get();
	}

	private String getSectionTitle(Document document) {
		return document.title().replace("Health A-Z - ", "").trim();
	}

	private String getSectionUrl(Document document) {
		return document.location();
	}

	private Set<String> getPageLinks(Document document) {
		Set<String> links = new HashSet<>();

		for (Element element : document.select("#haz-mod5 li>a")) {
			String possibleLink = element.attr("href").toLowerCase();
			if (possibleLink.startsWith(FULL_PAGE_PREFIX)) {
				links.add(possibleLink);
			} else if (possibleLink.startsWith(MINIMUM_PAGE_PREFIX)) {
				links.add(WEBSITE + possibleLink);
			}
		}

		return links;
	}

}
