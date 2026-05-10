package com.hichemtabtech.wame

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import androidx.core.net.toUri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hichemtabtech.wame.ui.theme.WameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WameTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    WameScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WameScreenPreview() {
    WameTheme {
        WameScreen()
    }
}

@Composable
fun WameScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var phoneNumber by remember { mutableStateOf("") }

    fun pasteFromClipboard() {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = clipboard.primaryClip
        if (clip != null && clip.itemCount > 0) {
            val text = clip.getItemAt(0).text.toString()
            phoneNumber = Sanitizer.sanitizePhoneNumber(text)
        }
    }

    fun openWhatsApp() {
        val sanitized = Sanitizer.sanitizePhoneNumber(phoneNumber)
        // Update input with sanitized version just in case
        phoneNumber = sanitized
        if (sanitized.isNotEmpty()) {
            val url = "https://wa.me/${sanitized.removePrefix("+")}"
            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            context.startActivity(intent)
        }
    }

    // Auto-paste on first load
    LaunchedEffect(Unit) {
        pasteFromClipboard()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Wa.me Redirect",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("Phone Number") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )

            Button(onClick = { pasteFromClipboard() }) {
                Text("Paste")
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { openWhatsApp() },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF25D366) // WhatsApp Green
            )
        ) {
            Text(
                text = "wa.me",
                fontSize = 48.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
            )
        }
    }
}
