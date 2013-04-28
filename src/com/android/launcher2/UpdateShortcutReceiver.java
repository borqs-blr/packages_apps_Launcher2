/*
 * Copyright (c) 2013-2014, The Linux Foundation. All rights reserved.
 *
 *Redistribution and use in source and binary forms, with or without
 *modification, are permitted provided that the following conditions are
 *met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above
      copyright notice, this list of conditions and the following
      disclaimer in the documentation and/or other materials provided
      with the distribution.
    * Neither the name of The Linux Foundation nor the names of its
      contributors may be used to endorse or promote products derived
      from this software without specific prior written permission.

 *THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESS OR IMPLIED
 *WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
 *MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT
 *ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS
 *BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 *SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
 *BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 *WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 *OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN
 *IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.android.launcher2;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import android.util.Log;

import com.android.launcher.R;

public class UpdateShortcutReceiver extends BroadcastReceiver {
    public static final String ACTION_UPDATE_SHORTCUT =
            "com.android.launcher.action.UPDATE_SHORTCUT";

    public static final String EXTRA_SHORTCUT_NEWNAME =
            "com.android.launcher.extra.shortcut.NEWNAME";

    public void onReceive(Context context, Intent data) {
        if (!ACTION_UPDATE_SHORTCUT.equals(data.getAction())) {
            return;
        }

        if (data.getStringExtra(EXTRA_SHORTCUT_NEWNAME) != null) {
            updateShortcutTitle(context, data);
        }
    }

    private boolean updateShortcutTitle(Context context, Intent data) {
        String original_name = data.getStringExtra(Intent.EXTRA_SHORTCUT_NAME);
        String name = data.getStringExtra(EXTRA_SHORTCUT_NEWNAME);

        Intent intent = data.getParcelableExtra(Intent.EXTRA_SHORTCUT_INTENT);
        if (intent != null && intent.getAction() == null) {
            intent.setAction(Intent.ACTION_VIEW);
        }
        if (intent == null || !LauncherModel.shortcutExists(
                context, original_name, intent)) return false;

        LauncherApplication app = (LauncherApplication) context.getApplicationContext();
        app.getModel().updateItemTitleInDatabase(context, original_name, name, intent);
        return true;
    }
}
