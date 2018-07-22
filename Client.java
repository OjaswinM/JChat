import java.io.*;
import java.net.*;

class Client
{
  public static void main(String args[]) throws IOException
  {
      Socket s = new Socket("localhost",Integer.parseInt(args[0]));
      //Socket s = new Socket("localhost",2052);
      PrintWriter out = new PrintWriter(s.getOutputStream(), true);
      PrintWriter stdOut = new PrintWriter(System.out, true);
      BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
      BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
      String inp;
      String name;

      stdOut.println(in.readLine());
      name = stdIn.readLine();
      out.println(name);
      do
      {
        stdOut.print("<" + name + ">");
        stdOut.flush();
        inp = stdIn.readLine();
        out.println(inp);
        stdOut.println(in.readLine());
      }while(!"Quit".equals(inp));
    }
}
