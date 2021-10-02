package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;

import java.util.ArrayList;
import java.util.List;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void goToHomePage() {
    if (isElementPresent(By.id("maintable"))) {
      return;
    }
    click(By.linkText("home"));
  }

  public void initContactCreation() {
    click(By.linkText("add new"));
  }

  public void fillContactForm(ContactData contactData, boolean creation) {
    type(By.name("firstname"), contactData.getFirstname());
    type(By.name("lastname"), contactData.getLastname());
    type(By.name("address"), contactData.getAddress());
    type(By.name("mobile"), contactData.getMobile());
    type(By.name("email"), contactData.getEmail());

    if (creation) {
      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
    } else {
      Assert.assertFalse(isElementPresent(By.name("new_group")));
    }
  }

  public void submitContactCreation() {
    click(By.xpath("//input[21]"));
  }

  public void selectContact(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void closeAlertWindow() {
    wd.switchTo().alert().accept();
  }

  public void initContactModificationById(int id) {
    //input[@id='" + id + "']/../following-sibling::td//img[@title='Edit']
    //input[@id='" + id + "']/../../td[8]/a/img
    wd.findElement(By.xpath("//input[@id='" + id + "']/../../td[8]/a/img")).click();
  }

  public void modifyContactForm() {
    typeWithoutClearing(By.name("firstname"), wd.findElement(By.name("firstname")).getText()+" version 2");
    typeWithoutClearing(By.name("lastname"), wd.findElement(By.name("lastname")).getText()+" version 2");
    typeWithoutClearing(By.name("address"), wd.findElement(By.name("address")).getText()+" version 2");
    typeWithoutClearing(By.name("mobile"), wd.findElement(By.name("mobile")).getText()+" version 2");
    typeWithoutClearing(By.name("email"), wd.findElement(By.name("email")).getText()+" version 2");
  }

  public void submitContactModification() {
    click(By.xpath("//input[22]"));
  }

  public boolean isThereAContact() {
      return isElementPresent(By.name("selected[]"));
    }

  public void create(ContactData contact) {
    initContactCreation();
    fillContactForm(contact, true);
    submitContactCreation();
    contactCashe = null;
    goToHomePage();
  }


  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModificationById(contact.getId());
    fillContactForm(contact,false);
    submitContactModification();
    contactCashe = null;
    goToHomePage();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContacts();
    closeAlertWindow();
    contactCashe = null;
    goToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    closeAlertWindow();
    contactCashe = null;
    goToHomePage();
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public List<ContactData> list() {
    List<ContactData> contacts = new ArrayList<ContactData>();
    List <WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> rows = element.findElements(By.tagName("td"));
      String lname = rows.get(1).getText();
      String fname = rows.get(2).getText();
      //System.out.println(lname + " " + fname);
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      contacts.add(new ContactData().withId(id).withFirstname(fname).withLastname(lname));
    }
    return contacts;
  }

  private Contacts contactCashe = null;

  public Contacts all() {
    if (contactCashe!=null) {
      return new Contacts(contactCashe);
    }

    contactCashe = new Contacts();
    List <WebElement> elements = wd.findElements(By.name("entry"));
    for (WebElement element : elements) {
      List<WebElement> rows = element.findElements(By.tagName("td"));
      String lname = rows.get(1).getText();
      String fname = rows.get(2).getText();
      //System.out.println(lname + " " + fname);
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("id"));
      contactCashe.add(new ContactData().withId(id).withFirstname(fname).withLastname(lname));
    }
    return new Contacts(contactCashe);
  }

  public ContactData infoFormEditForm(ContactData contact) {
    initContactModificationById(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withFirstname(firstname)
            .withLastname(lastname).withHomePhone(home).withMobilePhone(mobile)
            .withworkPhone(work);
  }
}

