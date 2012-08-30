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
package org.droid2droid.apps.buzzer.test;

import org.droid2droid.apps.buzzer.BuzzerActivity;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import static org.droid2droid.apps.buzzer.R.*;
import com.jayway.android.robotium.solo.Solo;

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
		CheckBox cb = (CheckBox) solo.getView(id.discover);
		solo.clickOnView(cb);
		TextView activeConnection = (TextView) solo.getView(id.active_connection_number);
		EditText time = (EditText) solo.getView(id.editText);
		for(;;)
		{
			solo.sleep(WAITING_TIME);
			if(Integer.parseInt(String.valueOf(activeConnection.getText()).trim())>0 )
			break;
		}
		solo.sleep(5000);
		solo.clickInList(1);
		TextView voteRemain = (TextView) solo.getView(id.vote_remain);
		for(;;)
		{
			solo.sleep(WAITING_TIME);
			if(Integer.parseInt(String.valueOf(voteRemain.getText()).trim())==0)
				break;
		}
		
		solo.sleep(2000);
		Button goback = (Button) solo.getView(id.go_back);
		solo.clickOnView(goback);
		solo.sleep(2000);
	}
	@Override
	protected void tearDown() throws Exception
	{
		solo.finishOpenedActivities();
	}

}
