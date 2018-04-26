package ac.at.acdh.cmdi.ccr.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import ac.at.acdh.cmdi.ccr.CCRConcept;
import ac.at.acdh.cmdi.ccr.CCRServiceFactory;
import ac.at.acdh.cmdi.ccr.CCRStatus;
import ac.at.acdh.cmdi.ccr.ICCRService;

public class CCRTest {

	ICCRService ccrService;

	@Before
	public void init() throws Exception {
		ccrService = CCRServiceFactory.getCCRService(false);
	}

	@Test
	public void testGetValidConcept() {
		String uri = "http://hdl.handle.net/11459/CCR_C-6586_2c79d86a-5a75-0890-d407-7d9cb86b9beb";
		CCRConcept concept = ccrService.getConcept(uri);
		assertEquals(concept.getUri(), uri);
		assertEquals(concept.getPrefLabel(), "licence URL");
		assertEquals(concept.getStatus(), CCRStatus.CANDIDATE);
	}

	@Test
	public void testGetInvalidConcept() {
		String uri = "invalid";
		CCRConcept concept = ccrService.getConcept(uri);
		assert (concept == null);
	}

	@Test
	public void testFetchAll() {
		Collection concepts = ccrService.getAll();
		if (concepts.size() < 4000)
			fail("CCR has more then 4000 concpets but " + concepts.size() + " were found");

	}

}
