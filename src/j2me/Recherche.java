/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package j2me;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.StringItem;
import javax.microedition.lcdui.TextField;
import javax.microedition.midlet.*;

/**
 * @author Administrateur
 */
public class Recherche extends MIDlet implements CommandListener,Runnable{
    Form f1=new Form("Recherche Offre");
        Form resultat=new Form("Resultat de Recherche");
    Alert alert=new Alert("validation", null, null, AlertType.INFO);

   Display disp=Display.getDisplay(this);

    Image im;
    TextField tf1=new TextField("Mot clé", "", 60, TextField.ANY);
    String[] choix={"Nom","Depart","Arriver","Nom Agence"};
    StringItem si=new StringItem("par : ","");
    ChoiceGroup cg=new ChoiceGroup("Choix de recherche", ChoiceGroup.EXCLUSIVE, choix, null);
    Command lancer=new Command("Lancer", Command.SCREEN, 0);
    Command annuler=new Command("EXIT", Command.EXIT, 0);
    Command retour=new Command("BACK", Command.EXIT, 0);
        StringBuffer sb=new StringBuffer();

    Image voyage;
    HttpConnection httpConnection; 
    DataInputStream dataInputStream;
    byte[] data;
    int size;
    public void startApp() {
        /*try {
            im=Image.createImage("/j2me/logo.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        f1.append(im);*/
        f1.append(tf1);
        f1.append(si);
        f1.append(cg);
        f1.addCommand(lancer);
        f1.addCommand(annuler);
        f1.setCommandListener((CommandListener) this);
        disp.setCurrent(f1);
        
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }

    public void commandAction(Command c, Displayable d) {
        if(c==lancer){
          Thread t=new Thread(this);
          t.start();
        }
    }

    public void run() {
                    String url="";
                    System.out.println(cg.getSelectedIndex());
        if(tf1.getString().equals("")){
            alert.setString("Champ vide");
        }else{
            if(cg.getSelectedIndex()==0){
                url="http://localhost/recherche.php?nom="+tf1.getString();
            }
            if(cg.getSelectedIndex()==2){
                url="http://localhost/recherche.php?depart="+tf1.getString();
            }
            if(cg.getSelectedIndex()==3){
                url="http://localhost/recherche.php?arriver="+tf1.getString();
            }
            if(cg.getSelectedIndex()==4){
             url="http://localhost/recherche.php?agence="+tf1.getString();
            }
           

            try {
            httpConnection=(HttpConnection) Connector.open(url);
            dataInputStream =new DataInputStream(httpConnection.openDataInputStream());
            while ((size=dataInputStream.read()) !=-1) {  
                sb.append((char)size);
            }
            String cc=sb.toString().trim();
                    if(cc.equals("no")){
                        alert.setString("Non trouver");
                        disp.setCurrent(f1);
                    }else{
                        resultat.append(cc);
                        disp.setCurrent(resultat);
                    }
           } catch (IOException ex) {
            ex.printStackTrace();
             }
        }
    }
}
