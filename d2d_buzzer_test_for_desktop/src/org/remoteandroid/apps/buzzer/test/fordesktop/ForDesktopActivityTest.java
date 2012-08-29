package org.remoteandroid.apps.buzzer.test.fordesktop;

import org.remoteandroid.apps.buzzer.BuzzerActivity;

import android.widget.CheckBox;

import com.jayway.android.robotium.solo.Solo;

import fr.prados.android.test.fordesktop.DesktopActivityInstrumentationTestCase2;

public class ForDesktopActivityTest extends
DesktopActivityInstrumentationTestCase2<BuzzerActivity>
{
	private Solo solo;

	public Solo getSolo()
	{
		return solo;
	}
	public ForDesktopActivityTest()
	{
		super(BuzzerActivity.class);
	}
	@Override
	protected void setUp() throws Exception
	{
		super.setUp();
		solo = new Solo(getInstrumentation(), getActivity());
	}
	public void testPreConditions() 
	{
		CheckBox cb = (CheckBox) solo.getView(org.remoteandroid.apps.buzzer.R.id.discover);
	    assertFalse(cb.isChecked());
	} 
	public void testPause()
	{
		pauseTest();
	}
	@Override
	protected void tearDown() throws Exception
	{
		solo.finishOpenedActivities();
	}
}
