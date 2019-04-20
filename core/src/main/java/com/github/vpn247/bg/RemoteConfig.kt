/*******************************************************************************
 *                                                                             *
 *  Copyright (C) 2018 by Max Lv <max.c.lv@gmail.com>                          *
 *  Copyright (C) 2018 by Mygod Studio <contact-shadowsocks-android@mygod.be>  *
 *                                                                             *
 *  This program is free software: you can redistribute it and/or modify       *
 *  it under the terms of the GNU General Public License as published by       *
 *  the Free Software Foundation, either version 3 of the License, or          *
 *  (at your option) any later version.                                        *
 *                                                                             *
 *  This program is distributed in the hope that it will be useful,            *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of             *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the              *
 *  GNU General Public License for more details.                               *
 *                                                                             *
 *  You should have received a copy of the GNU General Public License          *
 *  along with this program. If not, see <http://www.gnu.org/licenses/>.       *
 *                                                                             *
 *******************************************************************************/

package com.github.vpn247.bg

import android.util.Log
import androidx.core.os.bundleOf
import com.github.vpn247.Core
import com.github.vpn247.core.R
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import java.net.URL

object RemoteConfig {
    private val config = FirebaseRemoteConfig.getInstance().apply { setDefaults(R.xml.default_configs) }

    val proxyUrl get() = URL(config.getString("proxy_url"))

    fun fetch() = config.fetch().addOnCompleteListener {
        if (it.isSuccessful) config.activateFetched() else {
            val e = it.exception ?: return@addOnCompleteListener
            Log.w("RemoteConfig", e)
            Core.analytics.logEvent("femote_config_failure", bundleOf(Pair(e.javaClass.simpleName, e.message)))
        }
    }
}
