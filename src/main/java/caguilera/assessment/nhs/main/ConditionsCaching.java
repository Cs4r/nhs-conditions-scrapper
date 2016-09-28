package caguilera.assessment.nhs.main;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import caguilera.assessment.nhs.PagesRepository;
import caguilera.assessment.nhs.WebsiteBuilder;
import caguilera.assessment.nhs.impl.NhsWebPage;
import caguilera.assessment.nhs.impl.NhsWebsite;

/**
 * A service that scrapes the nhsChoice website and caches the condition pages
 * and their subpages content in a database
 * 
 * @author Cesar Aguilera <cesar.aguilera.p@gmail.com>
 *
 */
public class ConditionsCaching {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext(new String[] { "spring-conf.xml" });

		WebsiteBuilder<NhsWebsite> websiteBuilder = (WebsiteBuilder<NhsWebsite>) context.getBean(WebsiteBuilder.class);

		Optional<NhsWebsite> optionalWebsite = websiteBuilder.build();

		if (optionalWebsite.isPresent()) {

			NhsWebsite website = optionalWebsite.get();

			//@formatter:off
			List<NhsWebPage> pages = website.getSections().parallelStream()
					.flatMap(i -> i.getPages().parallelStream())
					.map(i -> (NhsWebPage) i)
					.collect(Collectors.toList());
			//@formatter:on

			PagesRepository<NhsWebPage> repository = (PagesRepository<NhsWebPage>) context
					.getBean(PagesRepository.class);

			repository.bulkInsert(pages);
		}
	}

}
