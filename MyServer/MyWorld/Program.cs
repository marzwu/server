using System;
using log4net;
using System.Reflection;

namespace MyWorld
{
    class Program
    {
        static void Main(string[] args)
        {
            //ILog log = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
            try
            {
                new TcpServer().start();
                Log.Info("server started");
            }
            catch (Exception e)
            {
                Log.Fatal(e);
            }
        }
    }
}
