package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

  @Test (enabled = false)
  public void testContactModification() throws Exception {
    app.goTo().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) {
      app.getContactHelper().createContact(new ContactData("FirstName", "LastName", "5 S.Main Street, Englishtown, NJ 07726", "+1 (202) 986-1805", "e-mail1@mail.ru", "test1"));
    }
    List<ContactData> before= app.getContactHelper().getContactList();
    //app.getContactHelper().selectContact(before.size()-1);
    app.getContactHelper().initContactModification(before.size()-1);
    ContactData contact = new ContactData(before.get(before.size()-1).getId(),"FirstName1", "LastName1", "5 S.Main Street, Englishtown, NJ 077261", "+1 (202) 986-1805!", "e-mail1@mail.ru1", null);
    app.getContactHelper().fillContactForm(contact,false);
    //app.getContactHelper().modifyContactForm();
    app.getContactHelper().submitContactModification();
    app.goTo().goToHomePage();
    List<ContactData> after= app.getContactHelper().getContactList();
    System.out.println(after);
    Assert.assertEquals(after.size(),before.size());

    before.remove(before.size()-1);
    System.out.println(before);
    before.add(contact);
    System.out.println(before);
    Comparator<? super ContactData> byId = (c1,c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }
}
