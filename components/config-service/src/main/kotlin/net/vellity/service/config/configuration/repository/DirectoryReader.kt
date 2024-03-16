package net.vellity.service.config.configuration.repository

import net.vellity.utils.context.Context
import java.io.File
import java.io.IOException
import java.nio.file.FileVisitResult
import java.nio.file.FileVisitor
import java.nio.file.Path
import java.nio.file.attribute.BasicFileAttributes
import java.util.function.BiConsumer

class DirectoryReader(private val fileFound: BiConsumer<Context, File>) : FileVisitor<Path> {
  override fun preVisitDirectory(path: Path?, p1: BasicFileAttributes?): FileVisitResult {
    path ?: return FileVisitResult.CONTINUE
    if (ignoredDirectories.contains(path.toFile().name)) {
      return FileVisitResult.SKIP_SUBTREE
    }
    return FileVisitResult.CONTINUE
  }

  override fun visitFile(path: Path?, attributes: BasicFileAttributes?): FileVisitResult {
    path ?: return FileVisitResult.CONTINUE
    if (!path.toFile().name.lowercase().endsWith(".json")) {
      return FileVisitResult.CONTINUE
    }

    var context = Context.UNKNOWN
    for (contextValue in Context.values()) {
      if (path.toFile().absolutePath.contains(contextValue.name)) {
        context = contextValue
        break
      }
    }

    fileFound.accept(context, path.toFile())
    return FileVisitResult.CONTINUE
  }

  override fun visitFileFailed(p0: Path?, p1: IOException?): FileVisitResult {
    return FileVisitResult.CONTINUE
  }

  override fun postVisitDirectory(p0: Path?, p1: IOException?): FileVisitResult {
    return FileVisitResult.CONTINUE
  }

  companion object {
    private val ignoredDirectories = listOf(".git")
    private val configFileExtensions = listOf(".json", ".yml")
  }
}