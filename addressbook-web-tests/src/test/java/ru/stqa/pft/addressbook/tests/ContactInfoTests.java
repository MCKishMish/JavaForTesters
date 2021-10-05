package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactInfoTests extends TestBase {
  @Test
  public void testContactInfo() {
    app.goTo().homePage();
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactFromInfoForm = app.contact().infoFromDetailsFrom(contact);
    assertThat(contact.getFirstname(), equalTo(contactFromInfoForm.getFirstname()));
    assertThat(contact.getLastname(), equalTo(contactFromInfoForm.getLastname()));
    assertThat(contact.getAddress(), equalTo(contactFromInfoForm.getAddress()));
    assertThat(contact.getAllEmails(), equalTo(contactFromInfoForm.getEmail1()));
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactFromInfoForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s) -> ! s.equals("")).map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }
}
