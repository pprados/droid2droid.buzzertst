package org.remoteandroid.apps.buzzer.test;

import org.remoteandroid.apps.buzzer.BuzzerActivity;

import com.jayway.android.robotium.solo.Solo;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class ForDesktopActivityTest extends ActivityInstrumentationTestCase2<BuzzerActivity>
{
	public static final int WAITING_TIME = 1000;
	private Solo solo;
	public ForDesktopActivityTest()
	{
		super("org.remoteandroid.apps.buzzer",BuzzerActivity.class);
	}
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	public void testDisplayWhiteBox()
	{
		CheckBox cb = (CheckBox) solo.getView(org.remoteandroid.apps.buzzer.R.id.discover);
		solo.clickOnView(cb);
		TextView activeConnection = (TextView) solo.getView(org.remoteandroid.apps.buzzer.R.id.active_connection_number);
		EditText time = (EditText) solo.getView(org.remoteandroid.apps.buzzer.R.id.editText);
		for(;;)
		{
			solo.sleep(WAITING_TIME);
			if(Integer.parseInt(String.valueOf(activeConnection.getText()).trim())>0 )
			break;
		}
		solo.sleep(5000);
		solo.clickInList(1);
		TextView voteRemain = (TextView) solo.getView(org.remoteandroid.apps.buzzer.R.id.vote_remain);
		for(;;)
		{
			solo.sleep(WAITING_TIME);
			if(Integer.parseInt(String.valueOf(voteRemain.getText()).trim())==0)
				break;
		}
		
		solo.sleep(2000);
		Button goback = (Button) solo.getView(org.remoteandroid.apps.buzzer.R.id.go_back);
		solo.clickOnView(goback);
		solo.sleep(2000);
	}
	@Override
	protected void tearDown() throws Exception
	{
		solo.finishOpenedActivities();
	}

}
