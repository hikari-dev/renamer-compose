import androidx.compose.desktop.Window
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
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
import java.io.File


fun main() = Window(
    title = "Renamer",
    size = IntSize(500, 360),
    resizable = false,
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
            OutlinedTextField(
                value = dir,
                onValueChange = { dir = it },
                modifier = Modifier.fillMaxWidth().padding(0.dp, 2.dp),
                singleLine = true,
                label = { Text("文件所处文件夹路径") },
            )
            OutlinedTextField(
                value = target,
                onValueChange = { target = it },
                modifier = Modifier.fillMaxWidth().padding(0.dp, 2.dp),
                singleLine = true,
                label = { Text("想要替换掉的名称") },
            )
            OutlinedTextField(
                value = replacement,
                onValueChange = { replacement = it },
                modifier = Modifier.fillMaxWidth().padding(0.dp, 2.dp),
                singleLine = true,
                label = { Text("替换成为的名称") }
            )
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

data class Result(val info: String, val type: Type) {
    enum class Type {
        ERR, INFO
    }
}

private fun rename(dir: String, target: String, replacement: String): Result {
    val file = File(dir)
    if (!file.exists()) {
        return Result("路径文件夹不存在！", Result.Type.ERR)

    }
    if (!file.isDirectory) {
        return Result("不是一个文件夹路径！", Result.Type.ERR)
    }
    file.walkTopDown()
        .maxDepth(1)
        .drop(1)
        .forEach { f ->
            f.renameTo(File(file, f.name.replaceFirst(target, replacement)))
        }
    return Result("修改完成", Result.Type.INFO)
}