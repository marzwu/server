using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using log4net;
using System.Reflection;

namespace MyWorld
{
    public class Log
    {
        protected static ILog iLog;
        public static ILog logger
        {
            get
            {
                if (null == iLog)
                    iLog = LogManager.GetLogger(MethodBase.GetCurrentMethod().DeclaringType);
                return iLog;
            }
        }

        public static void Debug(object message)
        {
            logger.Debug(message);
        }

        public static void Debug(object message, Exception exception)
        {
            logger.Debug(message, exception);
        }

        public static void Fatal(object message)
        {
            logger.Fatal(message);
        }

        public static void Fatal(object message, Exception exception)
        {
            logger.Fatal(message, exception);
        }

        public static void Info(object message)
        {
            logger.Info(message);
        }

        public static void Info(object message, Exception exception)
        {
            logger.Info(message, exception);
        }

        public static void Warn(object message)
        {
            logger.Warn(message);
        }

        public static void Warn(object message, Exception exception)
        {
            logger.Warn(message, exception);
        }
    }
}
