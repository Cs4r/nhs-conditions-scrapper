package caguilera.assessment;

import java.io.IOException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import caguilera.assessment.nhs.impl.NhsSectionBuilder;
import caguilera.assessment.nhs.impl.NhsWebPage;
import caguilera.assessment.nhs.impl.NhsWebSection;

public class Main {

	public static void main(String[] args) throws IOException {

		NhsSectionBuilder sectionBuilder = new NhsSectionBuilder();

		Set<NhsWebPage> pages = new HashSet<>();

		for (char letter = 'A'; letter <= 'B'; ++letter) {
			Optional<NhsWebSection> build = sectionBuilder
					.build("http://www.nhs.uk/Conditions/Pages/BodyMap.aspx?Index=" + letter);
			if (build.isPresent()) {
				pages.addAll(build.get().getPages());
			}
		}
	
		pages.stream().map(NhsWebPage::getTitle).forEach(System.out::println);

	}

}
