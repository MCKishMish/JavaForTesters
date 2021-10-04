package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions ()  {
    app.goTo().homePage();
    if (app.contact().list().size() == 0) {
      app.contact().create(new ContactData().withFirstname("FirstName1").withLastname("LastName1")
              .withAddress("5 S.Main Street, Englishtown, NJ 077261").withMobile("+1 (202) 986-1805!").withEmail1("e-mail1@mail.ru1")
              .withGroup("modd1"));
    }
  }

  @Test
  public void testContactDeletion ()  throws Exception {
    Contacts before= app.contact().all();
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    assertThat(app.contact().count(),equalTo(before.size()-1));
    Contacts after= app.contact().all();
    assertThat(after, equalTo(before.without(deletedContact)));
  }
}
