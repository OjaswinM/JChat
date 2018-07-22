import java.io.*;
import java.net.*;

class Client
{
  static Socket s;
  static BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
  static PrintWriter stdOut = new PrintWriter(System.out, true);
  static BufferedReader in;
  static PrintWriter out;
  static String inp;
  static String name;

  public static void main(String args[]) throws IOException
  {
      s = new Socket("localhost",Integer.parseInt(args[0]));
      initialization(s);
      new ClientListener();
      new ClientSpeaker();
  }

  static void initialization(Socket s) throws IOException
  {
    out = new PrintWriter(s.getOutputStream(), true);
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    stdOut.println(in.readLine());
    name = stdIn.readLine();
    out.println(name);
  }
}

class ClientListener extends Client implements Runnable
{
  Thread t;

  ClientListener()
  {
    t = new Thread(this,"Demo");
    t.start();
  }

  public void run()
  {
    do
    {
      //stdOut.print("<" + name + ">");
      //stdOut.flush();
      //inp = stdIn.readLine();
      //out.println(inp);
      try
      {
        do
        {
          String msg = in.readLine();
          if(msg != null)
            stdOut.println(msg);
        }while(true);

      }
      catch(IOException e)
      {
        e.printStackTrace();
      }
    }while(true);
  }
}

class ClientSpeaker extends Client implements Runnable
{
  Thread t;

  ClientSpeaker()
  {
    t = new Thread(this,"Demo");
    t.start();
  }

  public void run()
  {
    try
    {
      do
      {
        inp = stdIn.readLine();
        out.println(inp);
      }while(!"Quit".equals(inp));
      System.exit(0);
    }catch(IOException e)
    {
      e.printStackTrace();
    }
  }
}
