package in.adisworld.notification ;

import java.util.concurrent.locks.ReentrantLock;
import java.util.Properties ;
import java.io.InputStream ;
import java.io.FileNotFoundException ;
import java.io.IOException ;

public class NotificationConfig 
{

	private Properties prop = null ; 

	private static ReentrantLock lock = new ReentrantLock() ;
	
	private static NotificationConfig singleton = null ;

	private NotificationConfig(String configFileWithPath) throws FileNotFoundException
	{

		prop = new Properties() ;

		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configFileWithPath) ;
		if (inputStream == null)
		{
			throw new FileNotFoundException("Unable to find file " + configFileWithPath) ;
		}

		try
		{
			prop.load(inputStream) ;
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace() ;
			System.out.println ("IO Exception occurred while reading from properties file") ;
		}
	}

	
	public static NotificationConfig getInstance(String configFileWithPath) throws FileNotFoundException
	{
		lock.lock() ;

		if (singleton == null)
		{
			singleton = new NotificationConfig(configFileWithPath) ;
		}

		lock.unlock() ;

		return singleton ;
	}

	private String getProperty (String key)
	{
		return singleton.getProperty(key) ;
	}
}