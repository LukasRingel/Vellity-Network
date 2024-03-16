package net.vellity.minecraft.common.validator

interface Validator<T> {
  fun isValid(input: T): Boolean

  fun errorMessage(): String {
    return "commons-input-validator-invalid"
  }
}