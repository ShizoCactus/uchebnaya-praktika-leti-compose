import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

@Composable
@Preview
fun App() {
    val field1Value = remember { mutableStateOf("") }
    val field2Value = remember { mutableStateOf("") }
    val field3Value = remember { mutableStateOf("") }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            TextFieldWithButton(
                label = "Список вариантов",
                value = field1Value.value,
                onValueChange = { field1Value.value = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldWithButton(
                label = "Средние баллы",
                value = field2Value.value,
                onValueChange = { field2Value.value = it }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextFieldWithButton(
                label = "Выбор студентов",
                value = field3Value.value,
                onValueChange = { field3Value.value = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { Solution(field1Value.value, field2Value.value, field3Value.value).solve() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Обработать")
            }
        }
    }
}

@Composable
fun TextFieldWithButton(
    label: String,
    value: String,
    onValueChange: (String) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        OutlinedTextField(
            value = value,
            onValueChange = { onValueChange(it) },
            label = { Text(label) },
            modifier = Modifier.weight(1f)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { /* Действие при нажатии кнопки */ }) {
            Text("Сохранить")
        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
