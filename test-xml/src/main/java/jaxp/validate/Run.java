package jaxp.validate;

// just a handy class that launches a main() in another class
// it lets me run several applications with the -jar command

import java.lang.reflect.*;

public class Run
{
   static final String[][] classnames =
   {
      { "-help", "org.ananas.tips.Help" },
      { "-validatesax", "org.ananas.tips.ValidateSAX" },
      { "-validatedom", "org.ananas.tips.ValidateDOM" },
   };

   private static void run(String classname,String[] args)
      throws IllegalAccessException, IllegalArgumentException, InvocationTargetException,
             NoSuchMethodException, ClassNotFoundException
   {
      if(classname == null || args == null)
         throw new IllegalArgumentException("null parameter in Run.run()");
      Class clasz = Class.forName(classname);
      Method main = clasz.getMethod("main",new Class[] { args.getClass() });
      main.invoke(null,new Object[] { args });
   }

   private static String[] makeArgs(String[] args)
   {
      if(args == null)
         return null;
      if(args.length < 2)
         return new String[0];
      String[] result = new String[args.length - 1];
      System.arraycopy(args,1,result,0,args.length - 1);
      return result;
   }

   public static void main(String[] args)
   {
      try
      {
         String arg = args.length > 0 ? args[0] : null,
                target = null; // args.length > 0 ? args[0] : "-help";
         for(int i = 0;i < classnames.length;i++)
            if(classnames[i][0].equals(arg))
               target = classnames[i][1];
         if(target == null)
            target = classnames[0][1];
         run(target,makeArgs(args));
      }
      catch(InvocationTargetException x)
      {
         x.getTargetException().printStackTrace();
      }
      catch(Exception x)
      {
         x.printStackTrace();
      }
   }
}