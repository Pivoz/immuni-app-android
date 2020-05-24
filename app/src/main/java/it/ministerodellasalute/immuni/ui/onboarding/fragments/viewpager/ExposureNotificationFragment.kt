/*
 * Copyright (C) 2020 Presidenza del Consiglio dei Ministri.
 * Please refer to the AUTHORS file for more information.
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package it.ministerodellasalute.immuni.ui.onboarding.fragments.viewpager

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import it.ministerodellasalute.immuni.R
import it.ministerodellasalute.immuni.extensions.view.setSafeOnClickListener
import kotlinx.android.synthetic.main.onboarding_exposure_fragment.*

class ExposureNotificationFragment :
    ViewPagerBaseFragment(R.layout.onboarding_exposure_fragment) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var handled = false
        viewModel.isBroadcastingActive.observe(viewLifecycleOwner, Observer { isBroadcastingActive ->
            if (!handled && isBroadcastingActive == true && this.isResumed) {
                handled = true
                viewModel.onNextTap()
            }
        })

        next.isEnabled = true

        next.setOnClickListener(null)
        next.setSafeOnClickListener {

            if (canProceed()) {
                viewModel.onNextTap()
            } else {
                activity?.let {
                    viewModel.startExposureNotification(it)
                }
            }
        }

        checkSpacing()
    }

    private fun canProceed(): Boolean {
        return viewModel.isBroadcastingActive.value ?: false
    }
}
