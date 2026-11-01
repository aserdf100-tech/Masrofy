package com.masrofy.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                MasrofyApp()
            }
        }
    }
}

@Composable
fun MasrofyApp() {
    var amount by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "مصروفي",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text("إدارة مصروفاتك اليومية")

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("المبلغ بالجنيه") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(onClick = { amount = "" }) {
                Text("إضافة مصروف")
            }
        }
    }
}