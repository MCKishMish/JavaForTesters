package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getContactHelper().createContact(new ContactData("FirstName", "LastName", "5 S.Main Street, Englishtown, NJ 07726", "+1 (202) 986-1805", "e-mail1@mail.ru", "test1"));
    app.getNavigationHelper().goToHomePage();
  }
}