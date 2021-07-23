package com.morfly

import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider


class FunctionProcessorProvider : SymbolProcessorProvider {

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor {
    return FunctionProcessor(
      options = environment.options,
      logger = environment.logger,
      codeGenerator = environment.codeGenerator
    )
  }
}