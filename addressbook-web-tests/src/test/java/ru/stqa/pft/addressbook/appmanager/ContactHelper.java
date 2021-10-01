package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

  public void initContactModification(int id) {
    //input[@id='" + id + "']/../following-sibling::td//img[@title='Edit']
    wd.findElement(By.xpath("//input[@id='" + id + "']/../following-sibling::td//img[@title='Edit']")).click();
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
    goToHomePage();
  }


  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModification(contact.getId());
    fillContactForm(contact,false);
    submitContactModification();
    goToHomePage();
  }

  public void delete(int index) {
    selectContact(index);
    deleteSelectedContacts();
    closeAlertWindow();
    goToHomePage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    deleteSelectedContacts();
    closeAlertWindow();
    goToHomePage();
  }

  public int getContactCount() {
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

  public Set<ContactData> all() {
    Set<ContactData> contacts = new HashSet<ContactData>();
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
}

