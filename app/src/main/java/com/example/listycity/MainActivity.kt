package com.example.my_lab2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import com.example.listycity.ui.theme.ListyCityTheme
import com.example.listycity.ui.theme.ListyCityTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val cityRepository = CityRepository()
        setContent {
            ListyCityTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    CityListScreen(
                        cities = cityRepository.cities,
                        onAddCity = { cityRepository.addCity(it) },
                        onRemoveCity = { cityRepository.removeCity(it) },
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}


class CityRepository {
    private val _cities = mutableStateListOf(
        "Edmonton",
        "Vancouver",
        "Moscow",
        "Sydney",
        "Berlin",
        "Vienna",
        "Tokyo",
        "Beijing",
        "Osaka",
        "New Delhi"
    )
    val cities: List<String>
        get() = _cities

    fun addCity(city: String) {
        _cities.add(city)
    }

    fun removeCity(city: String) {
        _cities.remove(city)
    }

}

@Composable
fun CityRow (
    city: String,
    //selected: Boolean,
    onClick: () -> Unit
) {
    Text(
        text = city,
        fontSize = 28.sp,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp)
            .clickable{
                onClick()
            }
    )
}

@Composable
fun CityListScreen(
    cities: List<String>,
    onAddCity: (String) -> Unit,
    onRemoveCity: (String) -> Unit,
    modifier: Modifier = Modifier
)  {
    var addingCityName by remember { mutableStateOf("") }
    var selectedCityName by remember { mutableStateOf<String?>(null) }
    var adding by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        if(adding) {
            Row(modifier = Modifier.padding()) {
                OutlinedTextField(
                    value = addingCityName,
                    onValueChange = { addingCityName = it },
                    label = { Text("City Name") },
                    modifier = Modifier.padding(top=10.dp).weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Button(
                    onClick = {
                        if(addingCityName.isNotBlank()) {
                            onAddCity(addingCityName)
                            addingCityName = ""
                            adding = false
                        }
                    },
                    modifier = Modifier.padding(top = 40.dp)
                ) {
                    Text("CONFIRM")
                }

            }

            //if
        } else{
            Button(
                onClick = {
                    adding = true
                },
                modifier = Modifier.fillMaxWidth().padding(top = 60.dp)
            ) {
                Text("ADD CITY")
            }
        }

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(cities) {city ->
                CityRow(
                    city = city,
                    //selected = city == selectedCityName,
                    onClick = {
                        selectedCityName = city
                    }
                )
            }
            // Lazy
        }

        Button(
            onClick = {
                if (selectedCityName != null) {
                    onRemoveCity(selectedCityName!!)
                    selectedCityName = ""
                }
            },
            modifier = modifier.fillMaxWidth().padding(bottom =15.dp)
        ){
            Text("DELECT CITY")
        }


        // column
    }

}