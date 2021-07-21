package ru.stqa.pft.sandbox;

public class MyFirstProg {
  public static void main(String[] args) {
    Square s = new Square(5);

    hello("world");
    hello("user");
    hello("Pavel");

    //double l=5;
    System.out.println("Площадь квадрата со стороной "+s.l+ " = "+s.area());

    Rectangle r = new Rectangle(4,6);
    System.out.println("Площадь прямоугольника со сторонами " + r.a + " и " + r.b +  " = " +r.area());

    Point p1= new Point(0,5);
    Point p2= new Point(0,0);

    System.out.println("Расстояние между двумя точками с координатами "
            + "(" + p1.x + ";" + p1.y + ") и (" + p2.x + ";" + p2.y + ") равно " + p1.distance(p2));
  }

  public static void hello(String somebody) {
    System.out.println("Hello, " +somebody+"!");
  }

  public static double area (Square s){
    return s.l*s.l;
  }

  public static double area (Rectangle r){
    return r.a*r.b;
  }

  public static double distance(Point p1, Point p2){
    return Math.sqrt(Math.pow((p2.x- p1.x),2)+Math.pow((p2.y- p1.y),2));
  }
}
