package com.peargrammers.flatfinder

import android.app.Application
import com.peargrammers.flatfinder.repository.LoginRepository
import com.peargrammers.flatfinder.repository.OfferRepository
import com.peargrammers.flatfinder.ui.viewmodel.LoginViewModelProviderFactory
import com.peargrammers.flatfinder.ui.viewmodel.OfferViewModelProviderFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class FlatFinderApplication : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@FlatFinderApplication))

        bind() from singleton { OfferRepository() }
        bind() from provider {
            OfferViewModelProviderFactory(
                instance()
            )
        }
        bind() from singleton { LoginRepository() }
        bind() from provider {
            LoginViewModelProviderFactory(
                instance()
            )
        }
    }
}