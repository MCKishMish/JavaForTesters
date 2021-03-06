package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase{

  @Test
  public void testContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before= app.contact().all();
    File photo = new File("src/test/resources/photo.jpeg");
    ContactData contact = new ContactData().withFirstname("FirstName1").withLastname("LastName1")
    .withAddress("5 S.Main Street, Englishtown, NJ 077261").withMobile("+1 (202) 986-1805!")
            .withGroup("modd1").withEmail1("e-mail1@mail.ru1").withPhoto(photo);
    app.contact().create(contact);
    assertThat(app.contact().count(),equalTo(before.size()+1));
    Contacts after= app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((c) -> c.getId()).max().getAsInt()))));
  }

  @Test (enabled = false)
  public void testBadContactCreation() throws Exception {
    app.goTo().homePage();
    Contacts before= app.contact().all();
    ContactData contact = new ContactData().withFirstname("FirstName1'").withLastname("LastName1")
            .withAddress("5 S.Main Street, Englishtown, NJ 077261").withMobile("+1 (202) 986-1805!").withEmail1("e-mail1@mail.ru1")
            .withGroup("modd1");
    app.contact().create(contact);
    assertThat(app.contact().count(),equalTo(before.size()));
    Contacts after= app.contact().all();
    //contact.withId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());

    assertThat(after, equalTo(before));
  }


}