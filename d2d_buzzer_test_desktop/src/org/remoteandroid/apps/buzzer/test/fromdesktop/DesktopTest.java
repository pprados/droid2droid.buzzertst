package org.remoteandroid.apps.buzzer.test.fromdesktop;

import java.io.IOException;
import java.net.UnknownHostException;

import fr.prados.android.test.fordesktop.AdbDevice;
import fr.prados.android.test.fordesktop.DesktopTestCase;

public class DesktopTest extends DesktopTestCase
{

	public DesktopTest(String name)
	{
		super(name);
	}

	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		assertTrue(mDevices.length >= 1);
	}

	public AdbDevice getServer()
	{
		return mDevices[0];
	}

	public AdbDevice getClient()
	{
		return mDevices[1];
	}
	//Tests 
	public void testSimpleVote() throws Throwable
	{
		try
		{
			instrumentationServer();
			instrumentationClient();
			// Methode a appeller
			clickOnCheckBox(getServer(), 0);
			if (checkWaitingActivity(getClient()))
			{
				clickInList(getServer(), 1);
				new Thread(new Runnable()
				{

					@Override
					public void run()
					{
						try
						{
							Thread.sleep(5000);
							clickOnButton(getClient(), 1);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						} catch (Throwable e)
						{
							e.printStackTrace();
						}
					}
				}).start();
				waitForAnswer(getServer());
			}

		} finally
		{
			try
			{
				getServer().continueTest();
				getClient().continueTest();
			} catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public void testMultiChoiceVote() throws Throwable
	{
		try
		{
			instrumentationServer();
			instrumentationClient();
			// Methode a appeller
			clickOnCheckBox(getServer(), 0);
			if (checkWaitingActivity(getClient()))
			{
				clickInList(getServer(), 5);
				new Thread(new Runnable()
				{

					@Override
					public void run()
					{
						try
						{
							Thread.sleep(5000);
							clickOnCheckBox(getClient(), 0);
							clickOnCheckBox(getClient(), 2);
							clickOnButton(getClient(), 3);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						} catch (Throwable e)
						{
							e.printStackTrace();
						}
					}
				}).start();
				waitForAnswer(getServer());
			}

		} finally
		{
			try
			{
				getServer().continueTest();
				getClient().continueTest();
			} catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}

	public void testKillServer() throws Throwable
	{
		try
		{
			instrumentationServer();
			instrumentationClient();
			// Methode a appeller
			clickOnCheckBox(getServer(), 0);
			if (checkWaitingActivity(getClient()))
			{
				clickInList(getServer(), 1);
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(5000);
							clickOnButton(getClient(), 1);
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						} catch (Throwable e)
						{
							e.printStackTrace();
						}
					}
				}).start();
				getServer().continueTest();
				checkWaitingActivity(getClient());
			}

		} finally
		{
			try
			{
				getServer().continueTest();
				getClient().continueTest();
			} catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}

	public void testKillClient() throws Throwable
	{
		try
		{
			instrumentationServer();
			instrumentationClient();
			// Methode a appeller
			clickOnCheckBox(getServer(), 0);
			if (checkWaitingActivity(getClient()))
			{
				clickInList(getServer(), 1);
				new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						try
						{
							Thread.sleep(5000);
							getClient().continueTest();
						} catch (InterruptedException e)
						{
							e.printStackTrace();
						} catch (Throwable e)
						{
							e.printStackTrace();
						}
					}
				}).start();
				waitForAnswer(getServer());
			}
		} finally
		{
			try
			{
				getServer().continueTest();
				getClient().continueTest();
			} catch (Throwable e)
			{
				e.printStackTrace();
			}
		}
	}

	//Methodes
	public void instrumentationServer() throws UnknownHostException,
			IOException
	{
		getServer()
				.shell("am instrument "
						+
						// Wait the startup
						"-w "
						+
						// If running in debugger
						(isDebug() ? "-e debug true " : "")
						+
						// Select the test to run and pause
						"-e class org.remoteandroid.apps.buzzer.test.fordesktop.ForDesktopActivityTest#testPause "
						+
						// The package
						"org.remoteandroid.apps.buzzer.test/" +
						// The instrumentation with public method would be
						// called by Desktop
						".fordesktop.BuzzerDesktopInstrumentationTestRunner");
		getServer().connectApp();
	}

	public void instrumentationClient() throws UnknownHostException,
			IOException
	{
		getClient()
				.shell("am instrument "
						+
						// Wait the startup
						"-w "
						+
						// If running in debugger
						(isDebug() ? "-e debug true " : "")
						+
						// Select the test to run and pause
						"-e class org.remoteandroid.apps.buzzer.test.fordesktop.ForDesktopActivityTest#testPause "
						+
						// The package
						"org.remoteandroid.apps.buzzer.test/" +
						// The instrumentation with public method would be
						// called by Desktop
						".fordesktop.BuzzerDesktopInstrumentationTestRunner");
		getClient().connectApp();
	}

	void clickOnCheckBox(AdbDevice device, int index) throws IOException,
			Throwable
	{
		device.execute("clickOnCheckBox", index);
	}

	boolean checkWaitingActivity(AdbDevice device) throws IOException,
			Throwable
	{
		return (Boolean) device.execute("checkWaitingActivity");
	}

	public void clickInList(AdbDevice device, int index) throws IOException,
			Throwable
	{
		device.execute("clickInList", index);
	}

	public void waitForAnswer(AdbDevice device) throws IOException, Throwable
	{
		device.execute("waitForAnswer");
	}

	public void clickOnButton(AdbDevice device, int index) throws IOException,
			Throwable
	{
		device.execute("clickOnButton", index);
	}
}
