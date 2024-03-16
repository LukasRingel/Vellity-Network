package net.vellity.minecraft.common

import net.vellity.minecraft.common.translations.TranslationRepository

class CommonsStartup {
  companion object {
    fun load() {
      TranslationRepository.startUpdateTask()
    }

    fun start() {

    }
  }
}