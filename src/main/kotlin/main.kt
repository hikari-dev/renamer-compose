import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


fun main() = Window(
    title = "Renamer",
    size = IntSize(500, 360),
    resizable = false,
    icon = loadIcon()
) {
    var dir by remember { mutableStateOf("") }
    var target by remember { mutableStateOf("") }
    var replacement by remember { mutableStateOf("") }
    var result by remember { mutableStateOf(Result("", Result.Type.INFO)) }

    MaterialTheme {
        Column(
            modifier = Modifier.padding(50.dp, 12.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            InputTextFiled(dir, { dir = it }, "文件所处文件夹路径")
            InputTextFiled(target, { target = it }, "想要替换掉的名称")
            InputTextFiled(replacement, { replacement = it }, "替换成为的名称")
            Button(
                onClick = { result = rename(dir, target, replacement) },
                modifier = Modifier.padding(0.dp, 18.dp, 0.dp, 0.dp)
            ) {
                Text("确定", fontSize = 18.sp, modifier = Modifier.padding(6.dp, 0.dp))
            }
            Text(
                text = result.info,
                fontSize = 16.sp,
                color = when (result.type) {
                    Result.Type.INFO -> {
                        Color.Blue
                    }
                    Result.Type.ERR -> {
                        Color.Red
                    }
                },
                modifier = Modifier.padding(0.dp, 12.dp, 0.dp, 0.dp)
            )
        }
    }
}

