/******************************************************************************
 *
 * droid2droid - Distributed Android Framework
 * ==========================================
 *
 * Copyright (C) 2012 by Atos (http://www.http://atos.net)
 * http://www.droid2droid.org
 *
 ******************************************************************************
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
******************************************************************************/
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
