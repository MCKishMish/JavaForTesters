package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    List<ContactData> before= app.contact().list();
    ContactData contact = new ContactData().withFirstname("FirstName1").withLastname("LastName1")
    .withAddress("5 S.Main Street, Englishtown, NJ 077261").withMobile("+1 (202) 986-1805!").withEmail("e-mail1@mail.ru1")
    .withGroup("modd1");
    app.contact().create(contact);
    List<ContactData> after= app.contact().list();
    Assert.assertEquals(after.size(),before.size()+1);

    contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(contact);
    Comparator<? super ContactData> byId = (c1,c2) -> Integer.compare(c1.getId(), c2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before,after);
  }
}