package com.example.kotlin_application

//3 экрана:
//1й - авторизация
//2й - переиспользуется, на нем тест - выводится вопрос, и списком варианты ответов.
//Тест от 5 вопросов
//3й - результаты теста, с использованием имени пользователя с 1-го экрана
//Иконка, сплэш-скрин коррелируют с предметом теста

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import android.view.animation.AnticipateInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.animation.doOnEnd
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
// Навигационный контроллер
        val navController: NavController = findNavController(R.id.navHostFragment)

// Связывание навигации с BottomNavigationView (если используете)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        NavigationUI.setupWithNavController(bottomNav, navController)



 /*       // Set up an OnPreDrawListener to the root view.
        val content: View = findViewById(android.R.id.content)
        content.viewTreeObserver.addOnPreDrawListener(
            object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    // Check whether the initial data is ready.
                    return if (viewModel.isReady) {
                        // The content is ready. Start drawing.
                        content.viewTreeObserver.removeOnPreDrawListener(this)
                        true
                    } else {
                        // The content isn't ready. Suspend.
                        false
                    }
                }
            }
        )*/

        // Add a callback that's called when the splash screen is animating to the
        // app content.
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            // Create your custom animation.
            val slideUp = ObjectAnimator.ofFloat(
                splashScreenView,
                View.TRANSLATION_Y,
                0f,
                -splashScreenView.height.toFloat()
            )
            slideUp.interpolator = AnticipateInterpolator()
            slideUp.duration = 200L

            // Call SplashScreenView.remove at the end of your custom animation.
            slideUp.doOnEnd { splashScreenView.remove() }

            // Run your animation.
            slideUp.start()
        }

/*        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.main, AuthorizationFragment(), AUTHORIZATION_FRAGMENT)
                addToBackStack(AUTHORIZATION_FRAGMENT)
                commit()
            }
        }*/

    }

   /* companion object {
        private const val AUTHORIZATION_FRAGMENT = "UTHORIZATION_FRAGMENT"
        const val SECOND_FRAGMENT = "SECOND_FRAGMENT"
    }*/

/* val AuthorizationFragment : AuthorizationFragment = AuthorizationFragment()
supportFragmentManager
    .beginTransaction()
    .replace(R.id.container, AuthorizationFragment)
    .commit()*/

    }
