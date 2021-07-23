@file:Suppress("UnnecessaryVariable")

package com.morfly

import com.google.devtools.ksp.processing.*
import com.google.devtools.ksp.symbol.*
import com.google.devtools.ksp.symbol.Variance.*
import com.google.devtools.ksp.validate
import java.io.OutputStream


/**
 * This processor handles interfaces annotated with @Function.
 * It generates the function for each annotated interface. For each property of the interface it adds an argument for
 * the generated function with the same type and name.
 *
 * For example, the following code:
 *
 * ```kotlin
 * @Function(name = "myFunction")
 * interface MyFunction {
 *     val arg1: String
 *     val arg2: List<List<*>>
 * }
 * ```
 *
 * Will generate the corresponding function:
 *
 * ```kotlin
 * fun myFunction(
 *     arg1: String,
 *     arg2: List<List<*>>
 * ) {
 *     println("Hello from myFunction")
 * }
 * ```
 */
class FunctionProcessor(
  private val options: Map<String, String>,
  private val logger: KSPLogger,
  private val codeGenerator: CodeGenerator,
) : SymbolProcessor {

  operator fun OutputStream.plusAssign(str: String) {
    this.write(str.toByteArray())
  }

  override fun process(resolver: Resolver): List<KSAnnotated> {
    val symbols = resolver
      // Getting all symbols that are annotated with @Function.
      .getSymbolsWithAnnotation("com.morfly.Function")
      // Making sure we take only class declarations.
      .filterIsInstance<KSClassDeclaration>()

    // Exit from the processor in case nothing is annotated with @Function.
    if (!symbols.iterator().hasNext()) return emptyList()

    // The generated file will be located at:
    // build/generated/ksp/main/kotlin/com/morfly/GeneratedFunctions.kt
    val file = codeGenerator.createNewFile(
      dependencies = Dependencies(false),
      packageName = "com.morfly",
      fileName = "GeneratedFunctions"
    )
    // Generating package statement.
    file += "package com.morfly\n"

    // Processing each class declaration, annotated with @Function.
    symbols.forEach { it.accept(Visitor(file), Unit) }

    // Don't forget to close the out stream.
    file.close()

    val unableToProcess = symbols.filterNot { it.validate() }.toList()
    return unableToProcess
  }

  inner class Visitor(private val file: OutputStream) : KSVisitorVoid() {

    override fun visitClassDeclaration(classDeclaration: KSClassDeclaration, data: Unit) {
      if (classDeclaration.classKind != ClassKind.INTERFACE) {
        logger.error("Only interface can be annotated with @Function", classDeclaration)
        return
      }

      // Getting the @Function annotation object.
      val annotation: KSAnnotation = classDeclaration.annotations.first {
        it.shortName.asString() == "Function"
      }

      // Getting the 'name' argument object from the @Function.
      val nameArgument: KSValueArgument = annotation.arguments
        .first { arg -> arg.name?.asString() == "name" }

      // Getting the value of the 'name' argument.
      val functionName = nameArgument.value as String

      // Getting the list of member properties of the annotated interface.
      val properties: Sequence<KSPropertyDeclaration> = classDeclaration.getAllProperties()
        .filter { it.validate() }

      // Generating function signature.
      file += "\n"
      if (properties.iterator().hasNext()) {
        file += "fun $functionName(\n"

        // Iterating through each property to translate them to function arguments.
        properties.forEach { prop ->
          visitPropertyDeclaration(prop, Unit)
        }
        file += ") {\n"

      } else {
        // Otherwise, generating function with no args.
        file += "fun $functionName() {\n"
      }

      // Generating function body
      file += "    println(\"Hello from $functionName\")\n"
      file += "}\n"
    }

    override fun visitPropertyDeclaration(property: KSPropertyDeclaration, data: Unit) {
      // Generating argument name.
      val argumentName = property.simpleName.asString()
      file += "    $argumentName: "

      // Generating argument type.
      val resolvedType: KSType = property.type.resolve()
      file += resolvedType.declaration.qualifiedName?.asString() ?: run {
        logger.error("Invalid property type", property)
        return
      }
      file += if (resolvedType.nullability == Nullability.NULLABLE) "?" else ""

      // Generating generic parameters if any
      val genericArguments: List<KSTypeArgument> = property.type.element?.typeArguments ?: emptyList()
      visitTypeArguments(genericArguments)

      file += ",\n"
    }

    private fun visitTypeArguments(typeArguments: List<KSTypeArgument>) {
      if (typeArguments.isNotEmpty()) {
        file += "<"
        typeArguments.forEachIndexed { i, arg ->
          visitTypeArgument(arg, data = Unit)
          if (i < typeArguments.lastIndex) file += ", "
        }
        file += ">"
      }
    }

    override fun visitTypeArgument(typeArgument: KSTypeArgument, data: Unit) {
      // Handling KSP options, specified in the consumer's build.gradle(.kts) file.
      if (options["ignoreGenericArgs"] == "true") {
        file += "*"
        return
      }

      when (val variance: Variance = typeArgument.variance) {
        // <*>
        STAR -> {
          file += "*"
          return
        }
        // <out ...>, <in ...>
        COVARIANT, CONTRAVARIANT -> {
          file += variance.label
          file += " "
        }
        INVARIANT -> {
          // do nothing
        }
      }
      val resolvedType: KSType? = typeArgument.type?.resolve()
      file += resolvedType?.declaration?.qualifiedName?.asString() ?: run {
        logger.error("Invalid type argument", typeArgument)
        return
      }
      file += if (resolvedType?.nullability == Nullability.NULLABLE) "?" else ""

      val genericArguments: List<KSTypeArgument> = typeArgument.type?.element?.typeArguments ?: emptyList()
      visitTypeArguments(genericArguments)
    }
  }
}