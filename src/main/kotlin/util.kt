import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

fun rename(dir: String, target: String, replacement: String): Result {
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

fun loadIcon(): BufferedImage {
    val resource = Thread.currentThread().contextClassLoader.getResource("567.jpeg")
    return resource?.openStream().use {
        ImageIO.read(it)
    }
}