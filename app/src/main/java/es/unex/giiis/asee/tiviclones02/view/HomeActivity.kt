package es.unex.giiis.asee.tiviclones02.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import es.unex.giiis.asee.tiviclones02.R
import es.unex.giiis.asee.tiviclones02.databinding.ActivityHomeBinding
import es.unex.giiis.asee.tiviclones02.model.User

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    companion object {
        const val USER_INFO = "USER_INFO"
        fun start(
            context: Context,
            user: User,
        ) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra(USER_INFO) as User

        setUpUI(user)
        setUpListeners()
    }

    fun setUpUI(user: User) {
        with(binding) {
            tvGreeting.text = getString(R.string.greeting, user.name)
        }
    }

    fun setUpListeners() {
        //nothing to do
    }
}