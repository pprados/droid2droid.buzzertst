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
package org.droid2droid.apps.buzzer.test.fordesktop;

import android.app.Activity;
import android.test.AndroidTestRunner;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;

import fr.prados.android.test.fordesktop.DesktopInstrumentationTestRunner;

public class BuzzerDesktopInstrumentationTestRunner extends DesktopInstrumentationTestRunner 
{
	AndroidTestRunner mAndroidTestRunner;
    @Override
    protected AndroidTestRunner getAndroidTestRunner()
    {
    	return mAndroidTestRunner=super.getAndroidTestRunner();
    }
    private Solo getSolo()
    {
    	return ((ForDesktopActivityTest)mAndroidTestRunner.getTestCases().get(0)).getSolo();
    }

	public void clickOnCheckBox(int index)
	{
		Solo solo= getSolo();
		solo.clickOnCheckBox(index);
	}
	public boolean checkWaitingActivity()
	{
		Solo solo= getSolo();
		for(;;)
		{
			Activity currentActivity = solo.getCurrentActivity();
			if(currentActivity.getClass().getName().equals(WaitingActivity.class.getName()))
				return true;
			try
			{
				Thread.sleep(1000);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
				return false;
			}
		}
	}
	public void clickInList(int index)
	{
		Solo solo= getSolo();
		solo.clickInList(index);
	}
	public void waitForAnswer()
	{
		Solo solo= getSolo();
		TextView voteRemain = (TextView) solo.getView(org.droid2droid.apps.buzzer.R.id.vote_remain);
		for(;;)
		{
			solo.sleep(1000);
			if(Integer.parseInt(String.valueOf(voteRemain.getText()).trim())==0)
				break;
		}
	}
	public void clickOnButton(int index)
	{
		Solo solo= getSolo();
		solo.clickOnButton(index);
	}
	
//	public void clickOnListView()
	
}
