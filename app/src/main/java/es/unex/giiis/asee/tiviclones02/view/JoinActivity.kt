package es.unex.giiis.asee.tiviclones02.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

import es.unex.giiis.asee.tiviclones02.databinding.ActivityJoinBinding
import es.unex.giiis.asee.tiviclones02.model.User
import es.unex.giiis.asee.tiviclones02.utils.CredentialCheck

class JoinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinBinding

    companion object {

        const val USERNAME = "JOIN_USERNAME"
        const val PASS = "JOIN_PASS"
        fun start(
            context: Context,
            responseLauncher: ActivityResultLauncher<Intent>
        ) {
            //TODO create intent and launch activity

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //views initialization and listeners
        setUpUI()
        setUpListeners()
    }

    private fun setUpUI() {
        //get attributes from xml using binding
    }

    private fun setUpListeners() {
        with(binding) {
            btRegister.setOnClickListener {
                val check = CredentialCheck.join(
                    etUsername.text.toString(),
                    etPassword.text.toString(),
                    etRepassword.text.toString()
                )
                if (check.fail) notifyInvalidCredentials(check.msg)
                else navigateBackWithResult(User(etUsername.text.toString(), etPassword.text.toString()))
            }
        }
    }

    private fun navigateBackWithResult(user: User) {
        //TODO create intent and set result

    }

    private fun notifyInvalidCredentials(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }
}