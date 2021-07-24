package ru.stqa.pft.sandbox;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RectangleTests {

  @Test
  public void testArea (){
    Rectangle r = new Rectangle (4,9);
    Assert.assertEquals(r.area(),36);
  }
}
