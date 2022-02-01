package io.lb.rxjavaexample.ui.post

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.lb.rxjavaexample.databinding.ActivityPostBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable

class PostActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPostBinding
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPostBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}