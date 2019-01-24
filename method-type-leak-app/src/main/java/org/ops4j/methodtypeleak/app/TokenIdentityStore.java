package org.ops4j.methodtypeleak.app;

import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;

public class TokenIdentityStore implements IdentityStore {

	public CredentialValidationResult validate(IdTokenCredential credential) {
		return CredentialValidationResult.INVALID_RESULT;
	}
}
