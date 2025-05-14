package com.jda.framework.pages;

import com.jda.core.BrowserDriver;

public class PageFactory {

    private static <PageBase> PageBase Page(Class<PageBase> clazz) {
        try {
            PageBase page = clazz.getDeclaredConstructor().newInstance();
            org.openqa.selenium.support.PageFactory.initElements(BrowserDriver.getDriver(), page);
            return page;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static ContactsPage ContactsPage() { return Page(ContactsPage.class); }

    public static HomePage HomePage() { return Page(HomePage.class); }

    public static MainMenu MainMenu() { return Page(MainMenu.class); }
}
