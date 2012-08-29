package org.remoteandroid.apps.buzzer.test.fordesktop;

import org.remoteandroid.apps.buzzer.WaitingActivity;

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
		TextView voteRemain = (TextView) solo.getView(org.remoteandroid.apps.buzzer.R.id.vote_remain);
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
