package com.example.assignment.ui.main

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.assignment.R
import com.example.assignment.ui.main.dashboard.DashBoardFragment
import java.util.regex.Matcher
import java.util.regex.Pattern


class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var loginButton: Button? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private lateinit var viewModel: MainViewModel

    val emailId = "test@worldofplay.in"
    val password = "Worldofplay@2020"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.main_fragment, container, false)
        initializeView(view)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
    }

    private fun initializeView(view: View?) {
        emailEditText = view?.findViewById(R.id.editTextEmail)
        passwordEditText = view?.findViewById(R.id.editTextPassword)
        loginButton = view?.findViewById(R.id.buttonLogin)

        loginButton?.setOnClickListener { handleLoginButton() }
    }

    private fun handleLoginButton() {
        if(EmailAndPasswordValidator.isValidEmail(emailEditText?.text.toString()) && EmailAndPasswordValidator.isValidPassword(passwordEditText?.text.toString()))
        {
            Patterns.EMAIL_ADDRESS
            if((emailEditText?.text.toString() == emailId) && (passwordEditText?.text.toString() == password) )
            {
                Toast.makeText(activity,"Valid",Toast.LENGTH_SHORT).show()
                // Go to Dashboard
                //val nextFrag = DashBoardFragment()
                activity!!.supportFragmentManager.beginTransaction()
                    .replace(R.id.container, DashBoardFragment.newInstance(), "DashBoardFragment")
                    .commit()
            }
            else{
                Toast.makeText(activity,"Valid but not matching",Toast.LENGTH_SHORT).show()
            }
        }
        else
        {
            Toast.makeText(activity,"InValid",Toast.LENGTH_SHORT).show()
        }
    }
}
