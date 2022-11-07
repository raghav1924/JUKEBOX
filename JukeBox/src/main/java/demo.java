

import java.io.*;
import java.security.PublicKey;
import java.sql.*;

//import  java.lang.*;
public class demo {
    public static void main(String[] args) {
        Console console = System.console();
        System.out.println("Enter ur passwrod");
        char[] arr = console.readPassword();
        //String nw=System.console().readLine();
        //Console conso=System.console();
        // String password="";
        //if (conso!=null) {
        // char[] parr = conso.readPassword("Enter password Here");
        //for (char c : parr) {
        //  System.out.println("*");
    }
    //String password = new String(parr);
    // password=new String(parr);
    // }
    // else {
    //  System.out.println("console is "+conso);
    //}

    /*while (rs.next()) {
       Integer n9=-1;
       Integer n10=-1;
       Integer car = rs.getInt(1);//songI
       Integer car2 = rs.getInt(2);//podxasI
       System.out.println(car);
       System.out.println(car2);
       if(car==0 && car2==0){
           continue;
       }
       if (car == 0) {
           list.add("P,"+rs.getInt(2));
           // S,23 String arr[]=list.get(1).split(",");=
           //n10=playPodcast(rs.getInt(2));

       }
       if (car2 == 0) {
           list.add("P,"+rs.getInt(2));
           //n9=playSong(rs.getInt(1));
       }
//                    System.out.println("Next song -1  OR  Exit-0");
       if(n9==(rs.getInt(1)-2) || n10==(rs.getInt(2)-2)){
           rs.previous();
           rs.previous();

       }
       else if (n9 == 0 || n10 ==0) {
           break;
       }
       else if(n9!=0 && (n9!=(rs.getInt(1)-2) || n10!=(rs.getInt(2)-2)) && n9!=rs.getInt(1) && n10!=rs.getInt(2)){
           if (n9==-1) {
               playPodcast(n10);
           }
           if (n10 == -1) {
               playSong(n9);
           }
       }
   }*/
    /*

    String stat9="off";
    int a=3;
    while (!stat9.equals("on")) {
        try {

            stat9="on";
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("There is no Song with this ID");
        }
    }
    */


}





