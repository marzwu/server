using System;

namespace MyWorld
{
    class Program
    {
        static void Main(string[] args)
        {
            try
            {
                new TcpServer().start();
            }
            catch (Exception e)
            {
                Console.WriteLine("error in main: {0}", e);
            }
        }
    }
}
