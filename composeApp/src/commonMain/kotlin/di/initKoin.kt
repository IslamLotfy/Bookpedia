package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initKoin(appContext: Any = Any(), koinAppDeclaration: KoinAppDeclaration? = null) {
    startKoin {
        koinAppDeclaration?.invoke(this)
        modules(createAppModule(appContext))
    }
}
