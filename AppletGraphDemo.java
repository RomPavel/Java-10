
import java.applet.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppletGraphDemo extends Applet implements Runnable {
   Thread t = null; // поток


    Polygon arrow;
    int heigh = 100;//высота основания
    int baseR = 20;//радиус основания

   int i = 0;

  // boolean flag = true;
   int x0;//начальные координаты
   int y0;

   int x1 = x0+baseR, y1 = y0; //- левое основание
   int x2 = x0+baseR, y2 = y0+heigh;// - конец ло
   int x3 = x0-baseR, y3 = y0;// - правое основание
   int x4 = x0-baseR, y4 = y0+heigh;// конец по

   int R=(int)(heigh*1.5);//
   int x5 = x0-2*baseR, y5=y0+heigh; //левая крыша
   int x6 = x0+2*baseR, y6=y0+heigh; //правая крыша
   int x7 = x0,      y7=y0+R; //макушка
   int num = 90;// кол-во шагов по окружности (90 -- шаг на 4 градуса)

   int poly_x [] = {x1, x2, x5, x7, x6, x4, x3};
   int poly_y[] ={y1, y2, y5, y7, y6, y4, y3};


   double r2 = Math.sqrt(baseR*baseR+heigh*heigh), anglePerSecond = 2* Math.PI / num;
   double r3 = Math.sqrt(4*baseR*baseR+heigh*heigh);

   double angle;

   public void init() {
       x0 = getCoordinate(getParameter("x0_coordinate"));
       y0 = getCoordinate(getParameter("y0_coordinate")); // i -- текущее положение; x0, y0 - центр стрелки
      Color bkColor = getHtmlColor("AppBkColor", new Color(0x1AA083));
      this.setBackground(bkColor);

   }

   public void start() {
      if (t == null) // если поток пуст
         t = new Thread(this); // создаём новый
      t.start(); // запускаем поток
   }

   public synchronized void run() {


           while (true) // выполняем в бесконечном цикле
           {

               try {
                   Thread.sleep(25); // задержка в мс
                   angle = i++ * anglePerSecond; // рассчитываем текущий угол

                   poly_x[0] = (int) Math.round(x0 - baseR * Math.cos(angle));
                   poly_y[0] = (int) Math.round(y0 + baseR * Math.sin(angle));

                   poly_x[1] = (int) Math.round(x0 - r2 * Math.cos(Math.atan(heigh / baseR) + angle));
                   poly_y[1] = (int) Math.round(y0 + r2 * Math.sin(Math.atan(heigh / baseR) + angle));

                   poly_x[6] = (int) Math.round(x0 - baseR * Math.cos(Math.PI - angle));
                   poly_y[6] = (int) Math.round(y0 - baseR * Math.sin(Math.PI - angle));
//
                   poly_x[5] = (int) Math.round(x0 - r2 * Math.cos(Math.PI - Math.atan(heigh / baseR) + angle));
                   poly_y[5] = (int) Math.round(y0 + r2 * Math.sin(Math.PI - Math.atan(heigh / baseR) + angle));

                   poly_x[2] = (int) Math.round(x0 - r3 * Math.cos(Math.atan(heigh / (2 * baseR)) + angle));
                   poly_y[2] = (int) Math.round(y0 + r3 * Math.sin(Math.atan(heigh / (2 * baseR)) + angle));

                   poly_x[4] = (int) Math.round(x0 - r3 * Math.cos(Math.PI - Math.atan(heigh / (2 * baseR)) + angle));
                   poly_y[4] = (int) Math.round(y0 + r3 * Math.sin(Math.PI - Math.atan(heigh / (2 * baseR)) + angle));

                   poly_x[3] = (int) Math.round(x0 - R * Math.cos(Math.PI / 2 + angle));
                   poly_y[3] = (int) Math.round(y0 + R * Math.sin(Math.PI / 2 + angle));

                   if (i >= num)
                       i = 0; // если прошли окружность, то начинаем снова
                   repaint(); // вызываем отрисовку полигона
               } catch (InterruptedException e) {
               }

       }

   }
   public Color getHtmlColor( String strRGB, Color def ) {
      // in form #RRGGBB
      if ( strRGB != null && strRGB.charAt(0)== '#' ) {
         try {
            return new Color(
                    Integer.parseInt( strRGB.substring( 1 ), 16 ) );
         } catch ( NumberFormatException e ) {
            return def;
         }
      }
      return def;
   }
    public int getCoordinate( String str) {
        if ( str != null ) {
            try {
                return
                        Integer.parseInt( str, 10 ) ;
            } catch ( NumberFormatException e ) {
                return 100;
            }
        }
        return 100;
    }

   public void paint(Graphics dr) {

      {
         Color fillPol = getHtmlColor(getParameter("ArrowColor"), new Color(0x3489A0));
         dr.setColor(fillPol);

         arrow = new Polygon(poly_x, poly_y, poly_x.length);
         dr.drawPolygon(arrow);
         dr.fillPolygon(arrow);
      }
   }

}