package com.hichemtabtech.wame

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.hichemtabtech.wame.ui.theme.WameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WameTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = "main") {
                    composable("main") {
                        MainScreen(onInfoClick = { navController.navigate("info") })
                    }
                    composable("info") {
                        DeveloperInfoScreen(onBack = { navController.popBackStack() })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(onInfoClick: () -> Unit) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Tap to wa.me",
                        fontWeight = FontWeight.Bold
                    )
                },
                actions = {
                    IconButton(onClick = onInfoClick) {
                        Icon(Icons.Default.Info, contentDescription = "Developer Info")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    scrolledContainerColor = Color.Unspecified,
                    navigationIconContentColor = Color.Unspecified,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    actionIconContentColor = Color.Unspecified
                )
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                val context = LocalContext.current
                Text(
                    text = "Privacy Policy",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, "https://hichemtab-tech.github.io/wa.me/privacy.html".toUri())
                        context.startActivity(intent)
                    }
                )
            }
        }
    ) { innerPadding ->
        WameScreen(modifier = Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeveloperInfoScreen(onBack: () -> Unit) {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Developer Info") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Picture Placeholder
            AsyncImage(
                model = R.drawable.pdp,
                contentDescription = "Developer Profile Picture",
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Text("Hichem Taboukouyout", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text("Full-stack Developer", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            InfoLink("Website", "https://hichemtab-tech.me")
            InfoLink("GitHub", "https://github.com/HichemTab-tech")
            InfoLink("Email", "mailto:contact@hichemtab-tech.me")
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = {
                    val intent = Intent(Intent.ACTION_VIEW, "https://github.com/HichemTab-tech/wa.me".toUri())
                    context.startActivity(intent)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("View Source Code (Open Source)")
            }
        }
    }
}

@Composable
fun InfoLink(label: String, url: String) {
    val context = LocalContext.current
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                context.startActivity(intent)
            }
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = FontWeight.Medium)
        Text(url.removePrefix("mailto:"), color = MaterialTheme.colorScheme.primary, textDecoration = TextDecoration.Underline)
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    WameTheme {
        MainScreen(onInfoClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun DeveloperInfoScreenPreview() {
    WameTheme {
        DeveloperInfoScreen(onBack = {})
    }
}
