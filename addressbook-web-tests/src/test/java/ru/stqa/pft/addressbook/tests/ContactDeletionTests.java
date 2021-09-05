package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.List;

public class ContactDeletionTests extends TestBase {

  @Test (enabled = false)
  public void testContactDeletion ()  throws Exception {
    app.goTo().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("FirstName", "LastName", "5 S.Main Street, Englishtown, NJ 07726", "+1 (202) 986-1805", "e-mail1@mail.ru", "test1"));
    }
    List<ContactData> before= app.getContactHelper().getContactList();
    app.getContactHelper().selectContact(before.size()-1);
    app.getContactHelper().deleteSelectedContacts();
    app.getContactHelper().closeAlertWindow();
    app.goTo().goToHomePage();
    List<ContactData> after= app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size()-1);

    before.remove(before.size()-1);
    Assert.assertEquals(before, after);

  }
}
