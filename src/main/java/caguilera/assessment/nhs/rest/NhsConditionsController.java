package caguilera.assessment.nhs.rest;

import java.util.Collection;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import caguilera.assessment.nhs.PagesRepository;
import caguilera.assessment.nhs.impl.NhsWebPage;

/**
 * A rest controller that points an user to the most appropriate pages for
 * requests like “what are the symptoms of cancer?” “treatments for headaches”
 * 
 * @author Cesar Aguilera
 */
@RestController
public class NhsConditionsController {

	private static final Logger LOGGER = LoggerFactory.getLogger(NhsConditionsController.class);

	@Autowired
	private PagesRepository<NhsWebPage> repository;

	@RequestMapping("/ask")
	public Collection<String> findRelatedPages(@RequestParam(value = "text", defaultValue = "") String text) {
		LOGGER.info("Querying results for the input: {}", text);
		return repository.retrieveRelatedPages(text).stream().map(NhsWebPage::getUrl).collect(Collectors.toList());
	}
}
