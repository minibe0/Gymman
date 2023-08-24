package com.example.gymman

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.example.gymman.ui.theme.GymmanTheme
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //권한이 이미 부여되었는지 확인

        setContent {


            GymmanTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")

                }
            }
        }
    }

}

private lateinit var fusedLocationClient: FusedLocationProviderClient


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier
    )
}

//권한 확인 로직
@Composable
fun gpsPermissionCheck() {
    val context = LocalContext.current
    fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val permission = Manifest.permission.ACCESS_FINE_LOCATION

    val permissionStatus = ContextCompat.checkSelfPermission(context, permission)

    if (permissionStatus == PackageManager.PERMISSION_GRANTED) {
//권한 부여된 경우
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    // 위치 정보를 사용하여 작업 수행
                    val lat = location.latitude
                    val long = location.longitude
                }
            }


    } else {
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission(),
            onResult = { isNotGranted ->
                if (isNotGranted) {
                    launcher.launch( Manifest.permission.ACCESS_FINE_LOCATION)
                }
                    // 권한이 부여되었을 때 처리
                    // 예: 위치 서비스 시작
                }
        )

    }
}

//권한 부여 로직
@Composable
fun permissionAlarm() {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isNotGranted ->
            if (isNotGranted) {
                // 권한이 부여되었을 때 처리
                // 예: 위치 서비스 시작
            } else {
                // 권한이 부여되지 않았을 때 처리
            }
        }
    )

    when {
        ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED -> {
            Manifest.permission.ACCESS_FINE_LOCATION
        }

    }
    else -> {

        Launcher.launch( Manifest.permission.ACCESS_FINE_LOCATION)
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GymmanTheme {
        Greeting("Android")
    }
}
