package com.baseproject.ui.custom

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.baseproject.todoapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
     title:String? = null,
     actions:  () -> Unit = {},
){
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = {
            title?.let {
                Text(text = it, textAlign = TextAlign.Center, modifier = Modifier.fillMaxWidth())
            }
        },
        navigationIcon = {
            Image(painterResource(R.drawable.ic_back),
                contentDescription = "Go back", modifier = Modifier.clickable { actions() })
        }
    )

}



@Preview
@Composable
fun Preview(){
    AppBar()
}