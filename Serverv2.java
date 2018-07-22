import java.io.*;
import java.net.*;
import java.util.*;

class Serverv2
{
  public static void main(String args[]) throws IOException
  {
        ServerSocket s = new ServerSocket(Integer.parseInt(args[0]));
        //ServerSocket s = new ServerSocket(2052);
        PrintWriter stdOut = new PrintWriter(System.out, true);
        stdOut.println("Server Running");
        while(true)
        {
          Socket c = s.accept();
          new ClientControl(c);
        }
    }
}

class ClientControl extends ClientDetails implements Runnable
{
  Thread t;

  ClientControl(Socket c) throws IOException
  {
    super(c);
    t = new Thread(this);
    t.start();
  }

  public void run()
  {
    try
    {
      String msg = in.readLine();
      while(msg != null && !"Quit".equals(msg))
      {
        String fMsg = "<" + name + ">" + msg;
        stdOut.println(fMsg);
        broadcast(fMsg);
        msg = in.readLine();
      }
      removeClient(this);
      out.println("Conncetion Closed...");
      broadcast(this.name + " has left...");
      stdOut.println(this.name + " has left...");
      c.close();
    }
    catch(IOException e)
    {
      e.printStackTrace();
    }
  }
}

class ClientDetails
{
  static PrintWriter stdOut = new PrintWriter(System.out, true);
  static ArrayList<ClientDetails> clientList = new ArrayList<ClientDetails>();
  PrintWriter out;
  BufferedReader in;
  Socket c;
  int id;
  String name;

  ClientDetails(Socket c) throws IOException
  {
    this.c = c;
    out = new PrintWriter(c.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(c.getInputStream()));
    id = getID();
    out.println("Enter a name: ");
    name = in.readLine();
    addClient(this);
  }

  int getID()
  {
    Random r = new Random();
    int low = 1000;
    int high = 10000;
    int rand = r.nextInt(high-low) + low;
    return rand;
  }

  static void addClient(ClientDetails client)
  {
    clientList.add(client);
    //  stdOut.println("Size: " + clientList.size());
  }

  static void printClientList()
  {
       for(ClientDetails client:clientList)
       {
         stdOut.println(" -->" + client.name);
       }
  }

  static void removeClient(ClientDetails client)
  {
    clientList.remove(client);
  }

  synchronized void broadcast(String msg)
  {
    for(ClientDetails client:clientList)
    {
      if(client!=this)
      {
        //stdOut.println("Sending to: " + client.name);
        //stdOut.flush();
        client.out.println(msg);
      }
    }
  }
}
