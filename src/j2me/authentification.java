/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package j2me;
import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;  
import java.io.*;
import java.lang.*;
import javax.microedition.io.*;
import javax.microedition.rms.*;
import javax.microedition.midlet.*;

/**
 * @author dell
 */




 public class authentification extends MIDlet implements CommandListener {
    
	TextField UserName=null;
	TextField mdp=null;
	Form authForm,mainscreen;
	TextBox t = null;
	StringBuffer b = new StringBuffer();
    private Display myDisplay = null;
    private Command okCommand = new Command("OK", Command.OK, 1);
    private Command exitCommand = new Command("Exit", Command.EXIT, 2);
    private Command backCommand = new Command("Back", Command.BACK, 2);
    private Alert alert = null;

           

      

    

    public void startApp() throws MIDletStateChangeException {
          myDisplay = Display.getDisplay(this);

	UserName=new TextField("Your Name","",10,TextField.ANY);
	mdp=new TextField("Mot de passe","",10,TextField.ANY);
	authForm=new Form("Identification");
	mainscreen=new Form("Logging IN");
	mainscreen.append("Logging in....");
	mainscreen.addCommand(backCommand);
	authForm.append(UserName);
	authForm.append(mdp);
	authForm.addCommand(okCommand);
	authForm.addCommand(exitCommand);
	authForm.setCommandListener(this);
	myDisplay.setCurrent(authForm);
    }

    public void pauseApp() {
    }


  
    protected void destroyApp(boolean unconditional)
            throws MIDletStateChangeException {
    }

  
    public void commandAction(Command c, Displayable d) {
        
        if ((c == okCommand) && (d == authForm)) {
	if (UserName.getString().equals("") || mdp.getString().equals("")){
		alert = new Alert("Error", "You should enter Username and Location", null, AlertType.ERROR);
		alert.setTimeout(Alert.FOREVER);
		myDisplay.setCurrent(alert);
	}
	else{
	myDisplay.setCurrent(mainscreen);
	login(UserName.getString(),mdp.getString());
	}
	}
        if ((c == backCommand) && (d == mainscreen)) {
		myDisplay.setCurrent(authForm);
	}
	if ((c == exitCommand) && (d == authForm)) {
		notifyDestroyed();
	}
	}
        
        public void login(String UserName,String PassWord) {
	HttpConnection connection=null;
	DataInputStream in=null;
	String url="jdbc:mysql://http://localhost//helpers";

	OutputStream out=null;	
	try
	{
		connection=(HttpConnection)Connector.open(url);
		connection.setRequestMethod(HttpConnection.POST);
		connection.setRequestProperty("IF-Modified-Since", "2 Oct 2002 15:10:15 GMT");
		connection.setRequestProperty("User-Agent","Profile/MIDP-1.0 Configuration/CLDC-1.0");
		connection.setRequestProperty("Content-Language", "en-CA");
		connection.setRequestProperty("Content-Length",""+ (UserName.length()+PassWord.length()));
		connection.setRequestProperty("UserName",UserName);
		connection.setRequestProperty("PassWord",PassWord);
	out = connection.openDataOutputStream();
	out.flush();
	in = connection.openDataInputStream();
	int ch;
	while((ch = in.read()) != -1) {
		b.append((char) ch);
		//System.out.println((char)ch);
	}
	//t = new TextBox("Reply",b.toString(),1024,0);
	mainscreen.append(b.toString());
	if(in!=null)
		in.close();
	if(out!=null)
		out.close();
	if(connection!=null)
		connection.close();
	} 
	catch(IOException x){}
	myDisplay.setCurrent(mainscreen);
        
    }
} 