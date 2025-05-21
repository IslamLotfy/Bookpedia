package di

import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformModule(context: Any): Module = module {
    // iOS-specific dependencies
} 