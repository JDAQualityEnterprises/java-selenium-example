package com.jda.tests;

import com.jda.framework.pages.PageFactory;
import com.jda.core.TestBase;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.*;
import static org.assertj.core.api.Assertions.*;

public class HomeTests extends TestBase {

    @Test
    public void hasTitle() {
        assertThat(PageFactory.HomePage().getTitle()).contains("JDA Quality Enterprises Limited");
    }

    @Test
    public void hasLinks() {
        var emailText = PageFactory.HomePage().emailLink.getText();

        assertThat(emailText).contains("enquiry@jdaqualitylimited.com");

        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(PageFactory.MainMenu().homeLink.getDomAttribute("href")).contains("/home");
            softAssertions.assertThat(PageFactory.MainMenu().contactLink.getDomAttribute("href")).contains("/contact");
            softAssertions.assertThat(PageFactory.MainMenu().clientsLink.getDomAttribute("href")).contains("/clients");
            softAssertions.assertThat(PageFactory.MainMenu().aboutLink.getDomAttribute("href")).contains("/about-us");
        });
    }
}