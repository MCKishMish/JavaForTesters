package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.getNavigationHelper().goToHomePage();
    List<ContactData> before= app.getContactHelper().getContactList();
    ContactData contact = new ContactData("FirstName1", "LastName1", "5 S.Main Street, Englishtown, NJ 077261", "+1 (202) 986-1805!", "e-mail1@mail.ru1", "test1");
    app.getContactHelper().createContact(contact);
    app.getNavigationHelper().goToHomePage();
    List<ContactData> after= app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(),before.size()+1);

    contact.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (c1,c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }
}