package org.ops4j.methodtypeleak.test;

import static java.lang.invoke.MethodType.methodType;

import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.net.URLClassLoader;

import javax.security.enterprise.identitystore.CredentialValidationResult;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MethodTypeTest {

	@Test
	public void test01_shouldPassWithExplictGarbageCollection() throws Exception {
		URL url = new URL("file:../method-type-leak-app/target/classes/");
		URL[] urls = new URL[] { url };
		URLClassLoader cl1 = new URLClassLoader(urls, getClass().getClassLoader());

		Class<?> credentialClass1 = cl1.loadClass("org.ops4j.methodtypeleak.app.IdTokenCredential");
		Class<?> storeClass1 = cl1.loadClass("org.ops4j.methodtypeleak.app.TokenIdentityStore");

		Object store1 = storeClass1.newInstance();

		MethodHandles.lookup().bind(store1, "validate", methodType(CredentialValidationResult.class, credentialClass1));

		store1 = null;
		storeClass1 = null;
		credentialClass1 = null;
		cl1 = null;

		// remove the next line to make the test fail
		System.gc();

		URLClassLoader cl2 = new URLClassLoader(urls, getClass().getClassLoader());
		Class<?> credentialClass2 = cl2.loadClass("org.ops4j.methodtypeleak.app.IdTokenCredential");
		Class<?> storeClass2 = cl2.loadClass("org.ops4j.methodtypeleak.app.TokenIdentityStore");

		Object store2 = storeClass2.newInstance();

		MethodHandles.lookup().bind(store2, "validate", methodType(CredentialValidationResult.class, credentialClass2));
	}

	@Test
	public void test02_onlyPassesWhenRunInIsolation() throws Exception {
		URL url = new URL("file:../method-type-leak-app/target/classes/");
		URL[] urls = new URL[] { url };
		URLClassLoader cl1 = new URLClassLoader(urls, getClass().getClassLoader());
		Class<?> credentialClass1 = cl1.loadClass("org.ops4j.methodtypeleak.app.IdTokenCredential");
		Class<?> storeClass1 = cl1.loadClass("org.ops4j.methodtypeleak.app.TokenIdentityStore");

		Object store1 = storeClass1.newInstance();

		MethodHandles.lookup().bind(store1, "validate", methodType(CredentialValidationResult.class, credentialClass1));
	}
}
