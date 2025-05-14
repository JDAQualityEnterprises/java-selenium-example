package com.jda.tests;

import com.jda.framework.pages.PageFactory;
import com.jda.core.TestBase;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ContactsTests extends TestBase {

    @Test
    public void hasHeading() {
        PageFactory.MainMenu().contactLink.click();

        var headingText = PageFactory.ContactsPage().heading.getText();

        assertThat(headingText).contains("Contact");
    }
}