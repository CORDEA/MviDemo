package jp.cordea.mvidemo.ui.login

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import jp.cordea.mvidemo.R
import jp.cordea.mvidemo.di.ActivityScope
import jp.cordea.mvidemo.ui.main.MainActivity
import javax.inject.Inject

@ActivityScope
class LoginNavigator @Inject constructor(
        private val activity: Activity
) {

    fun navigateToMain() {
        activity.startActivity(Intent(activity, MainActivity::class.java))
    }

    fun showError() {
        Toast.makeText(
                activity,
                activity.resources.getString(R.string.login_toast_login_failed_message),
                Toast.LENGTH_SHORT
        ).show()
    }
}
