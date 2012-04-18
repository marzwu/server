using System;

using System.Net.Sockets;
using System.Net;
using System.Threading;

namespace MyWorld
{
    public class TcpServer
    {
        public void start()
        {
            init();
        }

        private void init()
        {
            try
            {
                TcpListener listener = new TcpListener(IPAddress.Parse("127.0.0.1"), 3456);
                listener.Start();

                Thread listenerThread = new Thread(listenClientConnect);
                listenerThread.Start(listener);
            }
            catch (Exception e)
            {
                Console.WriteLine("listener exception: {0}", e);
            }
        }

        private void listenClientConnect(Object args)
        {
            TcpListener listener = args as TcpListener;
            TcpClient newClient = null;
            while (true)
            {
                try
                {
                    newClient = listener.AcceptTcpClient();
                }
                catch
                {
                    //因此可以利用此异常退出循环  
                    break;
                }
                //每接收一个客户端连接，就创建一个对应的线程循环接收该客户端发来的信息；  
                Thread recieveThread = new Thread(ReceiveData);
                recieveThread.Start(newClient);
            }
        }

        private void ReceiveData(object tcpClient)
        {
            try
            {
                TcpClient client = tcpClient as TcpClient;
                NetworkStream stream = client.GetStream();
                int i;
                Byte[] bytes = new Byte[256];
                String data = null;
                while ((i = stream.Read(bytes, 0, bytes.Length)) != 0)
                {
                    //string receiveString = null;
                    try
                    {
                        data = System.Text.Encoding.ASCII.GetString(bytes, 0, i);
                        Console.WriteLine("Received: {0}", data);

                        // Process the data sent by the client.
                        data = data.ToUpper();

                        byte[] msg = System.Text.Encoding.ASCII.GetBytes(data);

                        // Send back a response.
                        stream.Write(msg, 0, msg.Length);
                        Console.WriteLine("Sent: {0}", data);
                    }
                    catch (Exception e)
                    {
                        Console.WriteLine("SocketException: {0}", e);
                    }
                }
                Console.WriteLine("user logout");
            }
            catch (Exception e)
            {
                Console.WriteLine("client exception: {0}", e);
            }
        }
    }
}
