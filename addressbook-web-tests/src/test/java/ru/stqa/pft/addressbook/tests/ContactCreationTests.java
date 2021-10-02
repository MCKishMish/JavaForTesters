package ru.stqa.pft.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before= app.contact().all();
    ContactData contact = new ContactData().withFirstname("FirstName1").withLastname("LastName1")
    .withAddress("5 S.Main Street, Englishtown, NJ 077261").withMobile("+1 (202) 986-1805!").withEmail("e-mail1@mail.ru1")
    .withGroup("modd1");
    app.contact().create(contact);
    Contacts after= app.contact().all();
    assertThat(after.size(),equalTo(before.size()+1));

    //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());

    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }
}