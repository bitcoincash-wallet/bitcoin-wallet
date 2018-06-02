/*
 * Copyright 2014-2015 the original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.schildbach.wallet.ui.preference;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import org.bitcoinj.core.VersionMessage;

import de.schildbach.wallet.Constants;
import de.schildbach.wallet.WalletApplication;
import de.schildbach.wallet_test.BuildConfig;
import de.schildbach.wallet_test.R;

/**
 * @author Andreas Schildbach
 */
public final class AboutFragment extends PreferenceFragment {
    private WalletApplication application;
    private PackageManager    packageManager;

    private static final String KEY_ABOUT_VERSION          = "about_version";
    private static final String KEY_ABOUT_SOURCE           = "about_source";
    private static final String KEY_ABOUT_UPSTREAM         = "about_upstream";
    private static final String KEY_ABOUT_CI               = "about_ci";
    private static final String KEY_ABOUT_MARKET_APP       = "about_market_app";
    private static final String KEY_ABOUT_CREDITS_BITCOINJ = "about_credits_bitcoinj";

    @Override
    public void onAttach(final Activity activity) {
        super.onAttach(activity);

        this.application = (WalletApplication) activity.getApplication();
        this.packageManager = activity.getPackageManager();
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference_about);

        final PackageInfo packageInfo = application.packageInfo();
        findPreference(KEY_ABOUT_VERSION).setSummary(WalletApplication.versionLine(packageInfo));
        Intent repoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(BuildConfig.GIT_COMMIT_URL, packageInfo.packageName)));
        Intent repoUpstreamIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(BuildConfig.GIT_UPSTREAM_COMMIT_URL, packageInfo.packageName)));
        Intent ciIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(BuildConfig.CI_BUILD_URL, packageInfo.packageName)));
        Intent marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Constants.MARKET_APP_URL, packageInfo.packageName)));
        if (packageManager.resolveActivity(marketIntent, 0) == null) {
            marketIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format(Constants.WEBMARKET_APP_URL, packageInfo.packageName)));
        }
        findPreference(KEY_ABOUT_MARKET_APP).setIntent(marketIntent);
        findPreference(KEY_ABOUT_CREDITS_BITCOINJ)
                .setTitle(getString(R.string.about_credits_bitcoinj_title, VersionMessage.BITCOINJ_VERSION));
        findPreference(KEY_ABOUT_SOURCE).setSummary(BuildConfig.GIT_COMMIT_URL);
        findPreference(KEY_ABOUT_UPSTREAM).setSummary(BuildConfig.GIT_UPSTREAM_COMMIT_URL);
        findPreference(KEY_ABOUT_UPSTREAM).setTitle((getString(R.string.about_upstream_title, BuildConfig.GIT_UPSTREAM_TAG)));
        findPreference(KEY_ABOUT_SOURCE).setIntent(repoIntent);
        findPreference(KEY_ABOUT_UPSTREAM).setIntent(repoUpstreamIntent);
        findPreference(KEY_ABOUT_CI).setSummary(BuildConfig.CI_BUILD_URL);
        findPreference(KEY_ABOUT_CI).setIntent(ciIntent);
    }
}
