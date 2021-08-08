package ru.stqa.pft.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactHelper extends HelperBase {

  public ContactHelper(WebDriver wd) {
    super(wd);
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

  public void selectContact() {
      click(By.name("selected[]"));
  }

  public void deleteSelectedContacts() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void closeAlertWindow() {
    wd.switchTo().alert().accept();
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
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
}
