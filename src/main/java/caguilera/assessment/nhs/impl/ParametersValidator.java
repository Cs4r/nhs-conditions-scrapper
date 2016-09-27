package caguilera.assessment.nhs.impl;

final class ParametersValidator {

	static void throwIfAnyIsNull(Object... params) {
		for (int i = 0; i < params.length; i++) {
			if (params[i] == null) {
				throw new IllegalArgumentException();
			}
		}
	}

}
