package di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration


fun initKoin(koinAppDeclaration: KoinAppDeclaration? = null){
    startKoin {
        koinAppDeclaration?.invoke(this)
        modules(platformModule,dataModule, domainModule, presentationModule)
    }
}
