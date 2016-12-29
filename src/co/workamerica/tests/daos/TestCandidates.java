package co.workamerica.tests.daos;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import org.testng.annotations.Test;

/**
 * Created by Faizan on 5/18/2016.
 */

@Test(groups = {"dao", "candidates"})
public class TestCandidates {

    private Candidates candidate = new Candidates();

    public void setUp() {
        candidate = new Candidates();
        candidate.setFirstName("John");
        candidate.setLastName("Smith");
        candidate.setEmail("john" + (int) (Math.random() * 10) + "@test.com");
        String[] passwordArray = CustomUtilities.hashPassword("test", null);
        candidate.setPassword(passwordArray[0]);
        candidate.setSalt(passwordArray[1]);
        candidate.setPassword("test");
        candidate.setCity("Bethesda");
        candidate.setState("MD");
        candidate.setZip("20852");
        candidate.setLatitude("");
        candidate.setLongitude("");
        candidate.setPhone("0000000001");
        candidate.setAlternatePhone("1111111111");
        candidate.setPastEducation("High school diploma or GED,Some trade/technical/vocational training");
        candidate.setWorkExperience("No relevant work experience");
        candidate.setVeteran("No");
        candidate.setEmployed("No");
        candidate.setRelocate("No");
        candidate.setAdditionalInformation("Testing");
        candidate.setSchool("Center for Employment Training- Santa Maria");
        candidate.setField("Medical Assistant");
        candidate.setAnticipatedCertification("School Issued Certificate/Diploma");
        candidate.setPastField("None");
        candidate.setObtainedCertification("None");
        candidate.setCompletionDate("07/28/16");
        candidate.setValidDriversLicense("Yes");
        candidate.setSchoolID(159);
        candidate.setAuthorized("Yes");
        candidate.setApproved("Yes");
        candidate.setWorkAmericaCreated("No");
        CandidatePersistence.persist(candidate);
    }

    public void getCandidateByIDAndDelete() {
        setUp();
        assert CandidatePersistence.getCandidateByID(candidate.getCandidateID()).getCandidateID() == candidate.getCandidateID();
        assert CandidatePersistence.delete(candidate.getCandidateID());
    }

    public void getCandidateByIDAndDeleteNull() {
        assert CandidatePersistence.getCandidateByID(-6) == null;
        assert CandidatePersistence.getCandidateByID(0) == null;
        assert !CandidatePersistence.delete(0);
        assert !CandidatePersistence.delete(-2);
    }

    public void login() {
        setUp();
        Candidates test = CandidatePersistence.login(candidate.getEmail(), "test");

        assert test != null;
        assert test.getCandidateID() == candidate.getCandidateID();

        test = CandidatePersistence.login(candidate.getPhone(), "test");

        assert test != null;
        assert test.getCandidateID() == candidate.getCandidateID();

        assert CandidatePersistence.delete(candidate.getCandidateID());
    }

    public void loginNull() {
        assert CandidatePersistence.login(null, null) == null;
        assert CandidatePersistence.login(null, "test") == null;
        assert CandidatePersistence.login(candidate.getEmail(), null) == null;
        assert CandidatePersistence.login("", "") == null;
        assert CandidatePersistence.login("", null) == null;
        assert CandidatePersistence.login(null, "") == null;
        assert CandidatePersistence.login("", "test") == null;
        assert CandidatePersistence.login(candidate.getEmail(), "") == null;
    }

    public void getCandidateByPhone() {
        setUp();

        Candidates test = CandidatePersistence.getCandidateByPhone(candidate.getPhone());
        assert test != null;
        assert test.getCandidateID() == candidate.getCandidateID();
        assert test.getPhone().equals(candidate.getPhone());

        assert CandidatePersistence.delete(candidate.getCandidateID());
    }

    public void getCandidateByPhoneNull() {
        assert CandidatePersistence.getCandidateByPhone(null) == null;
        assert CandidatePersistence.getCandidateByPhone("") == null;
        assert CandidatePersistence.getCandidateByPhone("240620824") == null;
        assert CandidatePersistence.getCandidateByPhone("2984629746298734629743") == null;
    }

    public void getCandidateByEmail () {
        setUp();

        Candidates test = CandidatePersistence.getCandidateByEmail(candidate.getEmail());
        assert test != null;
        assert test.getCandidateID() == candidate.getCandidateID();
        assert test.getEmail().equals(candidate.getEmail());

        assert CandidatePersistence.delete(candidate.getCandidateID());
    }

    public void getCandidateByEmailNull() {
        assert CandidatePersistence.getCandidateByEmail(null) == null;
        assert CandidatePersistence.getCandidateByEmail("") == null;
        assert CandidatePersistence.getCandidateByEmail("faizan@") == null;
    }

    public void checkIfExistsByEmail() {
        setUp();

        assert CandidatePersistence.checkIfExistsByEmail(candidate.getEmail());

        assert CandidatePersistence.delete(candidate.getCandidateID());
    }

    public void checkIfExistsByEmailNull() {
        assert !CandidatePersistence.checkIfExistsByEmail(null);
        assert !CandidatePersistence.checkIfExistsByEmail("");
        assert !CandidatePersistence.checkIfExistsByEmail("faizan@");
    }

    public void getAll() {

    }

    public void addLoginLog() {

    }

}
