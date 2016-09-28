package caguilera.assessment.nhs.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import caguilera.assessment.nhs.WebsiteBuilder;

/**
 * A builder for {@link NhsWebsite}s
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
@Component
public class NhsWebsiteBuilder implements WebsiteBuilder<NhsWebsite> {

	private static final Logger LOGGER = LoggerFactory.getLogger(NhsWebsiteBuilder.class);

	private static final String SECTION_PREFIX = "http://www.nhs.uk/conditions/pages/";
	private static final String NHS_WEB_SITE = "http://www.nhs.uk/Conditions/Pages/hub.aspx";

	@Autowired
	private NhsSectionBuilder sectionBuilder;

	// These two fields are only used for testing purposes
	boolean testMode;
	Document document;

	// Constructor for testing purposes
	NhsWebsiteBuilder(NhsSectionBuilder sectionBuilder) {
		super();
		this.sectionBuilder = sectionBuilder;
	}

	@Override
	public Optional<NhsWebsite> build() {
		try {
			Document document = getDocument(NHS_WEB_SITE);
			String url = getWebSiteUrl(document);
			Set<String> sectionLinks = getSectionLinks(document);

			Set<NhsWebSection> sections = sectionLinks.parallelStream().map(sectionBuilder::build)
					.filter(Optional::isPresent).map(Optional::get)
					.collect(Collectors.toCollection(() -> Collections.newSetFromMap(new ConcurrentHashMap<>())));

			LOGGER.info("Created website for the url: {}", NHS_WEB_SITE);

			return Optional.of(NhsWebsite.of(url, sections));

		} catch (Exception exception) {
		}

		return Optional.empty();
	}

	private Set<String> getSectionLinks(Document document) {
		return document.select("#haz-mod1 li>a").stream().map(element -> {
			String href = element.attr("href").toLowerCase();
			if (!href.contains(SECTION_PREFIX)) {
				href = SECTION_PREFIX + href;
			}
			return href;
		}).collect(Collectors.toSet());
	}

	private String getWebSiteUrl(Document document) {
		return document.location();
	}

	private Document getDocument(String siteUrl) throws IOException {
		if (testMode) {
			return document;
		}
		return Jsoup.connect(siteUrl).get();
	}

}
