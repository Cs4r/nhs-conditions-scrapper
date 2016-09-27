package caguilera.assessment.nhs.impl;

import java.io.IOException;

import org.apache.commons.io.IOUtils;

public class FilesHelper {

	static String getFileContent(String fileName) {
		String result = "";

		ClassLoader classLoader = FilesHelper.class.getClassLoader();
		try {
			result = IOUtils.toString(classLoader.getResourceAsStream(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result.substring(0, result.length() - 1);

	}

}
