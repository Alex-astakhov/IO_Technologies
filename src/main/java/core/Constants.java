package core;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Constants {

    public static final String EMAIL = "gexibawer@one2mail.info";
    public static final String PASSWORD = "q";

    public static final Cookie WIDGET_COOKIE = new Cookie("_io_widget_introduced", "true");

    public static String DOMEN;
    public static String HOST;
    public static boolean BROWSER_LOG;
    static final int PAGE_LOAD_TIMEOUT = 300;
    public static final int IMPLICITLY_WAIT_TIMEOUT = 5;
    static final int IMPLICITLY_WAIT_TIMEOUT_FOR_FRAME = 20;
    static final long TIMEOUT_FOR_ELEMENT_DISAPPEARING = 5000;
    public static final long TIMEOUT_FOR_EMAIL = 120000;
    public static final String REGISTRATION_EMAIL = "applicant.reg.test@gmail.com";
    public static final String APPLICANT_LOGIN_EMAIL = "applicant.test.email@gmail.com";
    public static final String APPLICANT_VISUAL_TEST_EMAIL = "applicant.visual.test@gmail.com";
    public static final String APPLICANT_API_TEST_EMAIL = "api-test.applicant@gmail.com";

    public static final String ADMIN_EMAIL = "aleksandr.astakhov@gen.tech";
    public static final String ADMIN_PASSWORD = "secretpass85";

    public static final String DEV_EMAIL = "rabota.team@gmail.com";
    public static final String DEV_PASSWORD = "BestTeamEver";

    public static final String MAIL_RU_REG_EMAIL = "applicant.reg.test@mail.ru";
    public static final String FACEBOOK_REG_EMAIL = "facebook_nvqxzoc_user@tfbnw.net";
    public static final String APPLICANT_SOCIAL_NET_FIRST_NAME = "Александр";
    public static final String APPLICANT_SOCIAL_NET_LAST_NAME = "Александров";

    public static final String EMPLOYER_EMAIL = "natalie78.86@mail.ru";
    public static final String EMPLOYER_PASSWORD = "natalie78.86@mail.ru";
    public static final String EMPLOYER_ID = "14157";
    public static final String EMPLOYER_VISUAL_MANAGER = "employer.test.manager1@mail.ru";
    public static final String EMPLOYER_MANAGER = "employer.test.manager@gmail.com";
    public static final String MANAGER_EMAIL_TO_ADD = "employer.test.manager3@gmail.com";
    public static final String EMPLOYER_BOSS = "employer.manual-test@mail.ru";
    public static final String CRM_MANAGER = "crm.test-manager@mailinator.com";

    public static final By CSS_POP_UP_SAVE = By.cssSelector("[data-test=save-popup]");
    public static final By CSS_POP_UP_CANCEL = By.cssSelector("[data-test=cancel-popup]");

    public static final String SCREENSHOT_PATH = "C:\\screenshots";
    public static final int APPLICANT_RESUME_ID = 1414327;
    public static final int APPLICANT_RESUME_ID_DEV = 1151274;
    public static final String VISUAL_RESUME_ID = "1192929";
    public static final String VISUAL_RESUME_ID_DEV = "1147002";

    public static final By POPUP_CONTENT = By.cssSelector("[data-test=popup-content]");

    public static final String API_REG_ID = "AutoTest";
    public static final String API_VERSION = "v1";
    public static final int API_RESUME_ID_DEV = 1150816; // api-test.applicant@gmail.com
    public static final int API_RESUME_ID_PROD = 1389544;// api-test.applicant@gmail.com
    public static final int API_RESUME_ID_DEV_FOR_EDIT = 1152787; // applicant.test.email@gmail.com
    public static final int API_RESUME_ID_PROD_FOR_EDIT = 1458835; // applicant.test.email@gmail.com


    public static final List<String> CITIES_WITH_REGIONS = new ArrayList<>(Arrays.asList("26402", "26403", "26313", "26394"));
}
